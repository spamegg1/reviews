# Translates Jack source code to VM code
# spamegg 3/8/2019
'Converts .jack files to .vm files'

import os
import sys
import jack_tokenizer
import vmwriter
import symbol_table

STATEMENTS = ['do', 'while', 'let', 'if', 'return']
UNARYOP = ['-', '~']
OP = ['+', '-', '*', '/', '&', '|', '<', '>', '=']
CLASSVAR = ['static', 'field']
SUB = ['method', 'constructor', 'function']
CONSTANTS = ['null', 'true', 'false']


class CompilationEngine():
    'Creates a compiler object for Jack source code.'

    def __init__(self, infile, outfile):
        """Takes input and output filenames as input.
        Creates an object and gets ready to compile input file and to
        write to output .vm file. """
        # create tokenizer from input file
        self.tokenizer = jack_tokenizer.JackTokenizer(infile)

        # create SymbolTable object
        self.table = symbol_table.SymbolTable()

        # create VMWriter object for outfile (this opens outfile for writing)
        self.writer = vmwriter.VmWriter(outfile)

        # variable to hold the class name e.g. 'Main' or 'SquareGame'
        self.classname = ''

    def compile_class(self):
        """Compiles an entire class in a Jack source file."""
        self.tokenizer.advance()
        # current token is 'class'
        self.tokenizer.advance()
        # current token is the class name, e.g. 'Main'
        self.classname = self.tokenizer.current_token
        self.tokenizer.advance()
        # current token is '{'
        self.tokenizer.advance()
        # next, handle class var declarations (add vars to symbol table)
        while self.tokenizer.current_token in CLASSVAR:
            self.compile_class_var_dec()  # writes no VM code
            self.tokenizer.advance()
        # once class var declarations are done, handle subroutine declarations
        while self.tokenizer.current_token in SUB:
            self.compile_subroutine()
            self.tokenizer.advance()
        # current token must be '}'
        self.writer.outfile.close()

    def compile_class_var_dec(self):
        """Compiles a static variable or field variable declaration."""
        # current token is either 'static' or 'field' (the kind)
        kind = self.tokenizer.current_token
        self.tokenizer.advance()
        # current token is 'int', 'boolean', 'char' or a class name (type)
        datatype = self.tokenizer.current_token
        self.tokenizer.advance()
        # current token is an identifier (the name of the variable)
        name = self.tokenizer.current_token
        self.tokenizer.advance()
        # add this variable to symbol table
        self.table.define(name, datatype, kind)
        # if current token is ',' there are more than one variables
        while self.tokenizer.current_token == ',':
            self.tokenizer.advance()
            # current token is an identifier (var name)
            name = self.tokenizer.current_token
            # add this variable to symbol table (kind and type are the same)
            self.table.define(name, datatype, kind)
            self.tokenizer.advance()
        # once variables are done, current token is ';'

    def compile_subroutine(self):
        """Compiles a complete method, function or constructor."""
        # reset the subroutine symbol table first, to start new subroutine
        self.table.start_subroutine()
        # current token is 'function', 'method' or 'constructor'
        subtype = self.tokenizer.current_token
        # if subtype is 'method', add 'this' object as argument to symbol table
        if subtype == 'method':
            self.table.define('this', self.classname, 'argument')
        self.tokenizer.advance()
        # current token is 'void', 'int', 'char', 'boolean' or some class name
        self.tokenizer.advance()
        # current token is an identifier (name of subroutine), e.g. 'getLength'
        name = self.tokenizer.current_token
        self.tokenizer.advance()
        # current token is '('
        self.tokenizer.advance()
        # current token is the start of a parameter list, or ')'
        self.compile_parameter_list()  # writes no VM code
        nargs = 0  # counts local vars
        # after parameter list, current token must be ')'
        self.tokenizer.advance()
        # current token is '{', this begins the subroutine body
        self.tokenizer.advance()
        # subroutine body is a list of LOCAL var declarations and statements
        while self.tokenizer.current_token == 'var':
            nargs += self.compile_var_dec()  # writes no VM code, updates table
            self.tokenizer.advance()
        # up to this point, no VM code has been written
        # write VM code for function label
        self.writer.write_function(self.classname + '.' + name, nargs)
        # if subroutine is a constructor, allocate memory for field vars
        if subtype == 'constructor':
            mem_needed = self.table.var_count('field')
            # write VM code to allocate memory for the fields of the new object
            self.writer.write_push('constant', mem_needed)
            self.writer.write_call('Memory.alloc', 1)
            # anchor 'this' at the base address of allocated space
            self.writer.write_pop('pointer', 0)
        # if subroutine is a method, set base of THIS segment to argument 0
        if subtype == 'method':
            # 'this' object is always stored as argument 0 in symbol table
            self.writer.write_push('argument', 0)
            # write VM code so that THIS segment points to the 'this' object
            self.writer.write_pop('pointer', 0)
        # once var declarations are done, handle statements
        self.compile_statements()
        # after var declarations and statements, current token must be '}'

    def compile_parameter_list(self):
        """Compiles a possibly empty parameter list, excluding ().
        Writes no VM code, only updates symbol tables."""
        # this is called only after prev token was '(' in a subroutine
        # if current token is ')', it's end of (or an empty) parameter list
        while self.tokenizer.current_token != ')':
            # current token is 'int', 'char', 'boolean' or a class name
            datatype = self.tokenizer.current_token
            self.tokenizer.advance()
            # current token is a parameter name, e.g. 'Asize'
            name = self.tokenizer.current_token
            # add this variable to the symbol table (the kind is 'argument')
            self.table.define(name, datatype, 'argument')
            self.tokenizer.advance()
            # if current token is ',' there are more parameters in list
            while self.tokenizer.current_token == ',':
                # current token is ','
                self.tokenizer.advance()
                # current token is 'int', 'char', 'boolean' or a class name
                datatype = self.tokenizer.current_token
                self.tokenizer.advance()
                # current token is a parameter name, e.g. 'Asize'
                name = self.tokenizer.current_token
                # add this variable to symbol table (the kind is 'argument')
                self.table.define(name, datatype, 'argument')
                self.tokenizer.advance()  # after this, current token is ')'

    def compile_var_dec(self):
        """Compiles a (local) variable declaration."""
        # current token is 'var', which means the kind is 'local'
        self.tokenizer.advance()
        # current token is 'int', 'char', 'boolean' or class e.g. 'SquareGame'
        datatype = self.tokenizer.current_token
        self.tokenizer.advance()
        # current token is an identifier (var name), e.g. 'game'
        name = self.tokenizer.current_token
        # add this variable to the symbol table (kind is 'local')
        self.table.define(name, datatype, 'local')
        nargs = 1
        self.tokenizer.advance()
        # if current token is ',' more than one var listed of this datatype
        while self.tokenizer.current_token == ',':
            # current token is ','
            self.tokenizer.advance()
            # current token is another identifier (var name), e.g. 'j'
            name = self.tokenizer.current_token
            # add this variable to the symbol table (datatype same as before)
            self.table.define(name, datatype, 'local')
            nargs += 1
            self.tokenizer.advance()  # after this, current token is ',' or ';'
        return nargs

    def compile_statements(self):
        """Compiles a sequence of statements, excluding {}."""
        # current token is 'do', 'while', 'let', 'if' or 'return'
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

    def compile_do(self):
        """Compiles a do statement."""
        # current token is 'do'
        self.tokenizer.advance()
        # current token is the beginning of a subroutineCall (term)
        self.compile_term()  # does NOT advance token
        self.tokenizer.advance()  # now current token is ';'
        # since subroutine calls in do statements are void, they return 0
        # this unnecessary constant must be popped from the stack
        self.writer.write_pop('temp', 0)

    def compile_let(self):
        """Compiles a let statement."""
        # current token is 'let'
        self.tokenizer.advance()
        # current token is an identifier (var name), look up in symbol table
        name = self.tokenizer.current_token  # 'wall'
        index = self.table.index_of(name)  # 2
        kind = self.table.kind_of(name)  # 'field'
        segment = 'this' if kind == 'field' else kind  # 'this'
        self.tokenizer.advance()
        # current token is either '[' or '='
        if self.tokenizer.current_token == '[':  # array access
            self.tokenizer.advance()
            # now current token is the beginning of expression1
            self.compile_expression()  # this advances token, pushes exp1 value
            # after expression1 is done, current token is ']'
            # write VM code to push base address of array to stack
            self.writer.write_push(segment, index)  # e.g. 'push local 0'
            # write VM code to add base address of array to evaluated exp1
            self.writer.write_arithmetic('+')
            self.tokenizer.advance()
            # current token is '='
            self.tokenizer.advance()
            # now current token is the beginning of expression2
            self.compile_expression()  # this advances token, pushes exp2 value
            # now the evaluated expression2 value is pushed onto the stack
            # write VM code to pop this exp2 value to TEMP memory segment
            self.writer.write_pop('temp', 0)
            # write VM code to point THAT memory segment to (array base + exp1)
            self.writer.write_pop('pointer', 1)
            # write VM code to push exp2 value from TEMP to the stack
            self.writer.write_push('temp', 0)
            # write VM code to pop this value to THAT 0
            self.writer.write_pop('that', 0)
            # after the expression is done, current token is ';'
        else:
            # current token is '='
            self.tokenizer.advance()
            # now current token is the beginning of an expression ball.move()
            self.compile_expression()  # this advances the token, pushes value
            # now the evaluated expression value is pushed onto the stack
            # write VM code to pop the evaluated expression value to the variable
            self.writer.write_pop(segment, index)
            # after the expression is done, current token is ';'

    def compile_while(self):
        """Compiles a while statement. Is only called by compile_statements."""
        # while statement has form: while(exp){stats}
        # below, label1 corresponds to stats, label2 to exiting while loop
        # current token is 'while'
        self.tokenizer.advance()
        # current token is '('
        self.tokenizer.advance()
        # write VM code for: 'label1'
        label1 = 'WHILE_EXP' + str(self.writer.while_exp)
        self.writer.write_label(label1)
        self.writer.while_exp += 1
        # current token is the beginning of an expression
        self.compile_expression()  # this advances the token
        # write VM code to negate the above expression
        self.writer.write_arithmetic('~')
        # after the expression is done, current token is ')'
        self.tokenizer.advance()
        # current token is '{'
        self.tokenizer.advance()
        # write VM code for: (when exp is false) 'if-goto label2'
        label2 = 'WHILE_END' + str(self.writer.while_end)
        self.writer.write_if(label2)
        self.writer.while_end += 1
        # current token is the beginning of a list of statements
        self.compile_statements()  # this advances the token
        # after statements are done, current token is '}'
        # write VM code for: 'goto label1'
        self.writer.write_goto(label1)
        # write VM code for: 'label2'
        self.writer.write_label(label2)

    def compile_return(self):
        'Compiles a return statement. Is only called by compile_statements.'
        # current token is 'return'
        self.tokenizer.advance()
        # current token may be the beginning of an expression, or ';'
        token = self.tokenizer.current_token
        # if return statement is 'return this;' it's a constructor subroutine
        # write VM code to return base address of new object to caller
        if token == 'this':
            self.writer.write_push('pointer', 0)
            self.tokenizer.advance()  # now current token is ';'
        elif token == ';':
            # void return value, write VM code to push constant 0
            self.writer.write_push('constant', 0)  # current token is still ';'
        # otherwise, a normal expression is returned, evaluate it
        else:
            self.compile_expression()  # this advances token, pushes expr value
        # current token is ';', write VM code for: 'return'
        self.writer.write_return()

    def compile_if(self):
        """Compiles an if statement, possibly with a trailing else clause.
        Is only called by compile_statements."""
        # if statement has form: if(exp){stats1} else{stats2}
        # below, label1 corresponds to stats1, label2 corresponds to stats2
        # current token is 'if'
        self.tokenizer.advance()
        # current token is '('
        self.tokenizer.advance()
        # current token is the beginning of an expression
        self.compile_expression()  # this advances the token
        # current token is ')'
        self.tokenizer.advance()
        # current token is '{'
        self.tokenizer.advance()
        # write VM code for: (if exp is true) 'if-goto label1'
        label1 = 'IF_TRUE' + str(self.writer.if_true)
        self.writer.write_if(label1)
        self.writer.if_true += 1
        # write VM code for: (when exp is false) 'goto label2',
        # (and then run stats2 when there is an else clause)'
        label2 = 'IF_FALSE' + str(self.writer.if_false)
        self.writer.write_goto(label2)
        self.writer.if_false += 1
        # write VM code for: label1 (IF_TRUE)
        self.writer.write_label(label1)
        # current token is the start of a list of statements (stats1)
        self.compile_statements()  # this advances the token
        # after statements list, current token must be '}'
        # next token (after '}') might be 'else'
        next_token = self.tokenizer.tokens[0]
        if next_token == 'else':
            # write VM code for: 'goto IF_END'
            label3 = 'IF_END' + str(self.writer.if_end)
            self.writer.write_goto(label3)
            self.writer.if_end += 1
            self.tokenizer.advance()
            # current token is 'else'
            self.tokenizer.advance()
            # current token is '{'
            self.tokenizer.advance()
            # write VM code for: label2 (IF_FALSE)
            self.writer.write_label(label2)
            # current token is the start of a list of statements (stats2)
            self.compile_statements()  # this advances the token
            # current token is '}'
            # write VM code for label3 (IF_END)
            self.writer.write_label(label3)
        else:  # there is no else clause, current token is '}'
            # write VM code for label2 (IF_FALSE)
            self.writer.write_label(label2)

    def compile_expression(self):
        """Compiles an expression.
        Advances token and pushes value to the stack at the end."""
        # current token is the beginning of a term
        self.compile_term()  # does NOT advance token
        self.tokenizer.advance()
        # if current token is an operator, expression has form: term op term
        token = self.tokenizer.current_token  # '+'
        if token in OP:
            # current token is an operator
            self.tokenizer.advance()
            # now current token is the beginning of a term
            self.compile_term()  # does NOT advance token
            self.tokenizer.advance()
            # write VM code for the operator (such as 'add' or 'sub')
            self.writer.write_arithmetic(token)

    def compile_term(self):
        """Compiles a term."""
        token = self.tokenizer.current_token
        next_token = self.tokenizer.tokens[0]
        datatype = self.table.type_of(token)
        kind = self.table.kind_of(token)
        segment = 'this' if kind == 'field' else kind
        index = self.table.index_of(token)
        # if token is '-' or '~' term has form: unaryOperator term
        if token in UNARYOP:
            self.tokenizer.advance()
            # now current token is the beginning of a term
            self.compile_term()  # does NOT advance token
            # after the term is done, write VM code for unary operator
            if token == '-':
                self.writer.outfile.write('neg\n')
            elif token == '~':
                self.writer.write_arithmetic(token)
        # if token is '(' term has form: ( expression )
        elif token == '(':
            self.tokenizer.advance()
            # now current token is the beginning of an expression
            self.compile_expression()  # this advances token
            # current token is ')'
        # if next_token is '(' or '.' then term is a subroutine call
        elif next_token == '(':  # method call
            # token is subroutine name, e.g. 'draw' or 'reduce'
            self.tokenizer.advance()
            # now current token is '('
            self.tokenizer.advance()
            # write VM code to push the start of THIS segment to stack
            self.writer.write_push('pointer', 0)
            # now handle the expression list inside the parentheses
            nargs = self.compile_expression_list()
            # after expr list, current token should be ')'
            # write VM code to call the method
            self.writer.write_call(self.classname + '.' + token, nargs + 1)
        elif next_token == '.':
            # current token is a var e.g. 'game', or class e.g. 'SquareGame'
            self.tokenizer.advance()
            # current token is '.'
            self.tokenizer.advance()
            # current token is an identifier (method call)
            method = self.tokenizer.current_token  # 'move'
            self.tokenizer.advance()
            # current token is '('
            self.tokenizer.advance()
            # if token is a var (object) it must first be pushed to stack
            if datatype is not None:
                # write VM code to push object to stack
                self.writer.write_push(segment, index)  # push this 1
                # now handle the expression list inside the parentheses
                nargs = self.compile_expression_list()
                # after expr list, current token should be ')'
                # now write VM code to call datatype.method
                self.writer.write_call(datatype + '.' + method, nargs + 1)
            else:  # token is a class name e.g. 'Keyboard'
                # now handle the expression list inside the parentheses
                nargs = self.compile_expression_list()
                # after expr list, current token should be ')'
                # write VM code to call classname.method/function
                self.writer.write_call(token + '.' + method, nargs)
        # if next_token is '[' then term has form: varname[expression]
        elif next_token == '[':  # array access
            # current token is an identifier (var name) 'a'
            kind = self.table.kind_of(token)
            segment = 'this' if kind == 'field' else kind
            index = self.table.index_of(token)
            self.tokenizer.advance()
            # current token is '['
            self.tokenizer.advance()
            # current token is the beginning of an expression
            self.compile_expression()  # this advances token, pushes exp value
            # write VM code to push base address of array to stack
            self.writer.write_push(segment, index)  # push local 0 (array 'a')
            # write VM code to sum base address of array with exp value
            self.writer.write_arithmetic('+')
            # write VM code to pop the a[i] address to THAT 0
            self.writer.write_pop('pointer', 1)
            # write VM code to now push this value
            self.writer.write_push('that', 0)
            # after the expression is done, current token is ']'
        else:  # just a simple term
            tokentype = self.tokenizer.tokentype()  # identifier
            # if token is one of 3 special Jack constants
            if token in CONSTANTS:
                if token == 'null' or token == 'false':
                    self.writer.write_push('constant', 0)
                elif token == 'true':
                    self.writer.write_push('constant', 0)
                    self.writer.outfile.write('not\n')
            elif token == 'this':
                self.writer.write_push('pointer', 0)
            elif tokentype == 'integerConstant':
                self.writer.write_push('constant', token)
            elif tokentype == 'stringConstant':
                # write VM code to push constant for length of string
                self.writer.write_push('constant', len(token) - 2)
                # write VM code to call String.new
                self.writer.write_call('String.new', 1)
                # write VM code to append rest of the characters
                for letter in token[1:-1]:  # avoid quotation marks
                    self.writer.write_push('constant', ord(letter))
                    self.writer.write_call('String.appendChar', 2)
            elif tokentype == 'identifier':
                # look up token in symbol table
                index = self.table.index_of(token)  # 3
                kind = self.table.kind_of(token)  # 'local'
                segment = 'this' if kind == 'field' else kind
                # write VM code for variable (token)
                self.writer.write_push(segment, index)  # push local 3

    def compile_expression_list(self):
        """Compiles a possibly empty comma-separated list of expressions."""
        number_of_exps = 0
        # if current token is ')' then the expression list is empty
        while self.tokenizer.current_token != ')':
            # current token may be a ',' that separates expressions
            if self.tokenizer.current_token == ',':
                self.tokenizer.advance()
            # current token is the beginning of an expression
            self.compile_expression()  # this advances the token
            number_of_exps += 1
        # once current token is ')', finish and quit
        return number_of_exps


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
    """Compiles Jack source code to VM code.
    Can do this for single .jack file or all .jack files in directory,
    in the latter case writes to separate .vm output files. """

    if len(sys.argv) == 1:
        infiles = get_files(os.getcwd())
    elif len(sys.argv) == 2:
        infiles = get_files(sys.argv[1])
    else:
        print("Usage: python compilation_engine.py filename.jack")
        print("For the entire directory, use: python compilation_engine.py")

    # go through list of .jack files, parse each, write to .vm file
    for filename in infiles:
        # create outfile name
        outfile = filename.replace('.jack', '.vm')

        # create parser object for file
        compiler = CompilationEngine(filename, outfile)

        # tokenize file
        compiler.tokenizer.tokenize()

        # using the tokens, translate file (entire class), write to output file
        compiler.compile_class()


# run main
main()
