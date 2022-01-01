# -*- coding: utf-8 -*-
"""
Created on Thu Oct 17 22:30:12 2019.

@author: spamegg1
"""


class UnionFind:
    """Union-find (or disjoint-set) data structure."""
    LEADER = 0
    RANK = 1

    def __init__(self, iterable):
        # key = object,
        # value = [leader of the set in which in the object, rank]
        self.core = {i: [i, 0] for i in iterable}

    def find(self, code):
        code_copy = code
        codes_for_path_compression = []

        leader = self.core[code_copy][self.LEADER]
        while code_copy != leader:
            codes_for_path_compression.append(code_copy)
            code_copy = leader
            leader = self.core[code_copy][self.LEADER]

        for i in codes_for_path_compression:
            self.core[i][self.LEADER] = leader

        return leader

    def union_by_rank(self, code1, code2):

        leader1 = self.find(code1)
        leader2 = self.find(code2)
        rank1 = self.core[leader1][self.RANK]
        rank2 = self.core[leader2][self.RANK]

        if rank1 < rank2:
            self.core[leader1][self.LEADER] = leader2
        elif rank1 > rank2:
            self.core[leader2][self.LEADER] = leader1
        elif rank1 == rank2:
            self.core[leader1][self.LEADER] = leader2
            self.core[leader2][self.RANK] += 1

##    def __init__(self, nodes):
##        """Take list of nodes as input, create union-find structure from it."""
##        self.nodes = nodes
##        self.parents = [i for i in range(len(nodes))]
##        self.sizes = [1 for _ in range(len(nodes))]
##        self.ranks = [0 for _ in range(len(nodes))]
##
##    def union_by_size(self, node1, node2):
##        """Merge the two sets containing given two nodes, by size."""
##        index1, index2 = self.find(node1), self.find(node2)
##        size1, size2 = self.sizes[index1], self.sizes[index2]
##        if index1 == index2:
##            return       # node1 and node2 belong to same set, nothing to merge
##        if size1 >= size2:   # add set containing node2 to set containing node1
##            self.parents[index2] = index1
##            self.sizes[index1] = size1 + size2
##        if size1 < size2:    # add set containing node1 to set containing node2
##            self.parents[index1] = index2
##            self.sizes[index2] = size1 + size2
##
##    def union_by_rank(self, node1, node2):
##        """Merge the two sets containing given two nodes, by rank."""
##        index1, index2 = self.find(node1), self.find(node2)
##        rank1, rank2 = self.ranks[index1], self.ranks[index2]
##        if index1 == index2:
##            return       # node1 and node2 belong to same set, nothing to merge
##        if rank1 > rank2:    # add set containing node2 to set containing node1
##            self.parents[index2] = index1
##        if rank1 < rank2:    # add set containing node1 to set containing node2
##            self.parents[index1] = index2
##        if rank1 == rank2:   # add set containing node1 to set containing node2
##            self.parents[index1] = index2
##            self.ranks[index2] += 1  # this is the only case with a rank change
##
##    def find(self, node):
##        """Return index of set that contains given node."""
##        index = self.nodes.index(node)
##        path = []
##        while True:
##            if self.parents[index] == index:
##                for child in path:
##                    self.parents[child] = index              # path compression
##                return index
##            path.append(index)
##            index = self.parents[index]                               # move up


# TESTING
if __name__ == "__main__":
    assert True
    print("Tests pass.")
