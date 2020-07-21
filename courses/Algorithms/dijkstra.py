# -*- coding: utf-8 -*-
"""
Created on Mon Sep 16 18:37:24 2019.

@author: spamegg1
"""
from collections import deque
import graph as g


def dijkstra(wgraph, node):
    """Take a weighted directed graph (with nonnegative edge weights) as input.

    Also take a node s on the graph as input.
    Return the shortest path distance of every node from s.
    """
    explored = {node: 0}          # dict of nodes with their shortest distances
    edges = wgraph.get_node(node)[0]               # outgoing edges from source
    frontier = deque(edges)                          # edges yet to be explored
    winner, least = node, -1     # choose node and path that minimizes distance

    while frontier:                # while there are still edges to be explored
        for edge in frontier:   # consider each edge in frontier, find shortest
            prevnode = wgraph.get_edge(edge)[0]                  # tail of edge
            newnode = wgraph.get_edge(edge)[1]                   # head of edge
            cost = wgraph.get_edge(edge)[2]                 # path cost of edge

            # if this is the first edge we look at, or gives us a shorter path
            if least < 0 or explored[prevnode] + cost < least:
                winner = newnode                                # update winner
                least = explored[prevnode] + cost   # update shortest path cost

        explored[winner] = least    # after looking at each edge, mark explored
        least = -1                    # reset shortest path cost for next round

        # incoming edges of winner can't be in frontier, remove them
        incoming = wgraph.get_node(winner)[1]
        for edge in incoming:
            if edge in frontier:
                frontier.remove(edge)   # tail of such edge is explored already

        # outgoing edges of winner with explored heads can't be in frontier
        outgoing = wgraph.get_node(winner)[0]
        for newedge in outgoing:
            head = wgraph.get_edge(newedge)[1]
            if head not in explored:
                frontier.append(newedge)     # only add unexplored headed edges

    return explored


def udijkstra(uwgraph, node):
    """Take an undirected weighted graph (with nonnegative weights) as input.

    Also take a node s on the graph as input.
    Return the shortest path distance of every node from s.
    """
    graphsize = len(uwgraph.nodes)
    explored = {node: 0}          # dict of nodes with their shortest distances
    frontier = deque(uwgraph.get_node(node))         # edges yet to be explored
    winner, least = node, -1     # choose node and path that minimizes distance
    edges_seen = set()                                      # edges seen so far

    while frontier:                # while there are still edges to be explored
        bestedge = None
        for edge in frontier:   # consider each edge in frontier, find shortest
            node1, node2 = uwgraph.get_edge(edge)[0]
            if node1 in explored:
                prevnode, newnode = node1, node2
            elif node2 in explored:
                prevnode, newnode = node2, node1
            cost = uwgraph.get_edge(edge)[1]                # path cost of edge

            # if this is the first edge we look at, or gives us a shorter path
            if least < 0 or explored[prevnode] + cost < least:
                winner = newnode                                # update winner
                least = explored[prevnode] + cost   # update shortest path cost
                bestedge = edge

        explored[winner] = least    # after looking at each edge, mark explored
        if len(explored) == graphsize:
            break
        least = -1                    # reset shortest path cost for next round
        edges_seen.add(bestedge)
        frontier.remove(bestedge)

        # consider edges of winner
        for edge in uwgraph.get_node(winner):
            if edge not in edges_seen:    # filter edges that were already seen
                frontier.append(edge)

        for edge in list(frontier):
            node1, node2 = uwgraph.get_edge(edge)[0]
            # both end points cannot be in explored simultaneously
            if node1 in explored and node2 in explored:
                frontier.remove(edge)

    return explored


# TESTING
if __name__ == "__main__":
    WNODES = {'s': [{'e', 'f'}, {}],
              'v': [{'g', 'i'}, {'e'}],
              'w': [{'h'}, {'f', 'i'}],
              't': [{}, {'g', 'h'}]}
    WEDGES = {'e': ('s', 'v', 1),
              'f': ('s', 'w', 4),
              'g': ('v', 't', 6),
              'h': ('w', 't', 3),
              'i': ('v', 'w', 2)}
    WTEST = g.Wgraph(graph=(WNODES, WEDGES))
    assert dijkstra(WTEST, 's') == {'s': 0, 'v': 1, 'w': 3, 't': 6}
    assert dijkstra(WTEST, 'v') == {'v': 0, 'w': 2, 't': 5}
    assert dijkstra(WTEST, 'w') == {'w': 0, 't': 3}
    assert dijkstra(WTEST, 't') == {'t': 0}

    WNODES = {'1': [{'1_3'}, {'5_1'}],
              '2': [{'2_4', '2_10'}, {'9_2'}],
              '3': [{'3_5', '3_11'}, {'1_3'}],
              '4': [{'4_7'}, {'2_4', '9_4'}],
              '5': [{'5_1', '5_7', '5_9'}, {'3_5'}],
              '6': [{'6_10'}, {'8_6', '11_6'}],
              '7': [{'7_9'}, {'4_7', '5_7'}],
              '8': [{'8_6'}, {'9_8', '10_8', '11_8'}],
              '9': [{'9_2', '9_4', '9_8'}, {'5_9', '7_9'}],
              '10': [{'10_8'}, {'2_10', '6_10'}],
              '11': [{'11_6', '11_8'}, {'3_11'}]}
    WEDGES = {'1_3': ('1', '3', 1),
              '2_4': ('2', '4', 2), '2_10': ('2', '10', 3),
              '3_5': ('3', '5', 4), '3_11': ('3', '11', 5),
              '4_7': ('4', '7', 6),
              '5_1': ('5', '1', 7), '5_7': ('5', '7', 8), '5_9': ('5', '9', 9),
              '6_10': ('6', '10', 9),
              '7_9': ('7', '9', 8),
              '8_6': ('8', '6', 7),
              '9_2': ('9', '2', 6), '9_4': ('9', '4', 5), '9_8': ('9', '8', 4),
              '10_8': ('10', '8', 3),
              '11_6': ('11', '6', 2), '11_8': ('11', '8', 1)}
    WTEST = g.Wgraph(graph=(WNODES, WEDGES))
    ANSWERS = [{'1': 0, '3': 1, '5': 5, '11': 6, '8': 7, '6': 8, '7': 13,
                '9': 14, '10': 17, '4': 19, '2': 20},
               {'2': 0, '4': 2, '10': 3, '8': 6, '7': 8, '6': 13, '9': 16},
               {'3': 0, '5': 4, '11': 5, '8': 6, '6': 7, '1': 11, '7': 12,
                '9': 13, '10': 16, '4': 18, '2': 19},
               {'4': 0, '7': 6, '9': 14, '8': 18, '2': 20, '10': 23, '6': 25},
               {'5': 0, '1': 7, '3': 8, '7': 8, '9': 9, '11': 13, '8': 13,
                '4': 14, '2': 15, '6': 15, '10': 18},
               {'6': 0, '10': 9, '8': 12},
               {'7': 0, '9': 8, '8': 12, '4': 13, '2': 14, '10': 17, '6': 19},
               {'8': 0, '6': 7, '10': 16},
               {'9': 0, '8': 4, '4': 5, '2': 6, '10': 9, '7': 11, '6': 11},
               {'10': 0, '8': 3, '6': 10},
               {'11': 0, '8': 1, '6': 2, '10': 11}]
    for i in range(1, 12):
        assert dijkstra(WTEST, str(i)) == ANSWERS[i - 1]

    # undirected weighted graph tests
    UWGRAPH = g.node_to_uwgraph('problem9.8test.txt')
    assert udijkstra(UWGRAPH, '1') == {'1': 0, '2': 1, '3': 2, '4': 3, '5': 4,
                                       '6': 4, '7': 3, '8': 2}
    UWGRAPH = g.node_to_uwgraph('uwdijktest.txt')
    assert udijkstra(UWGRAPH, '1') == {'1': 0, '3': 1, '5': 5, '11': 6,
                                       '6': 8, '10': 10, '9': 11, '2': 13,
                                       '7': 13, '4': 15, '8': 7}
    assert udijkstra(UWGRAPH, '2') == {'2': 0, '4': 2, '10': 3, '8': 6,
                                       '11': 7, '7': 8, '6': 9, '3': 12,
                                       '1': 13, '5': 15, '9': 6}
    assert udijkstra(UWGRAPH, '3') == {'3': 0, '1': 1, '5': 4, '11': 5,
                                       '6': 7, '10': 9, '9': 10, '2': 12,
                                       '7': 12, '4': 14, '8': 6}
    assert udijkstra(UWGRAPH, '4') == {'4': 0, '2': 2, '10': 5, '9': 5,
                                       '8': 8, '11': 9, '6': 11, '3': 14,
                                       '5': 14, '1': 15, '7': 6}
    assert udijkstra(UWGRAPH, '5') == {'5': 0, '3': 4, '1': 5, '7': 8,
                                       '9': 9, '8': 10, '6': 11, '10': 13,
                                       '4': 14, '2': 15, '11': 9}
    assert udijkstra(UWGRAPH, '6') == {'6': 0, '11': 2, '8': 3, '10': 6,
                                       '3': 7, '1': 8, '2': 9, '5': 11,
                                       '4': 11, '7': 15, '9': 7}
    assert udijkstra(UWGRAPH, '7') == {'7': 0, '4': 6, '2': 8, '5': 8,
                                       '9': 8, '10': 11, '8': 12, '3': 12,
                                       '1': 13, '11': 13, '6': 15}
    assert udijkstra(UWGRAPH, '8') == {'8': 0, '11': 1, '6': 3, '10': 3,
                                       '9': 4, '2': 6, '3': 6, '1': 7,
                                       '4': 8, '5': 10, '7': 12}
    assert udijkstra(UWGRAPH, '9') == {'9': 0, '8': 4, '4': 5, '11': 5,
                                       '2': 6, '6': 7, '10': 7, '7': 8,
                                       '5': 9, '3': 10, '1': 11}
    assert udijkstra(UWGRAPH, '10') == {'10': 0, '2': 3, '8': 3, '11': 4,
                                        '4': 5, '6': 6, '9': 7, '3': 9,
                                        '1': 10, '7': 11, '5': 13}
    assert udijkstra(UWGRAPH, '11') == {'11': 0, '8': 1, '6': 2, '10': 4,
                                        '3': 5, '9': 5, '1': 6, '2': 7,
                                        '5': 9, '4': 9, '7': 13}

    # THIS IS THE 200 NODE CHALLENGE SET!
    UWGRAPH = g.node_to_uwgraph('dijkstraData.txt')
    NODES = ['7', '37', '59', '82', '99', '115', '133', '165', '188', '197']
    RESULT = udijkstra(UWGRAPH, '1')
    ANSWER = []
    CORRECT = [2599, 2610, 2947, 2052, 2367, 2399, 2029, 2442, 2505, 3068]
    for nod in NODES:
        ANSWER.append(RESULT[nod])
    assert ANSWER == CORRECT
    print("Tests pass.")
