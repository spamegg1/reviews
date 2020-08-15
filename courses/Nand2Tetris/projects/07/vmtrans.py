# VM Translator Main Program
# Translates Virtual Machine code into Hack Assembly Code
# spamegg  1/3/2019
'Translates Virtual Machine code lines into Hack Assembly Code'

import sys

# label counters for branching arithmetic commands eq, lt, gt:
EQ_COUNTER = 0
LT_COUNTER = 0
GT_COUNTER = 0


def main():
    """Translates Virtual Machine code lines into Hack Assembly Code.
    Accepts .vm file name (like file.vm) as command line input,
    writes the translations to an output file (like file.asm). """

    if len(sys.argv) != 2:
        print("Usage: python vmtrans.py file.vm")
    else:
        infile = sys.argv[1]

    # open input file
    vmfile = open(infile, 'r')

    # name of output file (remove .vm, add .asm)
    filename = infile.split('.')[0]
    outfile = filename + '.asm'

    # open output file to write
    asmfile = open(outfile, 'w')

    # write bootstrap code to asmfile
    # asmfile.write(write_init())

    # PARSING
    line_counter = 0
    for line in vmfile:
        # parse line by removing comments and splitting line into words
        line_without_comments = line.split('//', 1)[0]
        line_parsed = line_without_comments.split()

        # make sure the line was not blank or purely comment
        if line_parsed:
            # Write a newline, if not at the very first line
            if line_counter != 0:
                asmfile.write('\n')

            # convert parsed line to assembly
            line_translated = translate(line_parsed, filename)

            # write translated line to output file
            asmfile.write(line_translated)

            # increment line counter
            line_counter += 1

    # add END loop to asmfile
    asmfile.write('\n// end loop\n(' + filename + '.END)\n@' +
                  filename + '.END\n0;JMP')

    # close both files
    vmfile.close()
    asmfile.close()


def write_init():
    'Writes assembly code for VM initialization (bootstrap code).'
    # initialize code string to be returned
    bootstrap_code = ''

    # SP=256
    bootstrap_code += '@256\n'
    bootstrap_code += 'D=A\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=D\n'

    # call Sys.init

    return bootstrap_code


def translate(line_parsed, filename):
    """Takes list of words of VM code,
    Returns string that is the translation into Assembly Code. """
    # initialize translation string
    line_translated = ''

    # Lists of possible commands
    push_pop = ['push', 'pop']
    arithmetic = ['add', 'sub', 'neg', 'eq', 'lt', 'gt', 'and', 'or', 'not']

    # Assume line_parsed = [command, segment, index] if push/pop
    # or     line_parsed = [command] if arithmetic
    command = line_parsed[0]
    segment, index = '', ''
    if command in push_pop:
        segment = line_parsed[1]
        index = line_parsed[2]

    # if command is push/pop
    if command in push_pop:
        line_translated = translate_push_pop(command, segment, index, filename)

    # if command is arithmetic
    elif command in arithmetic:
        line_translated = translate_arithmetic(command)

    return line_translated


def translate_push_pop(command, segment, index, filename):
    'Returns the assembly code that implements the given push or pop command.'
    # initialize assembly code to be returned
    assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'

    # this is for PUSH segment index
    if command == 'push':
        if segment in ['argument', 'local', 'this', 'that']:
            if segment == 'argument':
                assembly_code += '@ARG\n'
            elif segment == 'local':
                assembly_code += '@LCL\n'
            elif segment == 'this':
                assembly_code += '@THIS\n'
            elif segment == 'that':
                assembly_code += '@THAT\n'
            # this part is common to arg, local, this, that
            assembly_code += 'D=M\n'
            assembly_code += '@' + index + '\n'
            assembly_code += 'A=D+A\n'
            assembly_code += 'D=M\n'

        elif segment == 'constant':
            assembly_code += '@' + index + '\n'
            assembly_code += 'D=A\n'

        elif segment in ['pointer', 'temp']:
            if segment == 'pointer':
                assembly_code += '@R3\n'
            elif segment == 'temp':
                assembly_code += '@R5\n'
            # this part is common to pointer, temp
            assembly_code += 'D=A\n'
            assembly_code += '@' + index + '\n'
            assembly_code += 'A=D+A\n'
            assembly_code += 'D=M\n'

        elif segment == 'static':
            assembly_code += '@' + filename + '.' + index + '\n'
            assembly_code += 'D=M\n'

        # this part is common to all segments
        assembly_code += '@SP\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=M+1'

    # this is for POP segment index
    elif command == 'pop':
        # 'pop constant index' is impossible
        if segment in ['argument', 'local', 'this', 'that']:
            if segment == 'argument':
                assembly_code += '@ARG\n'
            elif segment == 'local':
                assembly_code += '@LCL\n'
            elif segment == 'this':
                assembly_code += '@THIS\n'
            elif segment == 'that':
                assembly_code += '@THAT\n'
            # this part is common to argument, local, this, that
            assembly_code += 'D=M\n'
            assembly_code += '@' + index + '\n'
            assembly_code += 'D=D+A\n'
            assembly_code += '@R13\n'
            assembly_code += 'M=D\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'
            assembly_code += 'D=M\n'
            assembly_code += '@R13\n'
            assembly_code += 'A=M\n'
            assembly_code += 'M=D\n'

        elif segment in ['pointer', 'temp']:
            if segment == 'pointer':
                assembly_code += '@R3\n'
            elif segment == 'temp':
                assembly_code += '@R5\n'
            # this part is common to pointer, temp
            assembly_code += 'D=A\n'
            assembly_code += '@' + index + '\n'
            assembly_code += 'D=D+A\n'
            assembly_code += '@R13\n'
            assembly_code += 'M=D\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'
            assembly_code += 'D=M\n'
            assembly_code += '@R13\n'
            assembly_code += 'A=M\n'
            assembly_code += 'M=D\n'

        elif segment == 'static':
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'
            assembly_code += 'D=M\n'
            assembly_code += '@' + filename.split('.')[0] + '.' + index + '\n'
            assembly_code += 'M=D\n'

        # this part is common to all segments
        assembly_code += '@SP\n'
        assembly_code += 'M=M-1'

    return assembly_code


def translate_arithmetic(command):
    'Returns the assembly code that implements the given arithmetic command.'
    global EQ_COUNTER, LT_COUNTER, GT_COUNTER

    # initialize assembly code to be returned
    assembly_code = '// ' + command + '\n'
    assembly_code += '@SP\n'
    assembly_code += 'A=M-1\n'

    if command in ['add', 'sub', 'and', 'or']:
        if command == 'add':
            assembly_code += 'D=M\n'  # copy value at RAM[stacktop-1] to D reg
            assembly_code += 'A=A-1\n'
            assembly_code += 'D=D+M\n'
            assembly_code += 'M=D\n'  # update RAM[stacktop-2] value to the sum
        elif command == 'sub':
            assembly_code += 'A=A-1\n'
            assembly_code += 'D=M\n'  # copy value at RAM[stacktop-2] to D reg
            assembly_code += 'A=A+1\n'
            assembly_code += 'D=D-M\n'
            assembly_code += 'A=A-1\n'
            assembly_code += 'M=D\n'
        elif command == 'and':
            assembly_code += 'A=A-1\n'
            assembly_code += 'D=M\n'
            assembly_code += 'A=A+1\n'
            assembly_code += 'D=D&M\n'
            assembly_code += 'A=A-1\n'
            assembly_code += 'M=D\n'
        elif command == 'or':
            assembly_code += 'A=A-1\n'
            assembly_code += 'D=M\n'
            assembly_code += 'A=A+1\n'
            assembly_code += 'D=D|M\n'
            assembly_code += 'A=A-1\n'
            assembly_code += 'M=D\n'
        # this part is common to add, sub, and, or
        assembly_code += '@SP\n'  # back to SP
        assembly_code += 'M=M-1'  # decrement SP value

    elif command in ['eq', 'lt', 'gt']:
        # this part is common to eq, lt, gt
        assembly_code += 'A=A-1\n'
        assembly_code += 'D=M\n'
        assembly_code += 'A=A+1\n'
        assembly_code += 'D=D-M\n'

        if command == 'eq':
            assembly_code += '@EQ' + str(EQ_COUNTER) + '\n'
            assembly_code += 'D;JEQ\n'
            assembly_code += '@EQ' + str(EQ_COUNTER) + '_ELSE\n'
            assembly_code += '0;JMP\n'
            assembly_code += '(EQ' + str(EQ_COUNTER) + ')\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'  # go back to stacktop-1
            assembly_code += 'A=A-1\n'  # go back to stacktop-2
            assembly_code += 'M=-1\n'  # put 'true' in there
            assembly_code += '@SP\n'
            assembly_code += 'M=M-1\n'  # decrement SP
            assembly_code += '@EQ' + str(EQ_COUNTER) + '_END\n'
            assembly_code += '0;JMP\n'
            assembly_code += '(EQ' + str(EQ_COUNTER) + '_ELSE)\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'  # go back to stacktop-1
            assembly_code += 'A=A-1\n'  # go back to stacktop-2
            assembly_code += 'M=0\n'  # put 'false' in there
            assembly_code += '@SP\n'
            assembly_code += 'M=M-1\n'  # decrement SP
            assembly_code += '(EQ' + str(EQ_COUNTER) + '_END)'
            EQ_COUNTER += 1
        elif command == 'lt':
            assembly_code += '@LT' + str(LT_COUNTER) + '\n'
            assembly_code += 'D;JLT\n'
            assembly_code += '@LT' + str(LT_COUNTER) + '_ELSE\n'
            assembly_code += '0;JMP\n'
            assembly_code += '(LT' + str(LT_COUNTER) + ')\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'  # go back to stacktop-1
            assembly_code += 'A=A-1\n'  # go back to stacktop-2
            assembly_code += 'M=-1\n'  # put 'true' in there
            assembly_code += '@SP\n'
            assembly_code += 'M=M-1\n'  # decrement SP
            assembly_code += '@LT' + str(LT_COUNTER) + '_END\n'
            assembly_code += '0;JMP\n'
            assembly_code += '(LT' + str(LT_COUNTER) + '_ELSE)\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'  # go back to stacktop-1
            assembly_code += 'A=A-1\n'  # go back to stacktop-2
            assembly_code += 'M=0\n'  # put 'false' in there
            assembly_code += '@SP\n'
            assembly_code += 'M=M-1\n'  # decrement SP
            assembly_code += '(LT' + str(LT_COUNTER) + '_END)'
            LT_COUNTER += 1
        elif command == 'gt':
            assembly_code += '@GT' + str(GT_COUNTER) + '\n'
            assembly_code += 'D;JGT\n'
            assembly_code += '@GT' + str(GT_COUNTER) + '_ELSE\n'
            assembly_code += '0;JMP\n'
            assembly_code += '(GT' + str(GT_COUNTER) + ')\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'  # go back to stacktop-1
            assembly_code += 'A=A-1\n'  # go back to stacktop-2
            assembly_code += 'M=-1\n'  # put 'true' in there
            assembly_code += '@SP\n'
            assembly_code += 'M=M-1\n'  # decrement SP
            assembly_code += '@GT' + str(GT_COUNTER) + '_END\n'
            assembly_code += '0;JMP\n'
            assembly_code += '(GT' + str(GT_COUNTER) + '_ELSE)\n'
            assembly_code += '@SP\n'
            assembly_code += 'A=M-1\n'  # go back to stacktop-1
            assembly_code += 'A=A-1\n'  # go back to stacktop-2
            assembly_code += 'M=0\n'  # put 'false' in there
            assembly_code += '@SP\n'
            assembly_code += 'M=M-1\n'  # decrement SP
            assembly_code += '(GT' + str(GT_COUNTER) + '_END)'
            GT_COUNTER += 1
    elif command == 'neg':
        assembly_code += 'D=-M\n'
        assembly_code += 'M=D'
    elif command == 'not':
        assembly_code += 'M=!M\n'  # negate M

    return assembly_code


# run main:
main()
