## Project 1b: `initial-xv6` implementing `getreadcount` system call

### all thanks to @palladian on Discord

in `proc.h` line 52, add one line:

```c
// Per-process state
struct proc {
  uint sz;                     // Size of process memory (bytes)
  pde_t* pgdir;                // Page table
  char *kstack;                // Bottom of kernel stack for this process
  enum procstate state;        // Process state
  int pid;                     // Process ID
  struct proc *parent;         // Parent process
  struct trapframe *tf;        // Trap frame for current syscall
  struct context *context;     // swtch() here to run process
  void *chan;                  // If non-zero, sleeping on chan
  int killed;                  // If non-zero, have been killed
  struct file *ofile[NOFILE];  // Open files
  struct inode *cwd;           // Current directory
  char name[16];               // Process name (debugging)
  int readcount;               // <<-- ADD THIS LINE
};
```

in `user.h` line 26:

```c
int sleep(int);
int uptime(void);
int getreadcount(void);  // ADD THIS LINE
```

in `usys.S` line 26 (bottom of file):

```assembly
SYSCALL(sleep)
SYSCALL(uptime)
SYSCALL(getreadcount)  // ADD THIS LINE
```

in `syscall.h` line 20 (bottom of file):

```c
#define SYS_mkdir  20
#define SYS_close  21
#define SYS_getreadcount 22 // ADD THIS LINE
```

in `proc.c`, line 289, add one line:

```c
if(p->state == ZOMBIE){
        // Found one.
        curproc->readcount += p->readcount;  // ADD THIS LINE (this adds up readcounts of children)
        pid = p->pid;
        kfree(p->kstack);
        p->kstack = 0;
        freevm(p->pgdir);
        p->pid = 0;
        p->parent = 0;
        p->name[0] = 0;
        p->killed = 0;
        p->state = UNUSED;
        release(&ptable.lock);
        return pid;
      }
```

in `syscall.c` line 106

```c
extern int sys_write(void);
extern int sys_uptime(void);
extern int sys_getreadcount(void);  // ADD THIS LINE
```

Line 130, inside `static int (*syscalls[])(void) = {`

```c
[SYS_mkdir]   sys_mkdir,
[SYS_close]   sys_close,
[SYS_getreadcount] sys_getreadcount,  // ADD THIS LINE
};
```

Line 141, inside `if(num > 0 && num < NELEM(syscalls) && syscalls[num]) {`

```c
if (num == SYS_read) {     // ADD THIS BLOCK
    myproc()->readcount++; // ADD THIS BLOCK
}                          // ADD THIS BLOCK
```

in `sysproc.c`, line 94 (bottom of file)

```c
// ADD THIS
// return the number of read() syscalls that have occurred
int
sys_getreadcount(void)
{
  return myproc()->readcount;
}
```

