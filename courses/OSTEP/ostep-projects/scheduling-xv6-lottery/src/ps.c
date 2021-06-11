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
