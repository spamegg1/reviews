## Start doing PA1 this week!
It’s in the `dist/assignments/PA1` directory.

### Compiling and running COOL programs

Extract the `dist.tar.gz` file, then add the `bin` directory to your `PATH`. For example, if you extracted it to `~/dist` then add the following to either your `~/.bashrc` or your `~/.profile` file:

```bash
export PATH=/home/$USER/dist/bin:$PATH
```

Now the `coolc` binary will be available on your Terminal (after a restart of your Terminal if you used `.bashrc` or after a logout/login if you used `.profile`).

If you have a COOL program, like: 

```java
class Main {
    main(): Int { 1 };
}
```

in a file, like `1.cl` and you compile it with 

```bash
coolc 1.cl
```

it will produce a MIPS assembly file `1.s`. How to run this file?

You need to install `spim` which is a MIPS emulator, it can run these `.s` (MIPS assembly) files. On Debian/Ubuntu you can use `apt install spim`. So then you run:

```bash
spim 1.s
```

which shows you:

```bash
 ➜ spim 1.s
SPIM Version 6.5 of January 4, 2003
Copyright 1990-2003 by James R. Larus (larus@cs.wisc.edu).
All Rights Reserved.
See the file README for a full copyright notice.
Loaded: ../lib/trap.handler
COOL program successfully executed
```

Your COOL program successfully executed! Cool! 
If this does not work, use the older version of `spim` provided in the `dist` folder.

You can also look into the `1.s` MIPS assembly file, it’s not a binary file, so it can be opened and viewed in a text editor, somewhat human readable:

```assembly
	.data
	.align	2
	.globl	class_nameTab
	.globl	Main_protObj
	.globl	Int_protObj
	.globl	String_protObj
	.globl	bool_const0
	.globl	bool_const1
	.globl	_int_tag
	.globl	_bool_tag
	.globl	_string_tag
_int_tag:
	.word	2
_bool_tag:
	.word	3
_string_tag:
	.word	4
	.globl	_MemMgr_INITIALIZER
_MemMgr_INITIALIZER:
	.word	_NoGC_Init
	.globl	_MemMgr_COLLECTOR
_MemMgr_COLLECTOR:
# and it goes on...
```

### Using Libraries in compilation

Take a look at this COOL program:

```java
class Main inherits A2I {
    main(): Object {
        (new IO).out_string(
            i2a(
                a2i((new IO).in_string()) + 1
            ).concat("\n")
        )
    };
};
```

If we try to compile it, we get an error:

```bash
 ➜ coolc 02-02-fact.cl 
02-02-fact.cl:1: Class Main inherits from an undefined class A2I.
Compilation halted due to static semantic errors.
```

The `A2I` library (ASCII to Integer) is in a file called `atoi.cl`. We need to copy it into the same directory as our file `02-02-fact.cl` and provide it as an argument to the compiler:

```bash
coolc 02-02-fact.cl atoi.cl
```

Now it compiles correctly! It adds 1 to our provided integer input correctly:

```bash
 ➜ spim 02-02-fact.s
SPIM Version 6.5 of January 4, 2003
Copyright 1990-2003 by James R. Larus (larus@cs.wisc.edu).
All Rights Reserved.
See the file README for a full copyright notice.
Loaded: ../lib/trap.handler
3
4
COOL program successfully executed
```

