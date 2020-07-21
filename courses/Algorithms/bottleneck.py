# -*- coding: utf-8 -*-
"""
Created on Wed Sep 18 08:45:11 2019.

@author: spamegg1
"""
from collections import deque
import graph as g


def bottleneck(wgraph, start, end):
    """Take a weighted directed graph (with nonnegative edge weights) as input.

    Also take two nodes start, end on the graph as input.
    Return the smallest bottleneck of any start->end paths.
    """
    if start == end:
        return 0

    reached = False                             # whether we reached end or not
    explored = []                                      # list of explored nodes
    frontier = deque(wgraph.get_node(start)[0])      # edges yet to be explored
    winner, least = start, -1        # node and path that minimizes edge length
    botleneck = -1                      # keep track of max edge length on path

    while frontier:                # while there are still edges to be explored
        for edge in frontier:   # consider each edge in frontier, find shortest
            newnode = wgraph.get_edge(edge)[1]                   # head of edge
            cost = wgraph.get_edge(edge)[2]                 # path cost of edge

            # if this is the first edge we look at, or gives us a shorter edge
            if least < 0 or cost < least:
                winner = newnode                                # update winner
                least = cost                           # update min edge length

        explored.append(winner)     # after looking at each edge, mark explored
        if least > botleneck:
            botleneck = least                  # update max edge length on path
        least = -1                       # reset min edge length for next round

        if winner == end:                        # path is complete, end search
            reached = True                                 # we reached the end
            break

        # incoming edges of winner can't be in frontier, remove them
        incoming = wgraph.get_node(winner)[1]
        for edge in incoming:
            if edge in frontier:
                frontier.remove(edge)   # tail of such edge is explored already

        # outgoing edges of winner with explored heads can't be in frontier
        for newedge in wgraph.get_node(winner)[0]:
            head = wgraph.get_edge(newedge)[1]
            if head not in explored:
                frontier.append(newedge)     # only add unexplored headed edges

    # if loop finishes with reached False, end is not reachable from start
    return botleneck if reached else None


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
    assert bottleneck(WTEST, 's', 'v') == 1
    assert bottleneck(WTEST, 's', 'w') == 2
    assert bottleneck(WTEST, 's', 't') == 3
    assert bottleneck(WTEST, 'v', 'w') == 2
    assert bottleneck(WTEST, 'v', 't') == 3
    assert bottleneck(WTEST, 'w', 't') == 3

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
    CORRECT1 = [0, 8, 1, 8, 4, 5, 8, 5, 8, 8, 5]
    CORRECT2 = [None, 0, None, 2, None, 7, 6, 3, 8, 3, None]
    CORRECT3 = [7, 8, 0, 8, 4, 5, 8, 5, 8, 8, 5]
    CORRECT4 = [None, 8, None, 0, None, 8, 6, 8, 8, 8, None]
    CORRECT5 = [7, 8, 7, 8, 0, 7, 8, 7, 8, 8, 7]
    CORRECT6 = [None, None, None, None, None, 0, None, 9, None, 9, None]
    CORRECT7 = [None, 8, None, 8, None, 8, 0, 8, 8, 8, None]
    CORRECT8 = [None, None, None, None, None, 7, None, 0, None, 9, None]
    CORRECT9 = [None, 6, None, 5, None, 7, 6, 4, 0, 6, None]
    CORRECT10 = [None, None, None, None, None, 7, None, 3, None, 0, None]
    CORRECT11 = [None, None, None, None, None, 2, None, 1, None, 9, 0]
    CORRECT = [CORRECT1, CORRECT2, CORRECT3, CORRECT4, CORRECT5, CORRECT6,
               CORRECT7, CORRECT8, CORRECT9, CORRECT10, CORRECT11]
    for i in range(1, 12):
        for j in range(1, 12):
            assert bottleneck(WTEST, str(i), str(j)) == CORRECT[i-1][j-1]
    print("tests pass")
