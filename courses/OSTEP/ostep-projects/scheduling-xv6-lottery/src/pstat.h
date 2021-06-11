#ifndef _PSTAT_H_		// OSTEP project: this entire file is new
#define _PSTAT_H_

#include "param.h"

struct pstat {
	int inuse[NPROC];	// whether this slot of the process table is in use (1 or 0)
	int tickets[NPROC];	// number of tickets this process has
	int pid[NPROC];		// PID of each process
	int ticks[NPROC];	// number of ticks each process has accumulated
};

#endif
