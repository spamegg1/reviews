// Program: Signum.asm
// Computes: if R0>0
//              R1=1
//           else
//              R1=0
    @R0
    D=M    // D = RAM[0]
    @POSITIVE
    D;JGT  // if RAM[0]>0, goto POSITIVE
    @ELSE
    0;JMP  // otherwise goto ELSE
(POSITIVE)
    @R1
    M=1
    @END
    0;JMP
(ELSE)
    @R1
    M=0
    @END
    0;JMP
(END)
    @END
    0;JMP
