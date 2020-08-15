// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed.
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// address is at RAM[16], holds values from 16384 to 24575

(KEYLOOP)
    @SCREEN
    D=A             // D = 16384
    @address
    M=D             // RAM[16] = 16384

    @KBD
    D=M             // D = keyboard input

    @WHITELOOP
    D;JEQ           // if no key is pressed (D=0), goto WHITELOOP

    @BLACKLOOP
    D;JNE           // if a key is pressed, goto BLACKLOOP

(WHITELOOP)
    @KBD
    D=A             // D = 24576
    @address
    D=D-M           // D = 24576 - 16384, etc.
    @KEYLOOP
    D;JLE           // if RAM[address] > 24575, goto KEYLOOP

    @address
    A=M             // A = 16384 etc.
    M=0             // RAM[16384] = 0 etc.

    @address
    M=M+1           // now RAM[16] = 16385 etc.

    @WHITELOOP
    0;JMP

(BLACKLOOP)
    @KBD
    D=A             // D = 24576
    @address
    D=D-M           // D = 24576 - 16384, etc.
    @KEYLOOP
    D;JLE           // if RAM[address] > 24575, goto KEYLOOP

    @address
    A=M             // A = 16384 etc.
    M=-1            // RAM[16384] = 0 etc.

    @address
    M=M+1           // now RAM[16] = 16385 etc.

    @BLACKLOOP
    0;JMP
