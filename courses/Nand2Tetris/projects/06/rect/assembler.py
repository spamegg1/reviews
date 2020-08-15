# Assembler for HACK assembly language
# Converts Hack Assembly code into Hack Binary machine code
# spamegg 12/30/2018


# This is the dictionary for a C-instruction's Comp portion
COMP_DICT = {
    '0': '0101010',
    '1': '0111111',
    '-1': '0111010',
    'D': '0001100',
    'A': '0110000',
    '!D': '0001101',
    '!A': '0110001',
    '-D': '0001111',
    '-A': '0110011',
    'D+1': '0011111',
    'A+1': '0110111',
    'D-1': '0001110',
    'A-1': '0110010',
    'D+A': '0000010',
    'D-A': '0010011',
    'A-D': '0000111',
    'D&A': '0000000',
    'D|A': '0010101',
    'M': '1110000',
    '!M': '1110001',
    '-M': '1110011',
    'M+1': '1110111',
    'M-1': '1110010',
    'D+M': '1000010',
    'D-M': '1010011',
    'M-D': '1000111',
    'D&M': '1000000',
    'D|M': '1010101'
}

# This is the dictionary for a C-instruction's Dest portion
DEST_DICT = {
    '': '000',
    'M': '001',
    'D': '010',
    'MD': '011',
    'A': '100',
    'AM': '101',
    'AD': '110',
    'AMD': '111'
}

# This is the dictionary for a C-instruction's Jump portion
JUMP_DICT = {
    '': '000',
    'JGT': '001',
    'JEQ': '010',
    'JGE': '011',
    'JLT': '100',
    'JNE': '101',
    'JLE': '110',
    'JMP': '111'
}

# This is the dictionary for the predefined symbols of the Hack Assembly code
PREDEF_SYMBOLS = {
    'R0': 0,
    'R1': 1,
    'R2': 2,
    'R3': 3,
    'R4': 4,
    'R5': 5,
    'R6': 6,
    'R7': 7,
    'R8': 8,
    'R9': 9,
    'R10': 10,
    'R11': 11,
    'R12': 12,
    'R13': 13,
    'R14': 14,
    'R15': 15,
    'SCREEN': 16384,
    'KBD': 24576,
    'SP': 0,
    'LCL': 1,
    'ARG': 2,
    'THIS': 3,
    'THAT': 4
}


def hack_to_bin(infile):
    """Takes a file name such as 'mycode.asm' as input (which needs to be in
    the current directory).
    Produces another file 'mycode.hack' that is the translation of input file
    from Hack Assembly to Hack Binary."""

    # First, go through the entire file once, to build the symbol table
    symbol_table = build_symbol_table(infile)

    # Second, go through the entire file again, to build the variable table
    variable_table = build_variable_table(infile, symbol_table)

    # open the .asm file
    asmfile = open(infile, "r")

    # get file name of .hack file to write into (remove .asm, add .hack)
    filename = infile.split('.')[0] + '.hack'

    # create output .hack file to write into
    hackfile = open(filename, "w")

    # PARSING
    line_counter = 0
    # read asmfile one line at a time:
    for line in asmfile:
        # split the line, by space, into a list of words
        line_splitted = line.split()

        # remove all the comments from the list (everything after //)
        line_without_comments = remove(line_splitted, '//')

        # remove all the pseudo commands (like (LOOP)) from the list
        line_without_pseudo = remove(line_without_comments, '(')

        # if it is an empty line (whitespace), or just a comment line,
        # or a pseudo-command, skip this line
        if line_without_pseudo:
            # Write a newline, if not at the very first line
            if line_counter != 0:
                hackfile.write('\n')

            # merge all the words into a single word, no spaces
            line_merged = ''.join(line_without_pseudo)

            # if the line is an A-instruction, it starts with @
            if line_merged[0] == '@':
                # get the rest of the instruction
                instruction = line_merged[1:]

                # calculate the 16-bit value that needs to be written
                value = handle_A_instruction(
                    instruction, symbol_table, variable_table)

                # then write the 16-bit binary code
                hackfile.write(decimal_to_binary(value))

            # else, it's a C-instruction of the form dest=comp;jump
            # one of the = or the ; might be absent, or both present
            else:
                # Parse the C-instruction into its dest, comp, jump fields
                [dest, comp, jump] = split_C_instruction(line_merged)

                # Write binary code for the C-instruction
                write_C_instruction(hackfile, dest, comp, jump)

            # increment line counter
            line_counter += 1

    # Close both files
    asmfile.close()
    hackfile.close()


def build_symbol_table(infile):
    """Takes an open file as input, returns dictionary for label symbols
    of pseudo commands such as (LOOP) or (END) etc. """

    symbols = {}
    line_counter = 0

    asmfile = open(infile, 'r')

    for line in asmfile:
        # split the line, by space, into a list of words
        line_splitted = line.split()

        # remove all the comments from the list
        line_without_comments = remove(line_splitted, '//')

        # if it was an empty line, or just a comment line,
        # the remaining list will be empty
        # ensure the list is not empty, otherwise skip this line
        if line_without_comments:
            # merge all the words into a single word, no spaces
            line_merged = ''.join(line_without_comments)
            firstchar = line_merged[0]

            # if the line is a pseudo-instruction like (LOOP) it begins with (
            if firstchar == '(':
                # get the label symbol by leaving out the parentheses
                label = line_merged[1:-1]

                # add entry to Symbol dictionary
                symbols[label] = line_counter

            # otherwise, the line is a "real" instruction
            else:
                line_counter += 1

    asmfile.close()
    return symbols


def build_variable_table(infile, symbols):
    """Takes an open file and a dictionary as input.
    Builds a dictionary of variable addresses starting at RAM[16]. """

    variables = {}
    variable_counter = 16

    asmfile = open(infile, 'r')

    for line in asmfile:
        # split the line, by space, into a list of words
        line_splitted = line.split()

        # remove all the comments from the list
        line_without_comments = remove(line_splitted, '//')

        # remove all the pseudo commands from the list
        line_without_pseudo = remove(line_without_comments, '(')

        # if it was an empty line, or just a comment line,
        # or a pseudo command, skip this line
        if line_without_pseudo:
            # merge all the words into a single word, no spaces
            line_merged = ''.join(line_without_pseudo)
            firstchar = line_merged[0]

            # if the line is an A-instruction it begins with @
            if firstchar == '@':
                # get the rest of the instruction
                instruction = line_merged[1:]

                # if instruction is not pre-defined, a label, or a number,
                # then it must be a variable, add it to the dictionary
                if instruction not in PREDEF_SYMBOLS \
                        and instruction not in symbols \
                        and instruction not in variables \
                        and not instruction.isnumeric():
                    variables[instruction] = variable_counter

                    # increment RAM location for next variable
                    variable_counter += 1

    asmfile.close()
    return variables


def remove(line_splitted, char):
    """Remove all the comments or pseudo-commands from the given list.
    Everything after an occurence of char is removed.
    Use char = '//' to remove comments, char = '(' for pseudo-commands."""

    line_without_comments = line_splitted

    for i in range(len(line_splitted)):
        if char in line_splitted[i]:
            line_without_comments = line_splitted[0:i]
            break

    return line_without_comments


def handle_A_instruction(instruction, symbols, variables):
    """Takes a 15-bit A-instruction, a symbol table, and a variable table as
    input. Returns the 16 bit value that will be written as binary code."""
    # if the instruction is a pre-defined symbol like @SCREEN,
    # look it up in Predef dictionary (value is Int or None)
    # for example @SCREEN -> @16384 -> 0010000000000000
    value = PREDEF_SYMBOLS.get(instruction)

    # if not, the instruction may be a label symbol like @LOOP
    # look it up in the Symbol table (value is Int or None)
    # for example @LOOP -> @4 -> 0000000000000100
    if value is None:
        value = symbols.get(instruction)

    # if not, the instruction may be a variable, like @sum or @n
    # look it up in the Variable table (value is Int or None)
    # for example @sum -> @16 -> 0000000000010000
    if value is None:
        value = variables.get(instruction)

    # otherwise, the instruction must be an address, like @15
    # for example, @10 -> 0000000000001010
    if value is None:
        value = int(instruction)
    return value


def write_C_instruction(outfile, dest, comp, jump):
    # the binary code is of the form 111 a cccccc ddd jjj
    # start by writing 111
    outfile.write('111')

    # Next, look up comp in the Comp dictionary
    # and write the 7 bit value: a cccccc
    outfile.write(COMP_DICT[comp])

    # Next, look up dest in the Dest dictionary
    # and write the 3 bit value ddd
    outfile.write(DEST_DICT[dest])

    # Next, look up jump in the Jump dictionary
    # and write the 3 bit value jjj
    outfile.write(JUMP_DICT[jump])


def split_C_instruction(line_merged):
    """Takes a string like 'dest=comp;jump' as input, where either 'dest='
    or ';jump' might be absent, and returns a list [dest, comp, jump]. """
    # both = and ; are present, all 3 fields nontrivial
    if '=' in line_merged and ';' in line_merged:
        # first split the line into 2 segments: dest=comp, and jump
        [destcomp, jump] = line_merged.split(';')

        # now split dest=comp into dest and comp
        [dest, comp] = destcomp.split('=')

    # only = present, jump is absent, we only have dest=comp
    elif '=' in line_merged and ';' not in line_merged:
        [dest, comp] = line_merged.split('=')
        jump = ''

    # only ; present, dest is absent, we only have comp;jump
    else:
        [comp, jump] = line_merged.split(';')
        dest = ''

    return [dest, comp, jump]


def decimal_to_binary(decimal):
    """Converts a decimal number into 16 bit binary number,
    padded with zeros on the left. """
    # Python's built-in bin function does this, for example
    # bin(10) = 0b1010

    # Remove the '0b' from the result
    binary = bin(decimal)[2:]

    # Count the number of digits in binary, then pad it with enough zeros
    digits = len(binary)
    return '0'*(16 - digits) + binary


# main
hack_to_bin('Rect.asm')
hack_to_bin('RectL.asm')
