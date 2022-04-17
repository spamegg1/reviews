## Getting Started with the VM

If you wish to write a compiler for the course, you will need an 
environment in which to do so. Due to the complexity of getting all the 
needed tools installed correctly, we provide a pre-configured Linux 
system via VirtualBox VM.

VirtualBox allows you to install the VM on your own computer. Instructions on setting up VirtualBox are available [here](https://courses.edx.org/courses/course-v1:StanfordOnline+SOE.YCSCS1+1T2020/7b74698308574f3c89d2ed498f26a019/).

Once the VM is set up, you can compile the example programs. Once the assignments become available, you will be able to work on these inside  the VM as well.

To play around with the example cool programs, make a directory and  copy over one or more examples from the /usr/class/cs143/examples  directory. These examples are the same as the ones posted on the  website. The coolc command will run the reference compiler to generate  the assembly output (.s) file, which you can run using the spim  simulator. For example, to compile and run "hello_world.cl", run in a  terminal (where $ represents the prompt):

```bash
$ mkdir examples
$ cd examples
$ cp /usr/class/cs143/examples/hello_world.cl .
$ coolc hello_world.cl
$ spim hello_world.s
SPIM Version 6.5 of January 4, 2003
Copyright 1990-2003 by James R. Larus (larus@cs.wisc.edu).
All Rights Reserved.
See the file README for a full copyright notice.
Loaded: /usr/class/cs143/cool/lib/trap.handler
Hello, World.
COOL program successfully executed
Stats -- #instructions : 152
         #reads : 27  #writes 22  #branches 28  #other 75
```