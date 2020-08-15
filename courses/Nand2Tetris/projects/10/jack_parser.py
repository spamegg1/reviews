# Parses list of tokens outputted by a Jack tokenizer
# spamegg 2/2/2019
'Parser for Jack tokenizer, into XML'

import os
import sys
import jack_tokenizer

TERMS = ['integerConstant', 'stringConstant', 'identifier']
STATEMENTS = ['do', 'while', 'let', 'if', 'return']
UNARYOP = ['-', '~']
OP = ['+', '-', '*', '/', '&', '|', '<', '>', '=']
CLASSVAR = ['static', 'field']
SUB = ['method', 'constructor', 'function']


class JackParser():
    'Creates a parser object for Jack source code.'

    def __init__(self, infile, outfile):
        """Takes an input and an output filename as input.
        Creates a Parser object and gets ready to parse input file and to
        write to output file in XML format. """
        # create tokenizer from input file
        self.tokenizer = jack_tokenizer.JackTokenizer(infile)

        # ready outfile for writing
        self.outfile = open(outfile, 'w')

    def compile_class(self):
        """Compiles an entire class in a Jack source file."""
        self.tokenizer.advance()
        # current token is 'class'
        self.outfile.write('<class>\n<keyword> class </keyword>\n')
        self.tokenizer.advance()
        # current token is the class name, e.g. 'Main'
        token = self.tokenizer.current_token
        self.outfile.write('<identifier> ' + token + ' </identifier>\n')
        self.tokenizer.advance()
        # current token is '{'
        self.outfile.write('<symbol> { </symbol>\n')
        self.tokenizer.advance()
        # next, handle class var declarations
        while self.tokenizer.current_token in CLASSVAR:
            self.compile_class_var_dec()
            self.tokenizer.advance()
        # once class var declarations are done, handle subroutine declarations
        while self.tokenizer.current_token in SUB:
            self.compile_subroutine()
            self.tokenizer.advance()
        # current token must be '}'
        self.outfile.write('<symbol> } </symbol>\n</class>\n')
        self.outfile.close()

    def compile_class_var_dec(self):
        """Compiles a static variable or field variable declaration."""
        self.outfile.write('<classVarDec>\n')
        # get current token. This is either 'static' or 'field'
        token = self.tokenizer.current_token
        self.outfile.write('<keyword> ' + token + ' </keyword>\n')
        self.tokenizer.advance()
        # current token is 'void', int', 'boolean', 'char' or a class name
        token = self.tokenizer.current_token
        tokentype = self.tokenizer.tokentype()
        self.outfile.write('<' + tokentype + '> ' + token)
        self.outfile.write(' </' + tokentype + '>\n')
        self.tokenizer.advance()
        # current token is an identifier (the name of the variable)
        token = self.tokenizer.current_token
        self.outfile.write('<identifier> ' + token + ' </identifier>\n')
        self.tokenizer.advance()
        # if current token is ',' there are more than one variables
        while self.tokenizer.current_token == ',':
            self.outfile.write('<symbol> , </symbol>\n')
            self.tokenizer.advance()
            # current token is an identifier (var name)
            token = self.tokenizer.current_token
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
        # once variables are done, current token is ';'
        self.outfile.write('<symbol> ; </symbol>\n</classVarDec>\n')

    def compile_subroutine(self):
        """Compiles a complete method, function or constructor."""
        self.outfile.write('<subroutineDec>\n')
        # get current token. This is 'function', 'method' or 'constructor'
        token = self.tokenizer.current_token
        self.outfile.write('<keyword> ' + token + ' </keyword>\n')
        self.tokenizer.advance()
        # current token is a keyword (the return type of subroutine)
        # this is either 'void', 'int', 'char', 'boolean' or some class name
        token = self.tokenizer.current_token
        tokentype = self.tokenizer.tokentype()
        self.outfile.write('<' + tokentype + '> ' + token)
        self.outfile.write(' </' + tokentype + '>\n')
        self.tokenizer.advance()
        # current token is an identifier (name of subroutine), e.g. 'getLength'
        token = self.tokenizer.current_token
        self.outfile.write('<identifier> ' + token + ' </identifier>\n')
        self.tokenizer.advance()
        # current token is '('
        self.outfile.write('<symbol> ( </symbol>\n')
        self.tokenizer.advance()
        # current token is the start of a parameter list, or ')'
        self.compile_parameter_list()  # this quits when token is ')'
        # after parameter list is written, current token must be ')'
        self.outfile.write('<symbol> ) </symbol>\n')
        self.tokenizer.advance()
        # current token is '{', this begins the subroutine body
        self.outfile.write('<subroutineBody>\n<symbol> { </symbol>\n')
        self.tokenizer.advance()
        # subroutine body is a list of var declarations and statements
        while self.tokenizer.current_token == 'var':
            self.compile_var_dec()
            self.tokenizer.advance()
        # once var declarations are done, handle statements
        self.compile_statements(self.tokenizer.current_token)
        # after var declarations and statements, current token must be '}'
        self.outfile.write('<symbol> } </symbol>\n')
        self.outfile.write('</subroutineBody>\n</subroutineDec>\n')

    def compile_parameter_list(self):
        """Compiles a possibly empty parameter list, excluding ()."""
        # this is called only after prev token was '(' in a subroutine
        self.outfile.write('<parameterList>\n')
        # if current token is ')', it's end of (or an empty) parameter list
        while self.tokenizer.current_token != ')':
            # current token is 'int', 'char', 'boolean' or a class name
            token = self.tokenizer.current_token
            self.outfile.write('<keyword> ' + token + ' </keyword>\n')
            self.tokenizer.advance()
            # current token is a parameter name, e.g. 'Asize'
            token = self.tokenizer.current_token
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
            # if current token is ',' there are more parameters in list
            while self.tokenizer.current_token == ',':
                self.outfile.write('<symbol> , </symbol>\n')
                self.tokenizer.advance()
                # current token is 'int', 'char', 'boolean' or a class name
                token = self.tokenizer.current_token
                self.outfile.write('<keyword> ' + token + ' </keyword>\n')
                self.tokenizer.advance()
                # current token is a parameter name, e.g. 'Asize'
                token = self.tokenizer.current_token
                self.outfile.write('<identifier> ' + token)
                self.outfile.write(' </identifier>\n')
                self.tokenizer.advance()
        # if current token is ')', finish and quit
        self.outfile.write('</parameterList>\n')

    def compile_var_dec(self):
        """Compiles a variable declaration."""
        # current token is 'var'
        self.outfile.write('<varDec>\n<keyword> var </keyword>\n')
        self.tokenizer.advance()
        # current token is keyword or identifier, e.g. 'int', 'SquareGame'
        token = self.tokenizer.current_token
        tokentype = self.tokenizer.tokentype()
        self.outfile.write('<' + tokentype + '> ' + token)
        self.outfile.write(' </' + tokentype + '>\n')
        self.tokenizer.advance()
        # current token is an identifier (var name), e.g. 'game'
        token = self.tokenizer.current_token
        self.outfile.write('<identifier> ' + token + ' </identifier>\n')
        self.tokenizer.advance()
        # if current token is ',' there is more than one var listed
        while self.tokenizer.current_token == ',':
            self.outfile.write('<symbol> , </symbol>\n')
            self.tokenizer.advance()
            # current token is another identifier (var name), e.g. 'j'
            token = self.tokenizer.current_token
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
        # current token is ';'
        self.outfile.write('<symbol> ; </symbol>\n</varDec>\n')

    def compile_statements(self, token):
        """Compiles a sequence of statements, excluding {}."""
        # current token is 'do', 'while', 'let', 'if' or 'return'
        self.outfile.write('<statements>\n')
        token = self.tokenizer.current_token
        while token in STATEMENTS:
            if token == 'do':
                self.compile_do()
            elif token == 'let':
                self.compile_let()
            elif token == 'while':
                self.compile_while()
            elif token == 'return':
                self.compile_return()
            elif token == 'if':
                self.compile_if()
            self.tokenizer.advance()
            token = self.tokenizer.current_token
        # if current token is no longer in STATEMENTS, finish and quit
        self.outfile.write('</statements>\n')

    def compile_do(self):
        """Compiles a do statement."""
        # current token is 'do'
        self.outfile.write('<doStatement>\n<keyword> do </keyword>\n')
        self.tokenizer.advance()
        # current token is an identifier (subroutine call or var/class name)
        token = self.tokenizer.current_token
        self.outfile.write('<identifier> ' + token + ' </identifier>\n')
        self.tokenizer.advance()
        # current token is either '.' or '('
        if self.tokenizer.current_token == '.':
            self.outfile.write('<symbol> . </symbol>\n')
            self.tokenizer.advance()
            # current token is an identifier (subroutine call)
            token = self.tokenizer.current_token
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
        # else current token is '('
        self.outfile.write('<symbol> ( </symbol>\n')
        self.tokenizer.advance()
        # now handle the expression list inside the parentheses
        self.compile_expression_list()
        # after expr list, current token should be ')', followed by ';'
        self.outfile.write('<symbol> ) </symbol>\n')
        self.tokenizer.advance()
        self.outfile.write('<symbol> ; </symbol>\n</doStatement>\n')

    def compile_let(self):
        """Compiles a let statement."""
        # current token is 'let'
        self.outfile.write('<letStatement>\n<keyword> let </keyword>\n')
        self.tokenizer.advance()
        # current token is an identifier (var name)
        token = self.tokenizer.current_token
        self.outfile.write('<identifier> ' + token + ' </identifier>\n')
        self.tokenizer.advance()
        # current token is either '[' or '='
        if self.tokenizer.current_token == '[':
            self.outfile.write('<symbol> [ </symbol>\n')
            self.tokenizer.advance()
            # now current token is the beginning of an expression
            self.compile_expression()  # this advances the token
            # after the expression is done, current token is ']'
            self.outfile.write('<symbol> ] </symbol>\n')
            self.tokenizer.advance()
        # current token is '='
        self.outfile.write('<symbol> = </symbol>\n')
        self.tokenizer.advance()
        # now current token is the beginning of an expression
        self.compile_expression()  # this advances the token
        # after the expression is done, current token is ';'
        self.outfile.write('<symbol> ; </symbol>\n</letStatement>\n')

    def compile_while(self):
        """Compiles a while statement. Is only called by compile_statements."""
        # current token is 'while'
        self.outfile.write('<whileStatement>\n<keyword> while </keyword>\n')
        self.tokenizer.advance()
        # current token is '('
        self.outfile.write('<symbol> ( </symbol>\n')
        self.tokenizer.advance()
        # current token is the beginning of an expression
        self.compile_expression()  # this advances the token
        # after the expression is done, current token is ')'
        self.outfile.write('<symbol> ) </symbol>\n')
        self.tokenizer.advance()
        # current token is '{'
        self.outfile.write('<symbol> { </symbol>\n')
        self.tokenizer.advance()
        # current token is the beginning of a list of statements
        token = self.tokenizer.current_token
        self.compile_statements(token)  # this advances the token
        # after statements are done, current token is '}'
        self.outfile.write('<symbol> } </symbol>\n</whileStatement>\n')

    def compile_return(self):
        'Compiles a return statement. Is only called by compile_statements.'
        # current token is 'return'
        self.outfile.write('<returnStatement>\n<keyword> return </keyword>\n')
        self.tokenizer.advance()
        # current token may be the beginning of an expression, or ';'
        if self.tokenizer.current_token != ';':
            self.compile_expression()  # this advances the token
        # current token is ';'
        self.outfile.write('<symbol> ; </symbol>\n</returnStatement>\n')

    def compile_if(self):
        """Compiles an if statement, possibly with a trailing else clause.
        Is only called by compile_statements."""
        # current token is 'if'
        self.outfile.write('<ifStatement>\n<keyword> if </keyword>\n')
        self.tokenizer.advance()
        # current token is '('
        self.outfile.write('<symbol> ( </symbol>\n')
        self.tokenizer.advance()
        # current token is the beginning of an expression
        self.compile_expression()  # this advances the token
        # current token is ')'
        self.outfile.write('<symbol> ) </symbol>\n')
        self.tokenizer.advance()
        # current token is '{'
        self.outfile.write('<symbol> { </symbol>\n')
        self.tokenizer.advance()
        # current token must be the beginning of a list of statements
        token = self.tokenizer.current_token
        self.compile_statements(token)  # this advances the token
        # after statements list, current token must be '}'
        self.outfile.write('<symbol> } </symbol>\n')
        # next token might be 'else'
        next_token = self.tokenizer.tokens[0]
        if next_token == 'else':
            self.tokenizer.advance()
            # current token is 'else'
            self.outfile.write('<keyword> else </keyword>\n')
            self.tokenizer.advance()
            # current token is '{'
            self.outfile.write('<symbol> { </symbol>\n')
            self.tokenizer.advance()
            # current token is the start of a list of statements
            token = self.tokenizer.current_token
            self.compile_statements(token)  # this advances the token
            # current token is '}'
            self.outfile.write('<symbol> } </symbol>\n')
        # after a possible else clause, finish and quit
        self.outfile.write('</ifStatement>\n')

    def compile_expression(self):
        """Compiles an expression."""
        self.outfile.write('<expression>\n')
        # current token is the beginning of a term
        self.compile_term()  # does NOT advance token
        self.tokenizer.advance()
        # if current token is an operator, expression has form: term op term
        token = self.tokenizer.current_token
        if token in OP:
            if token == '<':
                self.outfile.write('<symbol> &lt; </symbol>\n')
            elif token == '>':
                self.outfile.write('<symbol> &gt; </symbol>\n')
            elif token == '"':
                self.outfile.write('<symbol> &quot; </symbol>\n')
            elif token == '&':
                self.outfile.write('<symbol> &amp; </symbol>\n')
            else:
                self.outfile.write('<symbol> ' + token + ' </symbol>\n')
            self.tokenizer.advance()
            # now current token is the beginning of a term
            self.compile_term()
            self.tokenizer.advance()
        self.outfile.write('</expression>\n')

    def compile_term(self):
        """Compiles a term."""
        self.outfile.write('<term>\n')
        token = self.tokenizer.current_token
        next_token = self.tokenizer.tokens[0]  # to check for subroutine calls
        # if token is '-' or '~' term has form: unaryOperator term
        if token in UNARYOP:
            self.outfile.write('<symbol> ' + token + ' </symbol>\n')
            self.tokenizer.advance()
            # now current token is the beginning of a term
            self.compile_term()
        # if token is '(' term has form: ( expression )
        elif token == '(':
            self.outfile.write('<symbol> ( </symbol>\n')
            self.tokenizer.advance()
            # now current token is the beginning of an expression
            self.compile_expression()  # this advances token
            # current token is ')'
            self.outfile.write('<symbol> ) </symbol>\n')
        # if next_token is '(' or '.' then term is a subroutine call
        elif next_token == '(':
            # current token is an identifier (subroutine name)
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
            # current token is '('
            self.outfile.write('<symbol> ( </symbol>\n')
            self.tokenizer.advance()
            # current token is the beginning of an expression list
            self.compile_expression_list()  # this advances the token
            # after the expression list is done, current token is ')'
            self.outfile.write('<symbol> ) </symbol>\n')
        elif next_token == '.':
            # current token is an identifier (class name or var name)
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
            # current token is '.'
            self.outfile.write('<symbol> . </symbol>\n')
            self.tokenizer.advance()
            # current token is an identifier (subroutine name)
            token = self.tokenizer.current_token
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
            # current token is '('
            self.outfile.write('<symbol> ( </symbol>\n')
            self.tokenizer.advance()
            # current token is the beginning of an expression list
            self.compile_expression_list()  # this advances the token
            # after the expression list is done, current token is ')'
            self.outfile.write('<symbol> ) </symbol>\n')
        # if next_token is '[' then term has form: varname[expression]
        elif next_token == '[':
            # current token is an identifier (var name)
            self.outfile.write('<identifier> ' + token + ' </identifier>\n')
            self.tokenizer.advance()
            # current token is '['
            self.outfile.write('<symbol> [ </symbol>\n')
            self.tokenizer.advance()
            # current token is the beginning of an expression
            self.compile_expression()  # this advances the token
            # after the expression is done, current token is ']'
            self.outfile.write('<symbol> ] </symbol>\n')
        else:  # just a simple term
            tokentype = self.tokenizer.tokentype()
            self.outfile.write('<' + tokentype + '> ')
            if tokentype == 'stringConstant':
                self.outfile.write(token[1:-1])  # remove the double quotes
            else:
                self.outfile.write(token)
            self.outfile.write(' </' + tokentype + '>\n')
        self.outfile.write('</term>\n')

    def compile_expression_list(self):
        """Compiles a possibly empty comma-separated list of expressions."""
        self.outfile.write('<expressionList>\n')
        # if current token is ')' then the expression list is empty
        while self.tokenizer.current_token != ')':
            # current token may be a ',' that separates expressions
            if self.tokenizer.current_token == ',':
                self.outfile.write('<symbol> , </symbol>\n')
                self.tokenizer.advance()
            # current token is the beginning of an expression
            self.compile_expression()  # this advances the token
        # once current token is ')', finish and quit
        self.outfile.write('</expressionList>\n')


def get_files(file_or_dir):
    """Takes string as input. String is file name or directory name.
    Returns file name or list of .jack file names in directory. """
    # if input is a file name
    if file_or_dir.endswith('.jack'):
        return [file_or_dir]

    # elif input is a directory name
    infiles = []
    for filename in os.listdir(file_or_dir):
        if filename.endswith('.jack'):
            infiles.append(filename)
    return infiles


def main():
    """Parses Jack source code into XML format.
    Can do this for single .jack file or all .jack files in directory,
    in the latter case writes to separate .xml output files. """

    if len(sys.argv) == 1:
        infiles = get_files(os.getcwd())
    elif len(sys.argv) == 2:
        infiles = get_files(sys.argv[1])
    else:
        print("Usage: python jack_parser.py file.jack")
        print("For the entire directory, use python jack_parser.py instead")

    # go through list of .jack files, parse each, write to .xml file
    for filename in infiles:
        # create outfile name
        outfile = filename.replace('.jack', '.xml')

        # create parser object for file
        parser = JackParser(filename, outfile)

        # tokenize file
        parser.tokenizer.tokenize()

        # write tokens to separate output file (ends with 'T.xml')
        # parser.tokenizer.write_tokens(filename)

        # using the tokens, parse file (entire class), write to output file
        parser.compile_class()


# run main
main()
