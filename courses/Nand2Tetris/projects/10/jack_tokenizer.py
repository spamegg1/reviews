# Tokenizes Jack source code
# spamegg 1/31/2019
'Tokenizer for Jack source code'
import os
import sys

KEYWORDS = ['class', 'constructor', 'function', 'method', 'field', 'static',
            'var', 'int', 'char', 'boolean', 'void', 'true', 'false', 'null',
            'this', 'let', 'do', 'if', 'else', 'while', 'return']

SYMBOLS = ['{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/',
           '&', '|', '<', '>', '=', '~']


class JackTokenizer():
    """Creates a tokenizer object for Jack source code."""

    def __init__(self, filename):
        """Create a tokenizer object.
        Opens input file and gets ready to tokenize."""
        self.infile = open(filename, 'r')
        self.tokens = []  # list of (tokentype, token) pairs, initially empty
        self.current_token = None  # current token, initialized to None

    def has_more_tokens(self):
        """Returns True if there are still more tokens to go through."""
        return bool(self.tokens)

    def advance(self):
        """Advances current token if there are more tokens."""
        self.current_token = self.tokens.pop(0)

    def tokentype(self):
        """Returns type of current token."""
        if self.current_token.find('"') == 0:
            return 'stringConstant'
        if self.current_token in KEYWORDS:
            return 'keyword'
        if self.current_token in SYMBOLS:
            return 'symbol'
        if self.current_token.isdecimal():
            return 'integerConstant'
        return 'identifier'

    def tokenize(self):
        """Tokenizes input file. Returns list of tokens."""
        # initialize token list to be returned
        tokens = []

        for line in self.infile:
            # split line by spaces, then remove comments
            line_without_comments = remove_comments(line.split())

            # take care of string constants, update list
            line_without_comments = handle_strings(line_without_comments)

            # make sure line was not empty
            if line_without_comments:
                # examine symbols in each word in list
                for word in line_without_comments:
                    tokens += split_symbols(word)

        # update object state with new list of tokens
        self.tokens = tokens

        return tokens

    def write_tokens(self, filename):
        """Writes tokens to output file in XML format."""
        # create outfile
        outfile = open(filename.replace('.jack', 'T.xml'), 'w')

        # begin writing
        outfile.write('<tokens>\n')

        # check if there are still more tokens to go through
        while self.has_more_tokens():
            # advance to next token
            self.advance()

            # get tokentype of current token
            tokentype = self.tokentype()

            # write in XML format
            outfile.write('<' + tokentype + '> ')

            # symbols and string constants need special handling
            if tokentype == 'symbol':
                write_symbol(self.current_token, outfile)
            elif tokentype == 'stringConstant':
                write_string(self.current_token, outfile)
            else:
                outfile.write(self.current_token)

            outfile.write(' </' + tokentype + '>\n')

        # finish writing
        outfile.write('</tokens>')
        outfile.close()


def write_symbol(token, outfile):
    """Writes Jack symbol tokens without conflict with HTML."""
    if token == '<':
        outfile.write('&lt;')
    elif token == '>':
        outfile.write('&gt;')
    elif token == '"':
        outfile.write('&quot;')
    elif token == '&':
        outfile.write('&amp;')
    else:
        outfile.write(token)


def write_string(token, outfile):
    """Writes Jack string tokens without the double quotes."""
    outfile.write(token[1:-1])


def remove_comments(line_splitted):
    """Removes comments from list. Everything after //, or in between /* and
    */ , or /** and */ is removed. """
    # initialize the list to be returned
    line_without_comments = line_splitted.copy()

    # make sure line was not empty to begin with
    if line_without_comments:
        # if line is part of a multiline comment, it starts with * or */
        if line_without_comments[0] in ['*', '*/']:
            return []  # remove whole line

        # First remove comments in between /* (or /**) and */
        i = 0
        while i < len(line_without_comments):
            if line_without_comments[i] in ['/*', '/**']:
                line_without_comments.pop(i)
                # make sure line isn't empty now
                if line_without_comments:
                    while line_without_comments[i] != '*/':
                        line_without_comments.pop(i)
                    if line_without_comments[i] == '*/':
                        line_without_comments.pop(i)
            else:
                i += 1

        # then remove everything after an occurence of //
        for i, elt in enumerate(line_without_comments):
            if elt == '//':
                line_without_comments = line_without_comments[0:i]
                break

    return line_without_comments


def split_symbols(word):
    """Takes string as input, returns list of strings from original string
    separated by symbols.
    Example: 'Square.new(0,' -> ['Square', '.', 'new', '(', '0', ','] """
    # initialize list to be returned
    tokens = []

    # go through word letter by letter, look for symbols
    i = 0  # loop index var
    j = 0  # keeps track of the end of last token found
    k = 0  # keeps track of the start of very last token
    while i < len(word):
        if word[i] in SYMBOLS:
            if j != i:
                tokens.append(word[j:i])
            tokens.append(word[i])
            j = i + 1  # beginning of next token
            k = j  # very last token after the very last symbol occurence
        i += 1

    # add the very last token that comes after the very last symbol occurence
    if k < len(word):
        tokens.append(word[k:])

    return tokens


def handle_strings(line_without_comments):
    """Takes list of strings as input. Assumes input is the result of splitting
    a longer string by spaces. Tracks portion of longer string in between
    double quotes. Returns a modified list where portions between double quotes
    are put together. Example:
    ['let', 'a[i]', '=', 'Keyboard.readInt("ENTER', 'NEXT', 'NUMBER:', '");']
    returns
    ['let', 'a[i]', '=', 'Keyboard.readInt(', '"ENTER NEXT NUMBER: "', ');']
    """
    # initialize new list to be returned
    result = []

    # go through input list
    i = 0  # loop var
    j = 0  # keeps track of the beginning of double quoted portion
    k = 0  # keeps track of the end of double quoted portion
    while i < len(line_without_comments):
        # store ith word in list in a variable
        word = line_without_comments[i]
        # if ith word contains double quotes
        if '"' in word:
            # find first occurence of double quotes in word
            j = word.find('"')

            # add slice of word up to j as a new token to result list
            result.append(word[:j])

            # look through rest of word for double quotes
            if '"' in word[j + 1:]:
                # find second occurence of double quotes in word
                k = word[j + 1:].find('"')

                # add slice from j to k (in quotes) as new token to result list
                result.append(word[j:k + 1])

                # add the rest of word as new token to result list
                result.append(word[k + 1:])

                i += 1
            else:
                # save slice of word from j to end
                merger = word[j:]
                # look through rest of list for double quotes
                while '"' not in line_without_comments[i + 1]:
                    # until double quote is found, keep popping, merging words
                    merger += ' ' + line_without_comments.pop(i + 1)

                # eventually double quote will be found
                if '"' in line_without_comments[i + 1]:
                    newword = line_without_comments[i + 1]
                    # finds its occurence
                    k = newword.find('"')

                    # keep merging
                    merger += ' ' + newword[:k + 1]

                    # add merger (in quotes) as new token to result list
                    result.append(merger)

                    # add rest of newword as new token to result list
                    result.append(newword[k + 1:])

                    # we handled i+1st word, so increment i by 2
                    i += 2
        else:
            # no double quote found, just add word to list
            result.append(word)
            i += 1
    return result


def main():
    """Tokenizes Jack source code lines into XML format.
    Can do this for single .jack file or all .jack files in directory,
    in the latter case writes to separate .xml output files. """

    if len(sys.argv) == 1:
        infiles = get_files(os.getcwd())
    elif len(sys.argv) == 2:
        infiles = get_files(sys.argv[1])
    else:
        print("Usage: python jack_tokenizer.py file.jack")
        print("For the entire directory, use python jack_tokenizer.py instead")

    # go through list of .jack files, tokenize each, write to .xml file
    for filename in infiles:
        # create tokenizer object for file
        tokenizer = JackTokenizer(filename)

        # tokenize file
        tokenizer.tokenize()

        # write tokens to output file
        tokenizer.write_tokens(filename)


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


# run main
main()
