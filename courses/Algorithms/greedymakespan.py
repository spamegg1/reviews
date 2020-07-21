# -*- coding: utf-8 -*-
"""
Created on Tue Nov 26 23:07:03 2019.

@author: spamegg1
"""
from random import randint as r
import heapdict as h


def greedymakespan1(joblist, machinecount):
    """Take list of positive integers and a positive integer as input.

    :param joblist: (unsorted) list of job sizes: [7, 3, 5, 8] etc.
    :param machinecount: number of available machines (<= len(joblist)).
    :return: maximum load of a machine (aka "makespan").
    """
    machines = h.HeapDict()
    for i in range(machinecount):
        machines[str(i)] = 0

    for job in joblist:
        machine, load = machines.popitem()
        machines[machine] = load + job

    return max(machines.values())


def greedymakespan2(joblist, machinecount):
    """Take list of positive integers and a positive integer as input.

    :param joblist: (unsorted) list of job sizes: [7, 3, 5, 8] etc.
    :param machinecount: number of available machines (<= len(joblist)).
    :return: maximum load of a machine (aka "makespan").
    """
    sortedjoblist = sorted(joblist, reverse=True)
    return greedymakespan1(sortedjoblist, machinecount)


# TESTING
if __name__ == '__main__':
    JOBSIZERANGE = 20
    JOBCOUNT = 6
    MACHINES = 3

    JOBLIST = [r(1, JOBSIZERANGE) for _ in range(JOBCOUNT)]
    print(JOBLIST)
    RESULT1 = greedymakespan1(JOBLIST, MACHINES)
    RESULT2 = greedymakespan2(JOBLIST, MACHINES)
    print(RESULT1)
    print(RESULT2)
    print(RESULT1 / RESULT2)
    print("Tests pass.")
