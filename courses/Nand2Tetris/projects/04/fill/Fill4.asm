//intitialise A with no of registers to blacken/whiten
@10
D=A

@n
M=D

//store keyboard address in kb

@keyboard
D=A
@kb
M=D

//Infinite LOOP

(LOOP)

//Get keyboard inoput
@kb
A = M

@A
D=M

//check if pressed
@PRESSED
D;JGT

//if not pressed set color to 0 and call fillcolor
@0
D=A
@color
M=D
@FILLCOLOR
0;JMP

//if pressed set color to -1 and call fillcolor
(PRESSED)
@0
D=A
@color
M=D-1

@FILLCOLOR
0;JMP

//sets n registers on screen to color
(FILLCOLOR)
//get screen coordinates
@screen
D=M
@i
M=D

//fill the registers
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
