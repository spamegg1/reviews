@4096
D=A

@n
M=D

(LOOP)
@R0
D = M

@PRESSED
D;JGT

@color
M=0
@FILLCOLOR
0;JMP

(PRESSED)
@color
M=-1

@FILLCOLOR
0;JMP

(FILLCOLOR)
@screen
D=M
@i
M=D

(FILL)
@i
D=M

@n
D=D-M

@LOOP
D;JEQ

@color
D=M
@i
A=M
@A
M=D
@i
M=M+1
@FILL
0;JMP

@LOOP
0;JMP
