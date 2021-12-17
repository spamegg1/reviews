## all thanks to @palladian

### General Tips

* Read chapter 9 in the OSTEP book and watch the video for discussion 5. Lottery ticket schedulers aren't discussed in the lectures, so you really do have to read the book for this one. 

* In general, you can't use C standard library functions inside the kernel, because the kernel has to initialize before it can execute library binaries.  

* The xv6 kernel has a "kernel version" of `printf`; it takes an additional integer argument that tells it whether to print to `stdout` or `stderr`. Note that it can only handle basic format strings like `"%d"` and not more complex ones like `"%6.3g"`; you can deal with this by manually adding spaces instead. It also has another similar function, `cprintf`. 

* If you do want to use other library functions that aren't available inside the kernel (*cough* pseudo random number generators *cough*), you can see how those functions are implemented in P.J. Plauger's book, The Standard C Library, and then implement them yourself. 

  ### Implementation

  * You'll have to modify the same files you did in Project 1b in order to add the two new system calls. 
  * In order to understand how processes are created, remember that they start in the `EMBRYO` state before they become `RUNNABLE`--you'll have to find where that happens.  
  * System calls always have argument type `void`, so take a look at how system calls like `kill` and `read` manage to work around that limitation and get arguments (like integers and pointers) from user space. You might have to back a few steps in the chain that executes them. 
  * Make sure you're including `types.h` and `defs.h` wherever you need to access code from other parts of the kernel.  
  * In order to create the xv6 command `ps`, look at how `cat`, `ls`, and `ln` are implemented. Make sure to modify the Makefile to include the source code for your `ps` command.
  
  
## Spoilers below!

### Solution walk through

- Start from a fresh copy of the `xv6` source code.

- `argint` and `argptr` are important functions. So `syscall`s take no arguments, but in reality, in user code you want to pass arguments to them. 

- So the way you do that is the kernel will call the `syscall`, say, `sys_kill()` with no arguments, then `sys_kill` will use `argint()` to get the arguments from the call stack, then pass that to a function `kill(int pid)`. 

- So you can see there's a bunch of `extern int sys_whatever` function declarations below that; that means that these functions are defined in another file and should be pulled in from there as function pointers. 

- And these `sys_whatever` functions are basically just wrappers for the real `syscall`, which doesn't have the `sys_` at the beginning. So you need to add `sys_settickets` and `sys_getpinfo` to that list of function declarations. 

- Then there's an array of function pointers; it's using this old-school C way of initializing arrays where you can do `int arr[] = { [0] 5, [1] 7}`. 

- And the names inside the square brackets `SYS_fork`, etc. are defined as preprocessor macros in another header file `syscall.h`. 

- So you need to add two more entries in the array with function pointers to `sys_settickets` and `sys_getpinfo`, and then you need to define `SYS_settickets` and `SYS_getpinfo` in the relevant header file.

- So then all these `sys_` wrapper functions are defined in `sysproc.c`.

- So there, you need to create `int sys_settickets(void)` and `int sys_getpinfo(void)`.

- The real `settickets` function will need an int argument, so you need to use `argint` there to grab it from the call stack and pass it to `settickets`; similarly, `getpinfo` will need a pointer, so you'll use `argptr`.

- Also, there's an extra condition in the if statement for `sys_settickets`; that's because you're not allowed to use a number of tickets below 1.

- So then there's some assembly code that needs to run for each of the system calls; luckily, it's just a pre-written macro, so you don't have to write any assembly. that's in `usys.S`.

- So you just add two lines at the bottom to create macros for `SYSCALL(settickets)` and `SYSCALL(getpinfo)` 

- Last part for the `syscalls`: you need to declare them in a header file for user code to be able to call them. that's in `user.h`.

- So `struct pstat` will be properly defined in `pstat.h`, but you need to declare it in `user.h` as well so that user code doesn't complain when it sees it.

- Basically, any user code that uses `syscalls` or C (really, `xv6`) standard library functions will have to include `user.h`.

- So, so far, that's everything for the two system calls as far as the OS is concerned; now we just have to actually implement them with the regular functions `settickets` and `getpinfo`, then implement the scheduler and the `ps` program.

- `pstat.h` is not for the scheduler, but for the `ps` program, which will work somewhat like the Linux `ps`. `pstat.h` is just to define the `struct pstat`, but there's no `.c` file to go with it.

- So the scheduler will work by assigning 1 ticket by default to each process when it's created; then processes can set their own tickets using the `settickets` system call.

- so first we need to make sure each process tracks its own tickets, then we need to assign a default of 1 ticket when creating them, then we need to write `settickets`.

- the first part is in `proc.h`: processes are represented as a `struct proc`, so we add a new member for `int tickets`.

- the `int ticks` member is for `ps`; I'll come back to that.

- One other thing to note in `proc.h` is the `enum procstate`: you can see all the possible process states there. `EMBRYO` means it's in the process of creation; so what i did was `grep` for `EMBRYO` to find where the process was created in order to set the default tickets to 1. Turns out it's in `proc.c`.

- Inside `proc.c`, there's a function `allocproc`, which initializes a process. 

- There's a process table called `ptable`, and `allocproc` looks through it to find an unused process.

- Then when it does find it, it goes to create it; i added `p->tickets = 1;` there.

- okay so the next change is to fit one of the requirements: child processes need to inherit the number of tickets from their parent process.

- So child processes are created with `fork`, which is in the same file.

- In `fork`, `curproc` is the current process, and `np` is the new process.

- So i set `np->tickets = curproc->tickets`.

- So the scheduler needs to generate a pseudo random number, then it should iterate through the process table with a counter initialized to 0, adding the number of tickets for each process to the counter. once the counter is greater than the pseudo random number, it stops and runs that process.

- So I ended up looking in P.J. Plauger's The Standard C Library, which is just a big book of all the source code for the C library with commentary. It's pretty good; I don't know if it's still written that way though because the book is from the 80s.

- So i just implemented C's `rand` and `srand` functions. `srand` sets a random seed (not so random, as you'll see later), then `rand` turns it into a pseudo random integer.

- There's a bunch of type magic going on there between changes back and forth from integers to unsigned integers; that's to avoid signed integer overflow, which causes undefined behavior. unsigned integer overflow is okay though.

- I only made one change to make it faster, which was to write `& 32767` instead of `% 32768`.

- So you'll see the "random" seed i used: the number of `ticks`, which i think counts the number of timer interrupts so far.

- Which is totally not random at all, since the first time this program gets run, it'll be 0, then 1, then 2, etc.

- So there's some lines about counting `ticks`; that was for `ps`, not the scheduler.

- The main change to make it a lottery scheduler is the counter variable.

- And adding a for loop to count the total number of tickets that have been distributed.

- So then at the very bottom of this file is the implementation of `settickets` and `getpinfo`.

- So after initializing `counter` and `totaltickets`, there's for loop that counts the total numbers of tickets that have gone out to processes.

- Then we get the winning ticket.

- Let's discuss the original source code first. So first you acquire the lock. You'll release it at the very end. But in between, you have a for loop that iterates over all the processes in `ptable`.

- Specifically, it iterates over only the processes in `RUNNABLE` state; if a process isn't `RUNNABLE`, it just `continue`s on to the next one. (This is for the round-robin scheduling mechanism that's already in the code.)

- So now it's gonna switch to the very first `RUNNABLE` process it finds. Like, switching to executing it.

- So first, `c` represents the current CPU. so it sets the current CPU to run the process it found with `c->proc = p;`.

- Then it calls this function, `switchuvm(p)`, which sets up the virtual memory address space for `p`. Then it sets the process's state to `RUNNING`.

- And then `swtch` is where the magic happens: that one swaps out the register contents of the OS and scheduler content with the saved-in-memory register contents of the process `p`.

- So as soon as `swtch` executes, the CPU will continue executing instructions, but now they're the process's instructions. So this scheduler function just hangs there.

- Eventually, when a timer interrupt goes off, the processor will use another `swtch` call but with the arguments reversed to swap the scheduler's register contents from memory into the CPU's registers and save the process's register contents. At which point execution will continue at this exact point.

- So now `switchkvm` will set up the kernel's virtual memory address space.

- These 5 lines are the context switch:

  ```c
  c->proc = p;
  switchuvm(p);
  p->state = RUNNING;
  
  swtch(&(c->scheduler), p->context);
  switchkvm();
  ```

- So then we go on to the next iteration of the inner for loop, which finds the next `RUNNABLE` process and repeats.

- Only once we've executed all the `RUNNABLE` processes do we exit the inner for loop and release the lock.

- Original source code is structured like this (this is pseudocode):

  ```python
  while (1) {
    iterate over processes:
      if not runnable:
        continue
      run it
  ```

- New code is structured like this (this is pseudocode):

  ```python
  while (1) {
    count the total tickets allotted to all processes // one for loop here
    get the winning ticket number
    iterate over processes: // another for loop here
      if not runnable:
        continue
      add its tickets to counter
      if counter <= winning ticket number:
        continue
      run it
  ```

- We ignore the tickets of non-RUNNABLE processes.

- So the tickets aren't numbered; each process just has a set amount of tickets, and we just count up until we've passed `n` tickets, where `n` is the winner.

- For example if proc A has 5 tickets and proc B has 7, proc C has 2. if the winning number is 3, then A would run; if it's 8, then B would run; if it's 12, then C would run.

- A winner in 0-4 would be A, 5-11 would be B, and 12-13 would be C.

- So `settickets` is pretty basic: you just acquire a lock, set the tickets for the process, release the lock.

- For `getpinfo` basically it works like this: 

- `p` is a pointer a `struct pstat`, as defined in `pstat.h`. each of its members is an array, with one entry per process. 

- Check for a null pointer.

- Iterate over the process table and set `proc_i` to the i-th process.

- Set the i-th entry of each member of `p` to the value for this process.

- One last bookkeeping piece: we need to add declarations for `struct pstat` and the `settickets` and `getpinfo` system calls in `defs.h`.

- And then the last file is `ps.c`, which implements the `ps` program, similar to Linux's `ps`. it just calls `getpinfo` to fill a `struct pstat`, then prints out the info for each process in use.

- And then you just modify the Makefile to include `ps.c` in the compilation, and we're done!

- Oh and this is why we needed the ticks in the scheduler: `ps` will print out how long each process has run.

- So it needs to time the number of ticks that it actually executed.

- FINALLY run `make qemu` in the `/src` directory to make sure it's all working.

### actual code

- in `defs.h` line 12

  ```c
  struct superblock;
  struct pstat;		// ADD THIS LINE
  ```

- in `defs.h` line 124

  ```c
  void            yield(void);
  int				settickets(int number);		// ADD THIS LINE
  int				getpinfo(struct pstat *p);	// ADD THIS LINE
  ```

  

- in `syscall.c`: Line 106

  ```c
  extern int sys_write(void);
  extern int sys_uptime(void);
  extern int sys_settickets(void);  // ADD THIS LINE
  extern int sys_getpinfo(void);    // ADD THIS LINE
  ```

- in `syscall.h` at the bottom:

  ```c
  #define SYS_close  21
  #define SYS_settickets 22  // ADD THIS LINE
  #define SYS_getpinfo 23    // ADD THIS LINE
  ```
  
- in `sysproc.c` Line 9:

  ```c
  #include "proc.h"
  #include "pstat.h" // ADD THIS LINE
  ```

- in `sysproc.c`, at the bottom, two new functions:

  ```c
  // ADD THIS FUNCTION
  int
  sys_settickets(void)
  {
  	int n;
  
  	if ((argint(0, &n) < 0) || n < 1)
  		return -1;
  
  	return settickets(n);
  }
  
  // ADD THIS FUNCTION
  int
  sys_getpinfo(void)
  {
  	struct pstat *p;
  
  	if (argptr(0, (void *)&p, sizeof(*p)) < 0)
  		return -1;
  
  	return getpinfo(p);
  }
  ```

- in `user.h` at the top:

  ```c
  struct pstat;		// ADD THIS LINE
  struct stat;
  struct rtcdate;
  ```

- in `user.h` line 27:

  ```c
  int sleep(int);
  int uptime(void);
  int settickets(int);			// ADD THIS LINE
  int getpinfo(struct pstat*);	// ADD THIS LINE
  ```

- in `usys.S` at the bottom

  ```assembly
  SYSCALL(sleep)
  SYSCALL(uptime)
  SYSCALL(settickets)  // ADD THIS LINE
  SYSCALL(getpinfo)	 // ADD THIS LINE
  ```

- copy/paste code of `pstat.h` from the Project description, save it in a new file `pstat.h`:

  ```c
  #ifndef _PSTAT_H_
  #define _PSTAT_H_
  
  #include "param.h"
  
  struct pstat {
    int inuse[NPROC];   // whether this slot of the process table is in use (1 or 0)
    int tickets[NPROC]; // the number of tickets this process has
    int pid[NPROC];     // the PID of each process 
    int ticks[NPROC];   // the number of ticks each process has accumulated 
  };
  
  #endif // _PSTAT_H_
  ```

- in `proc.h` line 52:

  ```c
  char name[16];               // Process name (debugging)
  int tickets;				 // ADD THIS LINE
  int ticks;				     // ADD THIS LINE
  ```

- in `proc.c` line 9

  ```c
  #include "pstat.h"		// ADD THIS LINE
  ```

- in `proc.c` line 92

  ```c
  found:
    p->state = EMBRYO;
    p->pid = nextpid++;
    p->tickets = 1;		// ADD THIS LINE
  ```

- in `proc.c` line 204

  ```C
  np->sz = curproc->sz;
  np->parent = curproc;
  *np->tf = *curproc->tf;
  np->tickets = curproc->tickets;		// ADD THIS LINE
  ```

- in `proc.c` line 317 right after `// PAGEBREAK 42`

  ```c
  // ADD THIS: random seed for generator below
  unsigned long int seed = 1;
  
  // ADD THIS: update random seed value
  void
  srand(uint n)
  {
    seed = n;
  }
  
  // ADD THIS: generate a pseudorandom number from a random seed
  int
  rand(void)
  {
    // algorithm by Kernighan and Ritchie:
    seed = seed * 1103515243U + 12345U;
    return (unsigned int) (seed / 65536) & 32767;  // & 32767 same as % 32768
  }
  
  // ADD THIS: return a pseudorandom number between 0 and n
  int
  getwinner(int n)
  {
  	return (rand() % (n+1));
  }
  ```

- in `proc.c`, the function `void scheduler(void)` should now look like this:

  ```c
  void
  scheduler(void)
  {
    struct proc *p;
    struct cpu *c = mycpu();
    c->proc = 0;
  
    for(;;){  // this is while (1) {}
      // Enable interrupts on this processor.
      sti();
  
      // Loop over process table looking for process to run.
      acquire(&ptable.lock);
  
      // OSTEP project: to track if we've found the winner
      int counter = 0;
      int totaltickets = 0;
  
      // OSTEP project: count the total tickets alloted to runnable processes
      for (p = ptable.proc; p < &ptable.proc[NPROC]; p++) {
  		  if (p->state == RUNNABLE) {
  		      totaltickets += p->tickets;
  		  }
  	  }
  
      // OSTEP project: get a random value between 0 and totaltickets
  	  srand(ticks);
  	  int winner = getwinner(totaltickets);
  
  
      for(p = ptable.proc; p < &ptable.proc[NPROC]; p++){
        if(p->state != RUNNABLE)
          continue;
  
        // OSTEP project: increment counter by the process's tickets
  	    counter += p->tickets;
  
  	    // OSTEP project: continue until we find the winner
  	    if (counter <= winner) {
  		    continue;
  	    }
  
  	    // OSTEP project: start timer for process
  	    uint ticks0 = ticks;
  
        // Switch to chosen process.  It is the process's job
        // to release ptable.lock and then reacquire it
        // before jumping back to us.
        c->proc = p;
        switchuvm(p);
        p->state = RUNNING;
  
        swtch(&(c->scheduler), p->context);
        switchkvm();
  
        // Process is done running for now.
        // It should have changed its p->state before coming back.
        c->proc = 0;
  
        // OSTEP project: set process's ticks to timer result
  	    p->ticks += ticks - ticks0;  // for the ps program
      }
      release(&ptable.lock);
  
    }
  }
  ```

- in `proc.c` at the bottom, add these functions:

  ```c
  // OSTEP project: set the number of tickets for the calling process, to be
  // used by the lottery scheduler
  int
  settickets(int number)
  {
    struct proc *curproc = myproc();
    acquire(&ptable.lock);
    curproc->tickets = number;
    release(&ptable.lock);
    return 0;
  }
  
  // OSTEP project: population information about all running processes in p
  int
  getpinfo(struct pstat *p)
  {
    if (p == 0) {
      return -1;
    }
  
    struct proc proc_i;
  
    acquire(&ptable.lock);
    for (int i = 0; i < NPROC; i++) {
      proc_i = ptable.proc[i];
  
      p->inuse[i] = proc_i.state == UNUSED ? 0 : 1;
      p->tickets[i] = proc_i.tickets;
      p->pid[i] = proc_i.pid;
      p->ticks[i] = proc_i.ticks;
    }
    release(&ptable.lock);
    return 0;
  }
  ```

- create a new file `ps.c`

  ```c
  // OSTEP project: this whole file
  #include "types.h"
  #include "user.h"
  #include "pstat.h"
  
  
  int
  main(void)
  {
  	struct pstat p;
  
  	// System call to populate process info into p
  	if (getpinfo(&p) < 0) {
  		printf(2, "getpinfo: error\n");
  		exit();
  	}
  
  	// Print out info for all processes in use
  	for (int i = 0; i < NPROC; i++) {
  		if (p.inuse[i]) {
  			printf(1, "PID: %d\tTickets: %d\tTicks: %d\n", p.pid[i], p.tickets[i], p.ticks[i]);
  		}
  	}
  	exit();
  }
  
  ```

- edit `Makefile` line 168

  ```makefile
  UPROGS=\
  	_cat\
  	_echo\
  	_forktest\
  	_grep\
  	_init\
  	_kill\
  	_ln\
  	_ls\
  	_mkdir\
  	_ps\  # OSTEP project: add ps
  	_rm\
  	_sh\
  	_stressfs\
  	_usertests\
  	_wc\
  	_zombie\
  ```

  
