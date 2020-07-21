# -*- coding: utf-8 -*-
"""
Created on Sat Sep 28 10:36:52 2019.

@author: spamegg1
"""
from bst import Bst


class BstAug(Bst):
    """Augments binary search tree class with a count of nodes in subtrees.

    This is to speed up the SELECT operation for a binary search tree:
        'SELECT: given a number i, between 1 and the number of objects, return
        a pointer to the object in the data structure with the ith smallest
        value.'

    Each node in the binary search tree will have a number named SIZE that
    denotes the number of nodes of the subtree which has that node as root.
    For example, leaves will have size 1, a node with 1 leaf child will have
    size 2, a node with 2 leaf children will have size 3, and so on.

    This way, the size of a node's left/right child is the total number of
    nodes in its left/right subtree.
    """
    def addnode(self, node, parname=None, right=False):
        """Add new node, update parent's size."""
        Bst.addnode(self, node, parname, right)
        self.tree[node.name]['size'] = 1
        while parname:        # traverse upward, update all grandparents' sizes
            self.tree[parname]['size'] += 1
            parent = self.parent(parname)
            parname = parent.name if parent else None

    def getsize(self, nodename):
        """Return given node's size."""
        return self.tree[nodename]['size']


def select(tree, rank, nodename):
    """Take an augmented binary search tree and a rank i as input.

    Return the name of the node with the ith smallest value in the subtree
    that has given node as its root, or None.
    To find node with ith smallest value in entire tree, set nodename to
    tree.root.name.
    """
    children = tree.children(nodename)
    left = children['L']
    right = children['R']
    leftsize = tree.getsize(left.name) if left else 0

    if rank == leftsize + 1:         # the +1 is due to the root being included
        return nodename
    if rank < leftsize + 1:
        return select(tree, rank, left.name)
    if rank > leftsize + 1:
        return select(tree, rank - leftsize - 1, right.name)


# TESTING
if __name__ == "__main__":
    TEST = BstAug()
    TEST.createnode('root', 3)
    TEST.createnode('1L', 1, parname='root')
    TEST.createnode('5R', 5, parname='root', right=True)
    TEST.createnode('2R', 2, parname='1L', right=True)
    TEST.createnode('4L', 4, parname='5R')

    RANKS = ['1L', '2R', 'root', '4L', '5R']
    for i in range(len(RANKS)):
        assert select(TEST, i + 1, TEST.root.name) == RANKS[i]
    print("Tests pass.")
