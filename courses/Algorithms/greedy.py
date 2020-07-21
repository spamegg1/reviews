# -*- coding: utf-8 -*-
"""
Created on Wed Oct  2 21:29:08 2019.

@author: spamegg1
"""
from itertools import permutations as p


def greedy(score, jobs):
    """Take a 2-ary function and a list of pairs of integers as input.

    The list is a list of jobs. Each job is a (weight, length) pair.
    The function determines the greedy score (priority) of each job.
    The greedy schedule is jobs sorted decreasing by score.
    Return the greedy schedule.
    """
    result = sorted(jobs, key=score, reverse=True)
    # do a second pass through to break ties in favor of heavier weights
    for _ in range(200):
        for k in range(len(result) - 1):
            if score(result[k]) == score(result[k + 1]):
                if result[k + 1][0] > result[k][0]:
                    result[k], result[k + 1] = result[k + 1], result[k]
    return result


def text_to_jobs(filename):
    """Take textfile name as input.

    File has 2 integers x, y on each line.
    Return a list of pairs (x, y) from the lines of textfile.
    """
    result = []
    with open(filename, 'r') as file:
        for line in file:
            weight, job = line.split()
            weight, job = int(weight), int(job)
            result.append((weight, job))
    return result


def weightedsum(schedule):
    """Take a list of pairs of integers as input.

    Each pair is a job. Each job is a (weight, length) pair.
    Completion time of a job is the sum of the lengths of all jobs before it,
    plus its own length.
    Return the weighted sum of completion times of jobs in list.
    """
    completion = 0
    result = 0
    for weight, length in schedule:
        completion += length
        result += weight * completion
    return result


# Now for a different greedy scheduling problem.
# Jobs have length and a deadline. Each job is a (length, deadline) pair.
# A schedule is a list of (length, deadline) pairs.
def lateness(schedule):
    """Take a list of pairs of integers as input.

    Each pair is a job. Each job is a (length, deadline) pair.
    Completion time of a job is the sum of the lengths of all jobs before it,
    plus its own length.
    Lateness of a job is its completion time minus its deadline (if this is
    positive) or 0.
    Return the total lateness of all jobs in list.
    """
    completion = 0
    result = 0
    for length, deadline in schedule:
        completion += length
        late = completion - deadline
        result += 0 if late < 0 else late
    return result


def maxlate(schedule):
    """Take a list of pairs of integers as input.

    Each pair is a job. Each job is a (length, deadline) pair.
    Completion time of a job is the sum of the lengths of all jobs before it,
    plus its own length.
    Lateness of a job is its completion time minus its deadline (if this is
    positive) or 0.
    Return the maximum lateness of jobs in list.
    """
    completion = 0
    results = []
    for length, deadline in schedule:
        completion += length
        late = completion - deadline
        result = 0 if late < 0 else late
        results.append(result)
    return max(results)


def bruteforce(schedule, prob):
    """Take list of (length, deadline) integer pairs and a function as input.

    :param schedule:
    :param prob: name of a function (either lateness or maxlate).
    :return: Minimum total lateness of all possible schedules of job list.
    """
    results = []
    perms = (perm for perm in p(schedule))
    for perm in perms:
        results.append(prob(perm))
    return min(results)


# TESTING
if __name__ == "__main__":
    def diff(job):
        """."""
        return job[0] - job[1]

    def ratio(job):
        """."""
        return job[0] / job[1]

    # FILES = ['problem13.4test.txt', 'problem13.4.txt']
    # SCORES = [diff, ratio]
    # ANSWERS = [[68615, 69119377652], [67247, 67311454237]]
    # for i in range(len(SCORES)):
    #     for j in range(len(FILES)):
    #         assert weightedsum(greedy(SCORES[i], text_to_jobs(FILES[j])))\
    #             == ANSWERS[i][j]

    # testing lateness
    def prod(job):
        """."""
        return job[0] * job[1]

    def deadl(job):
        """."""
        return job[1]

    def leng(job):
        """."""
        return job[0]

    # assume all lengths and deadlines are distinct.
    JOBLISTS = [
        [(1, 3), (2, 2), (3, 1)],
        [(1, 6), (2, 5), (3, 3)]
    ]
    SCORES = [(prod, 'product'), (deadl, 'deadline'), (leng, 'length')]
    PROBLEMS = [(lateness, 'Total lateness'), (maxlate, 'Maximum lateness')]
    for pair in SCORES:
        score, name = pair
        for joblist in JOBLISTS:
            SORTEDJOBLIST = sorted(joblist, key=score)
            for problem in PROBLEMS:
                function, probname = problem
                print('When jobs are sorted in increasing order of: ' + name)
                print("Schedule is: ", SORTEDJOBLIST)
                print(probname + ' is: ', function(SORTEDJOBLIST))
                print("Minimum " + probname + " is: ",
                      bruteforce(SORTEDJOBLIST, function))
    print("Tests pass.")
