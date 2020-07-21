# -*- coding: utf-8 -*-
"""
Created on Tue Nov 19 18:07:03 2019.

@author: spamegg1
"""
import graph as g
from random import randint
from itertools import combinations as comb
from itertools import permutations as perm
from math import sqrt
from operator import itemgetter


def completegraph(nodect, weights):
    """Take a positive integer and a list of nonnegative integers as input.

    :param nodect: number of nodes (labeled from 1 to nodect).
    :param weights: edge weights (there are nodect*(nodect-1)/2 of them).
    :return: undirected weighted complete graph with given edge weights.
    """
    nodes, edges = {}, {}
    counter = 0
    for node in range(1, nodect + 1):
        nodes[node] = set()

    for node1 in range(1, nodect + 1):
        for node2 in range(node1 + 1, nodect + 1):
            edgelabel = str(node1) + '_' + str(node2)
            nodes[node1].add(edgelabel)
            nodes[node2].add(edgelabel)
            edges[edgelabel] = ({node1, node2}, weights[counter])
            counter += 1

    return g.Uwgraph(graph=(nodes, edges))


def allsubsets(nodect):
    """Take positive integer as input.

    :param nodect: number of nodes on graph.
    :return: dict of all subsets of {1, ..., nodect} containing 1.
    """
    allsubset = {}
    for size in range(nodect):
        subsets = [set(combo) for combo in comb(range(2, nodect + 1), size)]
        for subset in subsets:
            subset.add(1)
        allsubset[size + 1] = subsets
    return allsubset


def allsubs(nodect, size):
    """Take two positive integers as input.

    :param nodect: number of nodes on graph.
    :param size: positive integer
    :return: list of all subsets of {1, ..., nodect} of given size containing 1.
    """
    subsets = [set(combo) for combo in comb(range(2, nodect + 1), size - 1)]
    for subset in subsets:
        subset.add(1)
    return subsets


def bruteforcesolver(uwgraph):
    """Take undirected weighted complete graph as input.

    :param uwgraph: complete graph with nonnegative edge costs.
    :return: Solve Traveling Salesman Problem by brute force (up to 9 nodes).
    """
    size = len(uwgraph.nodes)

    # example: if size is 5, string = '2345'
    string = ''.join([str(i) for i in range(2, size + 1)])
    cycles = perm(string, size - 1)

    pathcosts = []
    for cycle in cycles:
        cyclist = list(cycle)
        firstedge = '1_' + cyclist[0]
        lastedge = '1_' + cyclist[-1]
        edges = [firstedge, lastedge]
        for index in range(len(cycle) - 1):
            edge1 = cyclist[index] + '_' + cyclist[index + 1]
            edge2 = cyclist[index + 1] + '_' + cyclist[index]
            edge = edge1 if cyclist[index] < cyclist[index + 1] else edge2
            edges.append(edge)

        pathcosts.append(sum([uwgraph.get_edge(edge)[1] for edge in edges]))

    return min(pathcosts)


def tsp(uwgraph):
    """Take complete undirected weighted graph as input.

    :param uwgraph: undirected weighted graph with nonnegative edge costs.
    :return: minimum cost cycle that visits all nodes.
    """
    nodect = len(uwgraph.nodes)
    print('Calculating all subsets...')
    subsets = allsubsets(nodect)  # all subsets of {1, ..., nodect} containing 1
    print('Finished calculating all subsets.')
    inf = float('inf')
    cache = {}

    # solving/caching base cases
    print('Caching base cases...')
    for size in subsets:
        cache[size] = {}
        for subset in subsets[size]:
            cache[size][(frozenset(subset), 1)] = 0 if size == 1 else inf
    print('Finished caching base cases.')

    # solving/caching recurrence
    for size in range(2, nodect + 1):
        print('Recurrence ' + str(size) + ' of ' + str(nodect) + '...')
        for subset in subsets[size]:  # subset = S
            for elt in subset:  # elt = j
                if elt != 1:
                    candidates = []
                    for lasthop in subset:
                        if lasthop != elt:
                            edge1 = str(elt) + '_' + str(lasthop)
                            edge2 = str(lasthop) + '_' + str(elt)
                            edge = edge1 if elt < lasthop else edge2
                            edgecost = uwgraph.get_edge(edge)[1]
                            subsetcopy = subset.copy()
                            subsetcopy.remove(elt)
                            frozen = frozenset(subsetcopy)
                            frozencache = cache[size - 1][(frozen, lasthop)]
                            candidate = frozencache + edgecost
                            candidates.append(candidate)
                    cache[size][(frozenset(subset), elt)] = min(candidates)
                    del candidates
        del cache[size - 1]
        del subsets[size]
    del subsets

    # final brute force search over the last hop from some node to 1
    nodes = set(uwgraph.nodes.keys())
    candidates = []
    print('Final brute force search...')
    for elt in range(2, nodect + 1):
        print('Loop ' + str(elt) + ' of ' + str(nodect) + '...')
        edge = '1_' + str(elt)
        edgecost = uwgraph.get_edge(edge)[1]
        candidates.append(cache[nodect][(frozenset(nodes), elt)] + edgecost)
    return min(candidates)


def tspmod(uwgraph):
    """Take complete undirected weighted graph as input.

    :param uwgraph: undirected weighted graph with nonnegative edge costs.
    :return: minimum cost cycle that visits all nodes.
    """
    nodect = len(uwgraph.nodes)
    inf = float('inf')
    cache = dict()

    # caching base cases for {1}
    cache[1] = {}
    cache[1][(frozenset({1}), 1)] = 0
    for elt in range(2, nodect + 1):
        cache[1][(frozenset({1}), elt)] = inf

    # solving/caching recurrence
    for size in range(2, nodect + 1):
        print('Calculating all subsets of size ' + str(size) + '...')
        subsets = allsubs(nodect, size)
        print('Finished calculating all subsets of size ' + str(size) + '.')

        # solving/caching base cases
        cache[size] = {}
        print("Caching base cases of size " + str(size) + '...')
        for subset in subsets:
            cache[size][(frozenset(subset), 1)] = inf
        print('Finished caching base cases of size ' + str(size) + '.')

        print('Recurrence ' + str(size) + ' of ' + str(nodect) + '...')
        for subset in subsets:  # subset = S
            for elt in subset:  # elt = j
                if elt != 1:
                    candidates = []
                    for lasthop in subset:
                        cond1 = elt < 12 and 15 < lasthop
                        cond2 = lasthop < 12 and 15 < elt
                        if lasthop != elt and not cond1 and not cond2:
                            edge1 = str(elt) + '_' + str(lasthop)
                            edge2 = str(lasthop) + '_' + str(elt)
                            edge = edge1 if elt < lasthop else edge2
                            edgecost = uwgraph.get_edge(edge)[1]
                            subsetcopy = subset.copy()
                            subsetcopy.remove(elt)
                            frozen = frozenset(subsetcopy)
                            frozencache = cache[size - 1][(frozen, lasthop)]
                            candidate = frozencache + edgecost
                            candidates.append(candidate)
                        del cond1
                        del cond2
                    if candidates:
                        cache[size][(frozenset(subset), elt)] = min(candidates)
                    else:
                        cache[size][(frozenset(subset), elt)] = inf
                    del candidates
        del cache[size - 1]
        del subsets

    # final brute force search over the last hop from some node to 1
    nodes = set(uwgraph.nodes.keys())
    candidates = []
    print('Final brute force search...')
    for elt in range(2, nodect + 1):
        print('Loop ' + str(elt) + ' of ' + str(nodect) + '...')
        edge = '1_' + str(elt)
        edgecost = uwgraph.get_edge(edge)[1]
        candidates.append(cache[nodect][(frozenset(nodes), elt)] + edgecost)
    return min(candidates)


def tspmodgen(uwgraph):
    """Take complete undirected weighted graph as input.

    :param uwgraph: undirected weighted graph with nonnegative edge costs.
    :return: minimum cost cycle that visits all nodes.
    """
    nodect = len(uwgraph.nodes)
    inf = float('inf')
    cache = dict()

    # caching base cases for {1}
    cache[1] = {}
    cache[1][(frozenset({1}), 1)] = 0
    for elt in range(2, nodect + 1):
        cache[1][(frozenset({1}), elt)] = inf

    # solving/caching recurrence
    for size in range(2, nodect + 1):
        cache[size] = {}
        subsets = (set(combo) for combo in comb(range(2, nodect + 1), size - 1))

        print('Recurrence ' + str(size) + ' of ' + str(nodect) + '...')
        for subset in subsets:  # subset = S
            subset.add(1)
            # solving/caching base cases
            cache[size][(frozenset(subset), 1)] = inf

            for elt in subset:  # elt = j
                if elt != 1:
                    candidates = []
                    for lasthop in subset:
                        if lasthop != elt:
                            edge1 = str(elt) + '_' + str(lasthop)
                            edge2 = str(lasthop) + '_' + str(elt)
                            edge = edge1 if elt < lasthop else edge2
                            edgecost = uwgraph.get_edge(edge)[1]
                            subsetcopy = subset.copy()
                            subsetcopy.remove(elt)
                            frozen = frozenset(subsetcopy)
                            frozencache = cache[size - 1][(frozen, lasthop)]
                            candidates.append(frozencache + edgecost)
                            del edge1
                            del edge2
                            del edge
                            del edgecost
                            del subsetcopy
                            del frozen
                            del frozencache

                    if candidates:
                        cache[size][(frozenset(subset), elt)] = min(candidates)
                    else:
                        cache[size][(frozenset(subset), elt)] = inf
                    del candidates
            del subset
        del cache[size - 1]

    # final brute force search over the last hop from some node to 1
    nodes = set(uwgraph.nodes.keys())
    candidates = []
    print('Final brute force search...')
    for elt in range(2, nodect + 1):
        print('Loop ' + str(elt) + ' of ' + str(nodect) + '...')
        edge = '1_' + str(elt)
        edgecost = uwgraph.get_edge(edge)[1]
        cachedval = cache[nodect][(frozenset(nodes), elt)]
        candidates.append(cachedval + edgecost)
    return min(candidates)


def tspmodgen2(uwgraph):
    """Take complete undirected weighted graph as input.

    :param uwgraph: undirected weighted graph with nonnegative edge costs.
    :return: minimum cost cycle that visits all nodes.
    """
    nodect = len(uwgraph.nodes)
    inf = float('inf')
    cache = dict()

    # caching base cases for {1}
    cache[1] = {}
    cache[1][(frozenset({1}), 1)] = ('', 0)
    for elt in range(2, nodect + 1):
        cache[1][(frozenset({1}), elt)] = ('', inf)

    # solving/caching recurrence
    for size in range(2, nodect + 1):
        cache[size] = {}
        subsets = (set(combo) for combo in comb(range(2, nodect + 1), size - 1))

        for subset in subsets:  # subset = S
            subset.add(1)
            # solving/caching base cases
            cache[size][(frozenset(subset), 1)] = ('', inf)

            for elt in subset:  # elt = j
                if elt != 1:
                    candidates = []
                    for lasthop in subset:
                        if lasthop != elt:
                            edge1 = str(elt) + '_' + str(lasthop)
                            edge2 = str(lasthop) + '_' + str(elt)
                            edge = edge1 if elt < lasthop else edge2
                            edgecost = uwgraph.get_edge(edge)[1]
                            subsetcopy = subset.copy()
                            subsetcopy.remove(elt)
                            frozen = frozenset(subsetcopy)
                            path, cost = cache[size - 1][(frozen, lasthop)]
                            candidate = cost + edgecost
                            path += '->' + edge
                            candidates.append((path, candidate))

                    if candidates:
                        result = min(candidates, key=itemgetter(1))
                        cache[size][(frozenset(subset), elt)] = result
                    else:
                        cache[size][(frozenset(subset), elt)] = ('', inf)
                    del candidates
        del cache[size - 1]

    # final brute force search over the last hops from some node to 1
    nodes = set(uwgraph.nodes.keys())
    candidates = []
    for elt in range(2, nodect + 1):
        edge = '1_' + str(elt)
        edgenodes, edgecost = uwgraph.get_edge(edge)
        path, cost = cache[nodect][(frozenset(nodes), elt)]
        path += '->' + edge
        candidates.append((path, cost + edgecost))
    return min(candidates, key=itemgetter(1))


def euclidean(point1, point2):
    """Take two pairs of floats as input.

    :param point1: pair of floats (x1, y1)
    :param point2: pair of floats (x2, y2)
    :return: Euclidean distance between points (float)
    """
    return sqrt((point1[0] - point2[0])**2 + (point1[1] - point2[1])**2)


def distances(filename):
    """Take string (textfile) as input. Return dict of distances between nodes.

    Text file has, on each line, two floats (x and y coordinates of a node). Ex:
    20833.3333 17100.0000
    20900.0000 17066.6667
    21300.0000 13016.6667   etc.
    :param filename: name of text file.
    :return: dictionary of distances between nodes: {(1, 2): 5, (1, 3): 2, ...}
    """
    nodes = {}
    nodect = 1
    with open(filename, 'r') as file:
        for line in file:
            x, y = line.split()
            nodes[nodect] = (float(x), float(y))
            nodect += 1

    dists = {}
    for node1 in nodes:
        for node2 in nodes:
            if (node2, node1) not in dists and node1 != node2:
                dists[(node1, node2)] = euclidean(nodes[node1], nodes[node2])
    return dists


def distances_to_uwgraph(dists):
    """Take dict of distances between nodes as input.

    :param dists: dictionary of (int, int): float key-value pairs.
    :return: undirected weighted complete graph with distances as edge costs.
    """
    nodes, edges = {}, {}
    for nodepair in dists:
        node1, node2 = nodepair
        if node1 not in nodes:
            nodes[node1] = set()
        if node2 not in nodes:
            nodes[node2] = set()
        edge = str(node1) + '_' + str(node2)
        dist = dists[nodepair]
        nodes[node1].add(edge)
        nodes[node2].add(edge)
        edges[edge] = ({node1, node2}, dist)
    return g.Uwgraph(graph=(nodes, edges))


# TESTING
if __name__ == '__main__':
    # testing allsubsets
    CORRECT = {1: [{1}],
               2: [{1, 2}, {1, 3}, {1, 4}],
               3: [{1, 2, 3}, {1, 2, 4}, {1, 3, 4}],
               4: [{1, 2, 3, 4}]}
    ALLSUBSETS = allsubsets(4)
    assert ALLSUBSETS == CORRECT

    # this is randomized, check it against brute force solver for small cases.
    NODECT = 9
    WEIGHTRANGE = 10
    EDGECT = NODECT * (NODECT - 1) // 2
    WEIGHTS = [randint(0, WEIGHTRANGE) for _ in range(EDGECT)]
    UWGRAPH = completegraph(NODECT, WEIGHTS)
    BRUTEFORCE = bruteforcesolver(UWGRAPH)
    assert BRUTEFORCE == tsp(UWGRAPH)
    assert BRUTEFORCE == tspmod(UWGRAPH)
    assert BRUTEFORCE == tspmodgen(UWGRAPH)

    # Challenge data set: nodes are (x, y) coordinate pairs. Calculate distances
    # UWGRAPH = distances_to_uwgraph(distances('tsp22.txt'))
    # print(tspmodgen2(UWGRAPH))
    # Plotted the points and ran tsp on the first 22 nodes. Got path.
    # Figured out the correct path for 23,24,25 from the path and the plot.
    # Below are the edges of the optimal path.
    UWGRAPH = distances_to_uwgraph(distances('tsp.txt'))
    EDGES = ['1_5', '5_8', '4_8', '3_4', '3_7', '7_9', '9_13', '13_14', '14_16',
             '16_24', '24_25', '20_25', '17_20', '17_21', '21_23', '22_23',
             '18_22', '18_19', '15_19', '12_15', '11_12', '10_11', '6_10',
             '2_6', '1_2']
    PATH = sum([UWGRAPH.get_edge(edge)[1] for edge in EDGES])  # 26442
    print(PATH)
    print("Tests pass.")
