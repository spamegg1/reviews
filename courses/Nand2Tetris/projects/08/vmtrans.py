# VM Translator Main Program
# Translates Virtual Machine code into Hack Assembly Code
# spamegg 1/3/2019
'Translates Virtual Machine code lines into Hack Assembly Code'

import sys
import os

# label counters for commands eq, lt, gt, return:
EQ_COUNTER = 0
LT_COUNTER = 0
GT_COUNTER = 0
RT_COUNTER = 0


# VERIFIED CORRECT
def main():
    """Translates Virtual Machine code lines into Hack Assembly Code.
    Accepts .vm file name (like file.vm) or DIRECTORY as command line input,
    writes the translations to an output file(like file.asm). """

    if len(sys.argv) == 1:
        infiles, outfile = get_files(os.getcwd())
    elif len(sys.argv) == 2:
        infiles, outfile = get_files(sys.argv[1])
    else:
        print("Usage: python vmtrans.py file.vm")
        print("For the entire directory, use python vmtrans.py instead")

    # open output file to write
    asmfile = open(outfile, 'w')

    # write bootstrap code to asmfile (you may have to enable/disable this!)
    # asmfile.write(write_init())

    # go through list of .vm files, translate each, write to asmfile
    number_of_files = len(infiles)
    for i in range(number_of_files):
        handle_single_file(infiles[i], asmfile)
        if i != number_of_files - 1:
            asmfile.write('\n')

    asmfile.close()


# VERIFIED CORRECT
def get_files(file_or_dir):
    """Takes string as input. String is file name or directory name.
    Returns file name or list of .vm file names in directory. """
    # if input is a file name
    if file_or_dir.endswith('.vm'):
        return [file_or_dir], file_or_dir.replace('.vm', '.asm')

    # elif input is a directory name
    infiles = []
    for filename in os.listdir(file_or_dir):
        if filename.endswith('.vm'):
            infiles.append(filename)
    return infiles, infiles[0].replace('.vm', '.asm')


# VERIFIED CORRECT
def handle_single_file(infile, asmfile):
    """Translates Virtual Machine code lines into Hack Assembly Code.
    Accepts .vm file name (like file.vm) and an open writable file as input,
    writes the translations to the open writable file (like file.asm). """

    # open input file
    vmfile = open(infile, 'r')

    # name of input file (remove .vm)
    filename = infile.split('.')[0]

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
    # asmfile.write('\n// end loop\n(' + filename + '.END)\n@' +
    #             filename + '.END\n0;JMP')

    # close infile
    vmfile.close()


# VERIFIED CORRECT
def write_init():
    'Writes assembly code for VM initialization (bootstrap code).'
    # initialize code string to be returned
    bootstrap_code = ''

    # set SP = 256
    bootstrap_code += '@256\n'
    bootstrap_code += 'D=A\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=D\n'

    # call Sys.init
    return_address = 'SYS.INIT.RETURN'
    # push return address label onto the stack
    bootstrap_code += '@' + return_address + '\n'
    bootstrap_code += 'D=A\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'A=M\n'
    bootstrap_code += 'M=D\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=M+1\n'
    # push LCL, ARG, THIS, THAT of the caller
    bootstrap_code += '@LCL\n'
    bootstrap_code += 'D=M\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'A=M\n'
    bootstrap_code += 'M=D\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=M+1\n'  # finished pushing LCL
    bootstrap_code += '@ARG\n'
    bootstrap_code += 'D=M\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'A=M\n'
    bootstrap_code += 'M=D\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=M+1\n'  # finished pushing ARG
    bootstrap_code += '@THIS\n'
    bootstrap_code += 'D=M\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'A=M\n'
    bootstrap_code += 'M=D\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=M+1\n'  # finished pushing THIS
    bootstrap_code += '@THAT\n'
    bootstrap_code += 'D=M\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'A=M\n'
    bootstrap_code += 'M=D\n'
    bootstrap_code += '@SP\n'
    bootstrap_code += 'M=M+1\n'  # finished pushing THAT
    # reposition ARG for the callee
    # new ARG = SP - 5
    bootstrap_code += '@SP\n'
    bootstrap_code += 'D=M\n'
    bootstrap_code += '@5\n'
    bootstrap_code += 'D=D-A\n'
    bootstrap_code += '@ARG\n'
    bootstrap_code += 'M=D\n'
    # reposition LCL for the callee: new LCL = SP
    bootstrap_code += '@SP\n'
    bootstrap_code += 'D=M\n'
    bootstrap_code += '@LCL\n'
    bootstrap_code += 'M=D\n'
    # goto callee
    bootstrap_code += '@Sys.init\n'
    bootstrap_code += '0;JMP\n'
    # declare label for return address
    bootstrap_code += '(' + return_address + ')\n'

    return bootstrap_code


# VERIFIED CORRECT
def translate(line_parsed, filename):
    """Takes list of words of VM code and a filename as input.
    Returns string that is the translation of list into Assembly Code. """
    # initialize translation string
    line_translated = ''

    # Lists of possible commands
    push_pop = ['push', 'pop']
    arithmetic = ['add', 'sub', 'neg', 'eq', 'lt', 'gt', 'and', 'or', 'not']
    function = ['function', 'call', 'return']
    flow = ['if-goto', 'label', 'goto']

    # Assume line_parsed = [command, segment, index] if push/pop
    # or     line_parsed = [command] if arithmetic/return
    # or     line_parsed = [command, name, args] if function/call
    # or     line_parsed = [command, label] if label/goto/if-goto
    command = line_parsed[0]
    segment, index, name, args, label = '', '', '', '', ''
    if command in push_pop:
        segment = line_parsed[1]
        index = line_parsed[2]
    elif command in ['function', 'call']:
        name = line_parsed[1]
        args = line_parsed[2]
    elif command in flow:
        label = line_parsed[1]

    # call translate function based on command type
    if command in push_pop:
        line_translated = translate_push_pop(command, segment, index, filename)
    elif command in arithmetic:
        line_translated = translate_arithmetic(command)
    elif command in function:
        line_translated = translate_function(command, name, args)
    elif command in flow:
        line_translated = translate_flow(command, label)

    return line_translated


# VERIFIED CORRECT
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
            assembly_code += '@' + filename + '.' + index + '\n'
            assembly_code += 'M=D\n'

        # this part is common to all segments
        assembly_code += '@SP\n'
        assembly_code += 'M=M-1'

    return assembly_code


# VERIFIED CORRECT
def translate_arithmetic(command):
    'Returns the assembly code that implements the given arithmetic command.'
    global EQ_COUNTER, LT_COUNTER, GT_COUNTER

    # initialize assembly code to be returned
    assembly_code = '// ' + command + '\n'

    # this part is common to all arithmetic commands
    assembly_code += '@SP\n'
    assembly_code += 'A=M-1\n'

    if command in ['add', 'sub', 'and', 'or']:
        if command == 'add':
            assembly_code += 'D=M\n'  # copy value at RAM[stacktop-1] to D reg
            assembly_code += 'A=A-1\n'
            assembly_code += 'D=D+M\n'
            assembly_code += 'M=D\n'  # update RAM[stacktop-2] value to the sum
        else:
            if command == 'sub':
                assembly_code += 'A=A-1\n'
                assembly_code += 'D=M\n'
                assembly_code += 'A=A+1\n'
                assembly_code += 'D=D-M\n'
            elif command == 'and':
                assembly_code += 'A=A-1\n'
                assembly_code += 'D=M\n'
                assembly_code += 'A=A+1\n'
                assembly_code += 'D=D&M\n'
            elif command == 'or':
                assembly_code += 'A=A-1\n'
                assembly_code += 'D=M\n'
                assembly_code += 'A=A+1\n'
                assembly_code += 'D=D|M\n'
            # this part is common to sub, and, or
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
        assembly_code += 'M=!M'  # negate M

    return assembly_code


# VERIFIED CORRECT
def translate_function(command, name, args):
    'Returns the assembly code that implements the given function command.'
    global RT_COUNTER

    # initialize assembly code to be returned
    assembly_code = '// ' + command
    if command in ['function', 'call']:
        assembly_code += ' ' + name + ' ' + args
    assembly_code += '\n'

    if command == 'call':
        # push return address label onto the stack
        return_address = name.upper() + '.RETURN.' + str(RT_COUNTER)
        RT_COUNTER += 1
        assembly_code += '@' + return_address + '\n'
        assembly_code += 'D=A\n'
        assembly_code += '@SP\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=M+1\n'
        # push LCL, ARG, THIS, THAT of the caller
        assembly_code += '@LCL\n'
        assembly_code += 'D=M\n'
        assembly_code += '@SP\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=M+1\n'  # finished pushing LCL
        assembly_code += '@ARG\n'
        assembly_code += 'D=M\n'
        assembly_code += '@SP\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=M+1\n'  # finished pushing ARG
        assembly_code += '@THIS\n'
        assembly_code += 'D=M\n'
        assembly_code += '@SP\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=M+1\n'  # finished pushing THIS
        assembly_code += '@THAT\n'
        assembly_code += 'D=M\n'
        assembly_code += '@SP\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=M+1\n'  # finished pushing THAT
        # reposition ARG for the callee: new ARG = SP - 5 - args
        args_plus_5 = str(int(args) + 5)
        assembly_code += '@SP\n'
        assembly_code += 'D=M\n'
        assembly_code += '@' + args_plus_5 + '\n'
        assembly_code += 'D=D-A\n'
        assembly_code += '@ARG\n'
        assembly_code += 'M=D\n'
        # reposition LCL for the callee: new LCL = SP
        assembly_code += '@SP\n'
        assembly_code += 'D=M\n'
        assembly_code += '@LCL\n'
        assembly_code += 'M=D\n'
        # goto callee
        assembly_code += '@' + name + '\n'
        assembly_code += '0;JMP\n'
        # declare label for return address
        assembly_code += '(' + return_address + ')'

    elif command == 'function':
        # declare label for function name
        assembly_code += '(' + name + ')'
        if int(args) != 0:
            assembly_code += '\n'
        # args many times, push 0 (set local vars to 0)
        for i in range(int(args)):
            assembly_code += '@SP\n'
            assembly_code += 'A=M\n'
            assembly_code += 'M=0\n'
            assembly_code += '@SP\n'
            assembly_code += 'M=M+1'
            if i != int(args) - 1:
                assembly_code += '\n'

    elif command == 'return':
        # create temp variable named end_frame, set it to LCL, store at R13
        assembly_code += '@LCL\n'
        assembly_code += 'D=M\n'
        assembly_code += '@R13\n'
        assembly_code += 'M=D\n'
        # return address must be the value stored at end_frame - 5
        # create another temp variable ret_addr to store this value in R14
        assembly_code += '@R5\n'
        assembly_code += 'A=D-A\n'
        assembly_code += 'D=M\n'
        assembly_code += '@R14\n'
        assembly_code += 'M=D\n'
        # reposition return value (pop the stacktop) for the caller to ARG
        assembly_code += '@SP\n'
        assembly_code += 'AM=M-1\n'
        assembly_code += 'D=M\n'
        assembly_code += '@ARG\n'
        assembly_code += 'A=M\n'
        assembly_code += 'M=D\n'
        # reposition SP for the caller to ARG + 1
        assembly_code += '@ARG\n'
        assembly_code += 'D=M+1\n'
        assembly_code += '@SP\n'
        assembly_code += 'M=D\n'
        # restore THAT of the caller, it's the value at end_frame - 1
        # remember that end_frame is stored at R13 temp location
        assembly_code += '@R13\n'
        assembly_code += 'AM=M-1\n'  # decrease value at end_frame by 1
        assembly_code += 'D=M\n'
        assembly_code += '@THAT\n'
        assembly_code += 'M=D\n'
        # similarly THIS is the value at end_frame - 2
        assembly_code += '@R13\n'
        assembly_code += 'AM=M-1\n'  # decrease value at end_frame by 1
        assembly_code += 'D=M\n'
        assembly_code += '@THIS\n'
        assembly_code += 'M=D\n'
        # similarly ARG is the value at end_frame - 3
        assembly_code += '@R13\n'
        assembly_code += 'AM=M-1\n'  # decrease value at end_frame by 1
        assembly_code += 'D=M\n'
        assembly_code += '@ARG\n'
        assembly_code += 'M=D\n'
        # similarly LCL is the value at end_frame - 4
        assembly_code += '@R13\n'
        assembly_code += 'AM=M-1\n'  # decrease value at end_frame by 1
        assembly_code += 'D=M\n'
        assembly_code += '@LCL\n'
        assembly_code += 'M=D\n'
        # finally, goto ret_addr, which was stored in R6
        assembly_code += '@R14\n'
        assembly_code += 'A=M\n'
        assembly_code += '0;JMP'

    return assembly_code


# VERIFIED CORRECT
def translate_flow(command, label):
    'Returns the assembly code that implements the given flow command.'
    # initialize assembly code to be returned
    assembly_code = '// ' + command + ' ' + label + '\n'

    if command == 'goto':
        assembly_code += '@' + label + '\n'
        assembly_code += '0;JMP'
    elif command == 'if-goto':
        assembly_code += '@SP\n'
        assembly_code += 'AM=M-1\n'
        assembly_code += 'D=M\n'
        assembly_code += '@' + label + '\n'
        assembly_code += 'D;JNE'
    elif command == 'label':
        assembly_code += '(' + label + ')'

    return assembly_code


# run main:
main()
