# -*- coding: utf-8 -*-
"""
Created on Mon Sep 16 09:03:46 2019.

@author: spamegg1
"""
import graph as g
from kosaraju import kosaraju


def cnf_to_graph(file):
    """Take text file as input.

    Text file consists of lines of two integers denoting a disjunction of two
    literals (Boolean variables or their negations). For example, the line:
        -2 3
    denotes the clause (minus sign being negation):
        (NOT x2) OR x3.
    Therefore the whole text file is one big clause in conjunctive normal form:
        1 2
        -1 3
        -2 -3
    means the statement:
        (x1 OR x2) AND ((NOT x1) OR x3) AND ((NOT x2) OR (NOT x3)).
    Construct a directed graph:
        with two nodes for each Boolean variable
            one for itself, say x1, and one for its negation, (NOT x1);
        with two edges for each clause (line) in the text file:
            one edge from (NOT x1) to x2, one from (NOT x2) to x1.
    This is because clause x1 OR x2 is logically equivalent to
        ((NOT x1) -> x2) AND ((NOT x1) -> x2).

    Return the directed graph.
    """
    nodes = {}
    edges = {}
    with open(file, 'r') as text:
        for line in text:
            node1, node2 = line.split()               # get two nodes from line
            node1 = int(node1)
            node2 = int(node2)
            newnodes = [node1, -node1, node2, -node2]   # might add 4 nodes max
            for newnode in newnodes:
                if newnode not in nodes:    # if one of the 4 nodes not present
                    nodes[newnode] = [set(), set()]  # create node dict entries

            edgelabel1 = str(-node1) + '_' + str(node2)    # create edge labels
            edgelabel2 = str(-node2) + '_' + str(node1)
            edges[edgelabel1] = (-node1, node2)             # add edges to dict
            edges[edgelabel2] = (-node2, node1)

            nodes[-node1][0].add(edgelabel1)   # OUTG edge from -node1 to node2
            nodes[node2][1].add(edgelabel1)    # INCM edge from node2 to -node1
            nodes[-node2][0].add(edgelabel2)   # OUTG edge from -node2 to node1
            nodes[node1][1].add(edgelabel2)    # INCM edge from node1 to -node2
    return g.Dgraph(graph=(nodes, edges))


def twosat(dgraph):
    """Take a directed graph as input.

    The input graph is a representation of a set of clauses in CNF.
    Return True if given set of clauses in CNF is satisfiable.
    This is logically equivalent to no SCC of the graph containing both a
    Boolean variable and its negation.
    Otherwise return False.
    """
    scc_dict = kosaraju(dgraph)
    for node in scc_dict:
        if scc_dict[-node] == scc_dict[node]:
            return False
    return True


# TESTING
if __name__ == "__main__":
    DGRAPH = cnf_to_graph('2SATtest.txt')
    DGRAPH2 = cnf_to_graph('2SATtest2.txt')
    assert twosat(DGRAPH)
    assert not twosat(DGRAPH2)

    # Challenge data sets
    FILES = ['2sat1.txt', '2sat2.txt', '2sat3.txt', '2sat4.txt', '2sat5.txt',
             '2sat6.txt']
    for FILE in FILES:
        DGRAPH = cnf_to_graph(FILE)
        print(twosat(DGRAPH))
    print("Tests pass.")
