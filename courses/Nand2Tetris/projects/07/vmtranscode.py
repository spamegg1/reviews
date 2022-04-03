# Stack pointer SP is stored at RAM[0]
# Stack starts at RAM[256]
# ARGUMENT LOCAL STATIC CONSTANT THIS THAT POINTER TEMP
command, segment, index = '', '', ''
# push argument i (same for local, this, that)
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@ARG\n'  # points at ARG = RAM[2]
assembly_code += 'D=M\n'  # copy base address of ARG segment to D register
assembly_code += '@' + index + '\n'  # now A = index
assembly_code += 'A=D+A\n'  # now A = index + base of ARG, points to ARG[index]
assembly_code += 'D=M\n'  # copy contents of ARG[index] to D register
assembly_code += '@SP\n'  # now points at SP = RAM[0]
assembly_code += 'A=M\n'  # now A = position of stacktop, points to stacktop
assembly_code += 'M=D\n'  # copy D register (ARG[index]) to RAM[stacktop]
assembly_code += '@SP\n'  # point to SP = RAM[0]
assembly_code += 'M=M+1'  # increment stacktop position by 1
# push constant i
assembly_code += '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@' + index + '\n'  # now A = index
assembly_code += 'D=A\n'  # copy index to D register
assembly_code += '@SP\n'  # select SP = RAM[0], points at 0
assembly_code += 'A=M\n'  # now A = stacktop address, points at stacktop
assembly_code += 'M=D\n'  # copy index from D register to RAM[stacktop]
assembly_code += '@SP\n'  # now increment stacktop address
assembly_code += 'M=M+1'
# push pointer i
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@R3\n'
assembly_code += 'D=A\n'
assembly_code += '@' + index + '\n'  # now A = index
assembly_code += 'A=D+A\n'
assembly_code += 'D=M\n'
assembly_code += '@SP\n'  # now points at SP = RAM[0]
assembly_code += 'A=M\n'
assembly_code += 'M=D\n'
assembly_code += '@SP\n'  # point to SP = RAM[0]
assembly_code += 'M=M+1'  # increment stacktop position by 1
# push temp i
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@R5\n'  # points at TEMP = RAM[5]
assembly_code += 'D=A\n'  # copy base address of TEMP segment to D register
assembly_code += '@' + index + '\n'  # now A = index
assembly_code += 'A=D+A\n'  # now A = index + base of POINTER
assembly_code += 'D=M\n'  # copy contents of POINTER[index] to D register
assembly_code += '@SP\n'  # now points at SP = RAM[0]
assembly_code += 'A=M\n'  # now A = position of stacktop, points to stacktop
assembly_code += 'M=D\n'  # copy D register (POINTER[index]) to RAM[stacktop]
assembly_code += '@SP\n'  # point to SP = RAM[0]
assembly_code += 'M=M+1'  # increment stacktop position by 1
# push static i
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@' + infile.split('.')[0] + '.' + index + '\n'
assembly_code += 'D=M\n'  # copy value of Xxx.index to D register
assembly_code += '@SP\n'  # now points at SP = RAM[0]
assembly_code += 'A=M\n'  # now A = position of stacktop, points to stacktop
assembly_code += 'M=D\n'  # copy D register (Xxx.index) to RAM[stacktop]
assembly_code += '@SP\n'  # point to SP = RAM[0]
assembly_code += 'M=M+1'  # increment stacktop position by 1


# pop constant i NOT POSSIBLE
# pop argument i (same for local, this, that)
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@ARG\n'  # select ARG = 2, points at 2
assembly_code += 'D=M\n'  # copy base address of ARG segment to D register
assembly_code += '@' + index + '\n'  # select index, now A = index
assembly_code += 'D=D+A\n'  # now D = base address of ARG + index
assembly_code += '@R13\n'  # select R13, points at 13
assembly_code += 'M=D\n'  # now RAM[13] = index + base address of ARG segment
assembly_code += '@SP\n'  # select SP = 0, points at 0
assembly_code += 'A=M-1\n'  # now A = stacktop-1, points to stacktop-1
assembly_code += 'D=M\n'  # copy value at stacktop-1 to D register
assembly_code += '@R13\n'  # go back to R13
assembly_code += 'A=M\n'  # now A = index + base of ARG, points at ARG[index]
assembly_code += 'M=D\n'  # finally, ARG[index] = (stacktop-1) value
assembly_code += '@SP\n'  # go back to SP = 0
assembly_code += 'M=M-1'  # decrement stacktop address
# pop pointer i
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@R3\n'
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
assembly_code += '@SP\n'
assembly_code += 'M=M-1'
# pop temp i
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@R5\n'
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
assembly_code += '@SP\n'
assembly_code += 'M=M-1'
# pop static i
assembly_code = '// ' + command + ' ' + segment + ' ' + index + '\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'
assembly_code += 'D=M\n'
assembly_code += '@' + infile.split('.')[0] + '.' + index + '\n'
assembly_code += 'M=D\n'
assembly_code += '@SP\n'
assembly_code += 'M=M-1'

# add
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'  # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0] - 1, points at stacktop-1
assembly_code += 'D=M\n'  # copy value at RAM[stacktop-1] to D register
assembly_code += 'A=A-1\n'  # go 1 below to RAM[stacktop-2]
assembly_code += 'D=D+M\n'  # add RAM[stacktop-2] to RAM[stacktop-1]
assembly_code += 'M=D\n'  # update RAM[stacktop-2] value to the sum
assembly_code += '@SP\n'  # back to SP
assembly_code += 'M=M-1'  # decrement SP value
# sub
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'  # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'A=A-1\n'  # now A = RAM[0]-2, points at stacktop-2
assembly_code += 'D=M\n'  # copy value at RAM[stacktop-2] to D register
assembly_code += 'A=A+1\n'  # go to stacktop-1
assembly_code += 'D=D-M\n'  # subtract RAM[stacktop-2]-RAM[stacktop-1]
assembly_code += 'A=A+1\n'  # back to stacktop-2
assembly_code += 'M=D\n'  # update RAM[stacktop-2] value to the difference
assembly_code += '@SP\n'  # back to SP
assembly_code += 'M=M-1'  # decrement SP value
# neg
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # now A = RAM[0] - 1, points to stacktop-1
assembly_code += 'D=-M\n'  # negate value at RAM[stacktop-1]
assembly_code += 'M=D'  # update value at RAM[stacktop-1] to its negative
# eq
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'  # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'A=A-1\n'  # now A = RAM[0]-2, points at stacktop-2
assembly_code += 'D=M\n'  # copy value at RAM[stacktop-2] to D register
assembly_code += 'A=A+1\n'  # go to stacktop-1
assembly_code += 'D=D-M\n'  # subtract RAM[stacktop-2]-RAM[stacktop-1]
assembly_code += '@EQ\n'
assembly_code += 'D;JEQ\n'
assembly_code += '@EQ_ELSE\n'
assembly_code += '0;JMP\n'
assembly_code += '(EQ)\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # go back to stacktop-1
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=-1\n'  # put 'true' in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
assembly_code += '(EQ_ELSE)\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # go back to stacktop-1
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=0\n'  # put 'false' in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
# lt
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'  # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'A=A-1\n'  # now A = RAM[0]-2, points at stacktop-2
assembly_code += 'D=M\n'  # copy value at RAM[stacktop-2] to D register
assembly_code += 'A=A+1\n'  # go to stacktop-1
assembly_code += 'D=D-M\n'  # subtract RAM[stacktop-2]-RAM[stacktop-1]
assembly_code += '@LT\n'
assembly_code += 'D;JLT\n'
assembly_code += '@LT_ELSE\n'
assembly_code += '0;JMP\n'
assembly_code += '(LT)\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # go back to stacktop-1
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=-1\n'  # put 'true' in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
assembly_code += '(LT_ELSE)\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # go back to stacktop-1
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=0\n'  # put 'false' in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
# gt
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'    # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'A=A-1\n'  # now A = RAM[0]-2, points at stacktop-2
assembly_code += 'D=M\n'    # copy value at RAM[stacktop-2] to D register
assembly_code += 'A=A+1\n'  # go to stacktop-1
assembly_code += 'D=D-M\n'  # subtract RAM[stacktop-2]-RAM[stacktop-1]
assembly_code += '@GT\n'
assembly_code += 'D;JGT\n'
assembly_code += '@GT_ELSE\n'
assembly_code += '0;JMP\n'
assembly_code += '(GT)\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # go back to stacktop-1
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=-1\n'   # put 'true' in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
assembly_code += '(GT_ELSE)\n'
assembly_code += '@SP\n'
assembly_code += 'A=M-1\n'  # go back to stacktop-1
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=0\n'    # put 'false' in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
# and
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'    # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'A=A-1\n'  # now A = RAM[0]-2, points at stacktop-2
assembly_code += 'D=M\n'    # copy value at RAM[stacktop-2] to D register
assembly_code += 'A=A+1\n'  # go to stacktop-1
assembly_code += 'D=D&M\n'  # calculate RAM[stacktop-2] AND RAM[stacktop-1]
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=D\n'    # put RAM[stacktop-2] AND RAM[stacktop-1] in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
# and version 2
# x           stacktop-2
# y           stacktop-1
# (nothing)   stacktop
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'    # point at SP = 0
assembly_code += 'A=M-1\n'  # now A points at stacktop-1
assembly_code += 'D=M\n'    # copy value y at stacktop-1 to D register
assembly_code += 'A=A-1\n'  # now A points at stacktop-2
assembly_code += 'D=D&M\n'  # calculate y&x
assembly_code += 'M=D\n'    # put x&y at position stacktop-2
assembly_code += '@SP\n'    # point at SP
assembly_code += 'M=M-1\n'  # decrement SP so now it's stacktop-1 (where y was)
# or
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'    # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'A=A-1\n'  # now A = RAM[0]-2, points at stacktop-2
assembly_code += 'D=M\n'    # copy value at RAM[stacktop-2] to D register
assembly_code += 'A=A+1\n'  # go to stacktop-1
assembly_code += 'D=D|M\n'  # calculate RAM[stacktop-2] OR RAM[stacktop-1]
assembly_code += 'A=A-1\n'  # go back to stacktop-2
assembly_code += 'M=D\n'    # put RAM[stacktop-2] OR RAM[stacktop-1] in there
assembly_code += '@SP\n'
assembly_code += 'M=M-1\n'  # decrement SP
# not
assembly_code = '// ' + command + '\n'
assembly_code += '@SP\n'  # point at SP = 0
assembly_code += 'A=M-1\n'  # now A = RAM[0]-1, points at stacktop-1
assembly_code += 'M=!M\n'  # negate M
