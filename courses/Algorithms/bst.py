# -*- coding: utf-8 -*-
"""
Created on Mon Sep 23 22:08:58 2019.

@author: spamegg1
"""


class Node:
    """Node object class, representing a node in a binary search tree.

    Each node has a name and a value.
    """

    def __init__(self, name, value):
        """Create node."""
        self.name = name
        self.value = value

    def __repr__(self):
        """Pretty printing for Node objects."""
        return "(" + str(self.name) + ", " + str(self.value) + ')'

    def __str__(self):
        """Pretty printing for Node objects."""
        return '(' + str(self.name) + ', ' + str(self.value) + ')'


class Bst:
    """Binary search tree object class.

    Tree is a dictionary of entries of the form
        'name': {'node': node0, 'L': node1, 'R': node2, 'P': node3}
    where node1, node2 and node3 can be None or other nodes.
    """

    def __init__(self):
        """Create empty binary search tree."""
        self.tree = {}
        self.root = None

    def __str__(self):
        """Pretty printing for binary search trees."""
        rootname = self.root.name if self.root else None
        result = 'root=' + str(rootname) + '\n'
        for key, value in self.tree.items():
            result += str(key) + ': '
            result += str(value) + '\n'
        return result

    def addnode(self, node, parname=None, right=False):
        """Take node object and two optional parameters as input.

        Add node to tree. Establish parent child relations accordingly.
        By default, adds a left node to given parent node.
        To add a right node, set optional argument right to True.
        If parent is None and tree is empty, adds node to tree as root.
        """
        if node.name not in self.tree:
            if not parname:
                # if not self.tree:                  # empty tree, setting root
                self.tree[node.name] = {'node': node, 'L': None, 'R': None,
                                        'P': None}
                self.root = node                                 # set root
                # else:
                #     print("add_node failed, root already exists: "
                #           + self.root.name)
            elif parname in self.tree:
                parent = self.tree[parname]['node']
                # establish node's parent in tree
                self.tree[node.name] = {'node': node, 'L': None, 'R': None,
                                        'P': parent}
                # establish node as child of parent
                if right:
                    if self.tree[parname]['R'] is None:
                        self.tree[parname]['R'] = node
                    else:
                        print("add_node() failed, parent already has a right "
                              + "child.")
                elif self.tree[parname]['L'] is None:
                    self.tree[parname]['L'] = node
                else:
                    print("add_node() failed, parent already has a left child")
            else:
                print("add_node() failed, no node with parentname in tree.")
        else:
            print("add_node() failed, tree already has a node with that name.")
        self.updatesize(node)               # increment sizes of node and above

    def updatesize(self, node, decr=False):
        """Update sizes of given node, and nodes above given node."""
        if decr:
            self.tree[node.name]['size'] -= 1     # for deletion, not insertion
        else:
            self.tree[node.name]['size'] = 1      # for insertion, not deletion
        parent = self.parent(node.name)
        parname = parent.name if parent else None
        while parname:             # traverse upward, update all ancestor sizes
            self.tree[parname]['size'] += -1 if decr else 1
            parent = self.parent(parname)
            parname = parent.name if parent else None

    def createnode(self, name, value, parname=None, right=False):
        """Take node name and value as input, create and add node to tree.

        Take optional parent and right arguments just like addnode() above.
        """
        node = Node(name, value)
        self.addnode(node, parname, right)

    def getnode(self, nodename):
        """Take node name as input, return node in tree, or None."""
        if nodename in self.tree:
            return self.tree[nodename]['node']
        return None

    def getval(self, nodename):
        """Take node name as input, return value of that node in tree."""
        if nodename in self.tree:
            return self.getnode(nodename).value
        return None

    def children(self, nodename):
        """Take node name as input, return dict of node's children."""
        if nodename in self.tree:
            nodeitem = self.tree[nodename]
            return {'L': nodeitem['L'], 'R': nodeitem['R']}
        return None

    def parent(self, nodename):
        """Take node name as input, return node's parent in tree, or None."""
        if self.root.name == nodename:
            return None
        return self.tree[nodename]['P']

    def childcount(self, nodename):
        """Take node name as input, return number of node's children (0-2)."""
        chcount = 0
        if self.tree[nodename]['L']:
            chcount += 1
        if self.tree[nodename]['R']:
            chcount += 1
        return chcount

    def depth(self, nodename):
        """Take node name as input, return node's depth in tree."""
        if self.root.name == nodename:
            return 0
        return 1 + self.depth(self.parent(nodename).name)

    def subtree(self, nodename, acc, depth):
        """Take node name and a dictionary as input.

        Return a copy of the subtree with node as its root.
        Dictionary acc is used as accumulator recursively.
        When you call subtree directly, acc should be an empty dictionary.
        Parameter depth is used to check whether method was called directly or
        recursively by another call of the method.
        When you call subtree directly, depth should be the depth of the node.
        """
        node = self.getnode(nodename)
        nodedepth = self.depth(nodename)
        chcount = self.childcount(nodename)
        parent = self.tree[nodename]['P']
        children = self.children(nodename)
        left = children['L']
        right = children['R']
        size = self.calcsize(nodename)

        if not chcount:                       # node is a leaf, has no children
            if nodedepth == depth:   # this function was called non-recursively
                sbtree = Bst()
                sbtree.addnode(node)     # this will set root correctly to node
                return sbtree
            # otherwise function was called recursively from a higher level
            acc[nodename] = {'node': node, 'L': None, 'R': None, 'P': parent,
                             'size': size}
            return acc      # return updated accumulator to higher level caller

        if chcount == 1:                          # case where node has 1 child
            child = left if left else right
            newacc = self.subtree(child.name, acc, depth)   # child updates acc
            if nodedepth == depth:   # this function was called non-recursively
                sbtree = Bst()
                sbtree.root = node      # node is the "root" of desired subtree
                # establish parent child relation in subtree before returning:
                # declare child as left or right child of node
                # node will be declared parent of child in above recursive call
                if left:
                    newacc[nodename] = {'node': node, 'L': child, 'R': None,
                                        'P': None, 'size': size}
                elif right:
                    newacc[nodename] = {'node': node, 'L': None, 'R': child,
                                        'P': None, 'size': size}
                sbtree.tree = newacc
                return sbtree
            # otherwise function was called recursively from a higher level
            # establish parent child relation in subtree before returning:
            # declare parent (the higher level caller) as parent of node
            # declare child as child of node
            if left:
                acc[nodename] = {'node': node, 'L': child, 'R': None,
                                 'P': parent, 'size': size}
            elif right:
                acc[nodename] = {'node': node, 'L': None, 'R': right,
                                 'P': parent, 'size': size}
            return acc      # return updated accumulator to higher level caller

        if chcount == 2:        # main case, recurse on left and right subtrees
            leftacc = self.subtree(left.name, acc, depth)
            rightacc = self.subtree(right.name, acc, depth)
            newacc = {**leftacc, **rightacc}
            if nodedepth == depth:   # this function was called non-recursively
                sbtree = Bst()
                sbtree.root = node      # node is the "root" of desired subtree
                # establish parent child relation in subtree before returning:
                # declare child as left or right child of node
                # node will be declared parent of child in above recursive call
                newacc[nodename] = {'node': node, 'L': left, 'R': right,
                                    'P': None, 'size': size}
                sbtree.tree = newacc
                return sbtree
            # otherwise function was called recursively from a higher level
            # establish parent child relation in subtree before returning:
            # declare parent (the higher level caller) as parent of node
            acc[nodename] = {'node': node, 'L': left, 'R': right, 'P': parent,
                             'size': size}
            return acc      # return updated accumulator to higher level caller

    def paste(self, parentname, childname, right=False):
        """Take parent name and child name as input.

        Paste child's subtree to parent.
        Also take optional Boolean 'right' argument (False/left by default).
        To paste subtree as right child of parent, set 'right' to True.
        """
        parent = self.tree[parentname]['node']
        child = self.tree[childname]['node']
        self.tree[childname]['P'] = parent
        if right:
            self.tree[parentname]['R'] = child
        else:
            self.tree[parentname]['L'] = child

    def remove(self, nodename):
        """Take nodename as input. Remove node along with its subtree."""
        chcount = self.childcount(nodename)
        if chcount == 0:          # node has no children, can be safely deleted
            del self.tree[nodename]
            return
        children = self.children(nodename)
        for child in children.values():
            if child:
                self.remove(child.name)    # recursively remove child's subtree
        del self.tree[nodename]

    def getsize(self, nodename):
        """Return given node's size."""
        return self.tree[nodename]['size']

    def calcsize(self, nodename):
        """Take nodename as input. Return size of node."""
        chcount = self.childcount(nodename)
        if not chcount:
            return 1

        children = self.children(nodename)
        left = children['L']
        right = children['R']
        size = 0
        if left:
            size += self.calcsize(left.name)
        if right:
            size += self.calcsize(right.name)
        return 1 + size


def search(tree, value, nodename):
    """Take a binary search tree, a value and a node name as input.

    Return a path of nodes in the tree from the node to the value, or None.
    To search entire tree, set nodename to tree.root.name.
    """
    nodeval = tree.getval(nodename)
    if nodeval == value:                              # we are lucky, found val
        return [nodename]

    children = tree.children(nodename)
    chcount = tree.childcount(nodename)
    if not chcount:
        return None                      # node was a leaf, we did not find val

    if chcount == 1:                          # case when there is only 1 child
        left = children['L']
        right = children['R']
        child = left if left else right
        if child.value < nodeval:                # child is on the left of root
            if value < nodeval:             # val we search is also on the left
                return [nodename] + search(tree, value, child.name)
            return None           # val we search is on right, so can't find it
        if nodeval < child.value:               # child is on the right of root
            if nodeval < value:            # val we search is also on the right
                return [nodename] + search(tree, value, child.name)
            return None            # val we search is on left, so can't find it

    # main case: 2 children at the node
    if value < nodeval:
        child = children['L']                           # recurse to left child
    elif value > nodeval:
        child = children['R']                          # recurse to right child

    result = search(tree, value, child.name)  # search one of children's sbtree
    if not result:
        return None
    return [nodename] + result


def minmax(tree, nodename, maxm=False):
    """Take binary search tree and a node name as input.

    Return nodename with min or max value in subtree with node as its root.
    To find min/max of whole tree, set nodename to tree.root.name.
    Take optional Boolean argument for min or max (False/min by default).
    Set maxm to True to find maximum value instead of minimum.
    """
    curname = nodename
    while True:
        children = tree.children(curname)
        chcount = tree.childcount(curname)
        if not chcount:                             # if current node is a leaf
            return curname
        if maxm:
            if not children['R']:               # if curnode has no right child
                return curname               # no right subtree, curname is max
            curname = children['R'].name            # go to right child for max
        else:
            if not children['L']:                   # curname has no left child
                return curname                # no left subtree, curname is min
            curname = children['L'].name             # go to left child for min


def pred(tree, nodename):
    """Take binary search tree, a nodename as input. Return a nodename or None.

    Returned node's value is given node's value's predecessor in tree.
    If given node has min value in tree, return None.
    """
    children = tree.children(nodename)
    if children['L']:
        left = children['L'].name
        return minmax(tree, left, maxm=True)   # max of subtree is predeccessor

    # If given node does not have a left child, traverse upward.
    # Given node becomes the new "child", we check its parent,
    # if this new child is a right child of parent, return parent.
    childname = nodename
    parentname = tree.parent(nodename).name
    children = tree.children(parentname)
    while True:
        if children['R']:                         # if parent has a right child
            right = children['R'].name
            if childname == right:          # if child is right child of parent
                return parentname
        if parentname == tree.root.name:             # if we ran out of parents
            return None                                # no predecessors exist!
        # otherwise keep traversing up
        childname = parentname             # parent becomes the new child again
        parentname = tree.parent(parentname).name
        children = tree.children(parentname)


def succ(tree, nodename):
    """Take binary search tree, a nodename as input. Return a nodename or None.

    Returned node's value is given node's value's successor in tree.
    If given node has max value in tree, return None.
    """
    children = tree.children(nodename)
    if children['R']:
        right = children['R'].name
        return minmax(tree, right)                # min of subtree is successor

    # given node does not have a right child, traverse upward
    # given node becomes the new "child", we check its parent
    # if this new child is a left child of parent, return parent.
    childname = nodename
    parentname = tree.parent(nodename).name
    children = tree.children(parentname)
    while True:
        if children['L']:
            left = children['L'].name
            if childname == left:            # if child is left child of parent
                return parentname
        if parentname == tree.root.name:                # we ran out of parents
            return None                                  # no successor exists!
        # otherwise keep traversing up
        childname = parentname             # parent becomes the new child again
        parentname = tree.parent(parentname).name
        children = tree.children(parentname)


def output_sorted(tree, nodename):
    """Take binary search tree and a node name as input.

    Print values of subtree with node as its root in increasing order.
    To print all values in tree, set nodename to tree.root.name.
    """
    children = tree.children(nodename)
    if children['L']:
        left = children['L'].name
        output_sorted(tree, left)
    print(nodename)
    if children['R']:
        right = children['R'].name
        output_sorted(tree, right)


def delete(tree, value, nodename):
    """Take binary search tree, a value and a node name as input.

    Delete node with value, if it exists, from tree whose root is given node.
    To delete a node from the whole tree, set nodename to tree.root.name.
    """
    nodes = search(tree, value, nodename)
    if not nodes:
        return              # given value is not in the tree, nothing to delete
    nodename = nodes[-1]
    node = tree.getnode(nodename)
    children = tree.children(nodename)
    chcount = tree.childcount(nodename)
    parent = tree.parent(nodename)

    if not chcount:                     # node is a leaf, can be safely deleted
        if parent:                                       # update node's parent
            parchildren = tree.children(parent.name)
            for child in parchildren:
                if parchildren[child] == node:
                    parchildren[child] = None
        # before deleting node, update sizes of its ancestors!
        tree.updatesize(node, decr=True)
        del tree.tree[nodename]
        if node == tree.root:
            tree.root = None
        return

    if chcount == 1:                                 # case with only one child
        left = children['L']
        right = children['R']
        child = left if left else right
        if parent:
            parchildren = tree.children(parent.name)
            right = parchildren['R'] == node
            tree.paste(parent.name, child.name, right)
            tree.updatesize(node, decr=True)
            del tree.tree[nodename]
        else:                                   # node must be the root of tree
            tree.root = child                   # so child becomes the new root
            tree.tree[child.name]['P'] = None
            tree.updatesize(node, decr=True)
            del tree.tree[nodename]
        return

    if chcount == 2:                     # main case, given node has 2 children
        # find given node's predecessor, replace node with it, delete node
        predname = pred(tree, nodename)
        prednode = tree.tree[predname]['node']
        predval = prednode.value
        predchildren = tree.children(predname)
        predchcount = tree.childcount(predname)

        # since node has 2 children, predecessor is in node's left subtree.
        # It has max value in that subtree.
        # Predecessor cannot have a right child,
        # because pred calculates by following down right children.
        # predecessor might or might not have a left child.
        # this reduces us to the previous case (chcount == 1).
        # Swap node with its predecessor, then repeat above algorithm.
        if not predchcount:                     # if predecessor has 0 children
            predparent = tree.parent(predname)
            if tree.tree[predparent.name]['L'] == prednode:
                tree.tree[predparent.name]['L'] = None
            if tree.tree[predparent.name]['R'] == prednode:
                tree.tree[predparent.name]['R'] = None
            tree.updatesize(predparent, decr=True)
            node.name = predname
            node.value = predval
            tree.tree[node.name] = tree.tree.pop(nodename)
            return

        if predchcount == 1:                   # predecessor has 1 (left) child
            predchild = predchildren['L']
            predparent = tree.parent(predname)
            tree.paste(predparent.name, predchild.name)
            tree.updatesize(predparent, decr=True)
            node.name = predname
            node.value = predval
            tree.tree[node.name] = tree.tree.pop(nodename)
            return


def insert(tree, value, nodename):
    """Take a binary search tree, a value and a nodename as input.

    Add a new node with given value to the subtree with given node as its root.
    Maintains binary search tree property and correct sizes for all nodes.
    If a node with the same value already exists, the insert is ignored.
    To insert the value into the whole tree, set nodename to tree.root.name.
    To insert a value into an empty tree, set nodename to None.
    """
    if not nodename:
        tree.createnode('root', value)
        return

    node = tree.getnode(nodename)
    if node.value == value:                   # duplicate value being inserted!
        return                                      # ignore insert, do nothing

    children = tree.children(node.name)
    chcount = tree.childcount(node.name)

    if not chcount:                                   # if node has no children
        right = value > node.value
        name = str(value)
        name += 'R' if right else 'L'
        tree.createnode(name, value, parname=node.name, right=right)
        return

    if chcount == 1:                          # case when there is only 1 child
        left = children['L']
        right = children['R']
        child = left if left else right
        if left:
            if node.value < value:
                name = str(value) + 'R'
                tree.createnode(name, value, parname=node.name, right=True)
                return
            insert(tree, value, child.name)   # otherwise go into child subtree
        if right:
            if value < node.value:
                name = str(value) + 'L'
                tree.createnode(name, value, parname=node.name)
                return
            insert(tree, value, child.name)   # otherwise go into child subtree

    child = None
    if chcount == 2:                        # main case: 2 children at the node
        if value < node.value:
            child = children['L']
        elif value > node.value:
            child = children['R']
        insert(tree, value, child.name)  # insert to one of children's subtrees


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
    TEST = Bst()
    TEST.createnode('root', 3)
    TEST.createnode('1L', 1, parname='root')
    TEST.createnode('5R', 5, parname='root', right=True)
    TEST.createnode('2R', 2, parname='1L', right=True)
    TEST.createnode('4L', 4, parname='5R')
    # root=4L
    # '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': None, 'size': 1}
    print(TEST.subtree('4L', {}, 2))
    # root=2L
    # '2R': {'node': ('2R', 2), 'L': None, 'R': None, 'P': None, 'size': 1}
    print(TEST.subtree('2R', {}, 2))
    # root=5R
    # '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('5R', 5), 'size':1}
    # '5R': {'node': ('5R', 5), 'L': ('4L', 4), 'R': None, 'P': None, 'size':2}
    print(TEST.subtree('5R', {}, 1))
    # root=1L
    # '2R': {'node': ('2R', 2), 'L': None, 'R': None, 'P': ('1L', 1), 'size':1}
    # '1L': {'node': ('1L', 1), 'L': None, 'R': ('2R', 2), 'P': None, 'size':2}
    print(TEST.subtree('1L', {}, 1))
    # root=root
    # '2R': {'node': ('2R', 2), 'L': None, 'R': None, 'P': ('1L', 1), 'size':1}
    # '1L': {'node': ('1L', 1), 'L': None, 'R': ('2R', 2), 'P': ('root', 3),
    #           'size': 2}
    # '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('5R', 5), 'size':1}
    # '5R': {'node': ('5R', 5), 'L': ('4L', 4), 'R': None, 'P': ('root', 3),
    #           'size': 1}
    # 'root': {'node': ('root', 3), 'L': ('1L', 1), 'R': ('5R', 5), 'P': None,
    #           'size': 5}
    print(TEST.subtree('root', {}, 0))

    RANKS = ['1L', '2R', 'root', '4L', '5R']
    for i in range(len(RANKS)):
        assert select(TEST, i + 1, TEST.root.name) == RANKS[i]

    assert search(TEST, 3, 'root') == ['root']
    assert search(TEST, 1, 'root') == ['root', '1L']
    assert search(TEST, 5, 'root') == ['root', '5R']
    assert search(TEST, 2, 'root') == ['root', '1L', '2R']
    assert search(TEST, 4, 'root') == ['root', '5R', '4L']
    assert minmax(TEST, 'root') == '1L'
    assert minmax(TEST, 'root', maxm=True) == '5R'

    # testing pred and succ
    assert pred(TEST, 'root') == '2R'
    assert pred(TEST, '1L') is None
    assert pred(TEST, '5R') == '4L'
    assert pred(TEST, '2R') == '1L'
    assert pred(TEST, '4L') == 'root'
    assert succ(TEST, 'root') == '4L'
    assert succ(TEST, '1L') == '2R'
    assert succ(TEST, '5R') is None
    assert succ(TEST, '2R') == 'root'
    assert succ(TEST, '4L') == '5R'

    # should print 1L, 2R, root, 4L, 5R in order
    output_sorted(TEST, 'root')

    delete(TEST, 3, TEST.root.name)  # deleting root
    # root=2R
    # {'1L': {'node': ('1L', 1), 'L': None, 'R': None, 'P': ('2R', 2)},
    #  '5R': {'node': ('5R', 5), 'L': ('4L', 4), 'R': None, 'P': ('2R', 2)},
    #  '2R': {'node': ('2R', 2), 'L': ('1L', 1), 'R': ('5R', 5), 'P': None},
    #  '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('5R', 5)}}
    print(TEST)
    delete(TEST, 5, TEST.root.name)  # deleting right subtree
    # root=2R
    # {'1L': {'node': ('1L', 1), 'L': None, 'R': None, 'P': ('2R', 2)},
    #  '2R': {'node': ('2R', 2), 'L': ('1L', 1), 'R': ('4L', 4), 'P': None},
    #  '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('2R', 2)}}
    print(TEST)
    delete(TEST, 2, TEST.root.name)  # deleting new root
    # root=1L
    # {'1L': {'node': ('1L', 1), 'L': None, 'R': ('4L', 4), 'P': None},
    #  '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('1L', 1)}}
    print(TEST)
    delete(TEST, 1, TEST.root.name)  # deleting new root again
    # root=4L
    # {'4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': None}}
    print(TEST)
    delete(TEST, 4, TEST.root.name)  # deleting final node
    print(TEST)  # root=None {}

    insert(TEST, 3, None)
    # root='root'
    # 'root': {'node': (root, 3), 'L': None, 'R': None, 'P': None}
    print(TEST)
    insert(TEST, 1, 'root')
    # root='root'
    # 'root': {'node': ('root', 3), 'L': (1L, 1), 'R': None, 'P': None}
    #   '1L': {'node': (1L, 1), 'L': None, 'R': None, 'P': (root, 3)}
    print(TEST)
    insert(TEST, 2, 'root')
    # root='root'
    # 'root': {'node': ('root', 3), 'L': (1L, 1), 'R': None, 'P': None}
    #   '1L': {'node': (1L, 1), 'L': None, 'R': (2R, 2), 'P': (root, 3)}
    #   '2R': {'node': (2R, 2), 'L': None, 'R': None, 'P': (1L, 1)}
    print(TEST)
    insert(TEST, 5, 'root')
    # root='root'
    # 'root': {'node': ('root', 3), 'L': (1L, 1), 'R': (5R, 5), 'P': None}
    #   '1L': {'node': (1L, 1), 'L': None, 'R': (2R, 2), 'P': (root, 3)}
    #   '2R': {'node': (2R, 2), 'L': None, 'R': None, 'P': (1L, 1)}
    #   '5R': {'node': (5R, 5), 'L': None, 'R': None, 'P': (root, 3)}
    print(TEST)
    insert(TEST, 4, 'root')
    # root='root'
    # 'root': {'node': ('root', 3), 'L': (1L, 1), 'R': (5R, 5), 'P': None}
    #   '1L': {'node': (1L, 1), 'L': None, 'R': (2R, 2), 'P': (root, 3)}
    #   '2R': {'node': (2R, 2), 'L': None, 'R': None, 'P': (1L, 1)}
    #   '5R': {'node': (5R, 5), 'L': (4L, 4), 'R': None, 'P': (root, 3)}
    #   '4L': {'node': (4L, 4), 'L': None, 'R': None, 'P': (5L, 5)}
    print(TEST)

    TEST = Bst()
    TEST.createnode('root', 3)
    TEST.createnode('1L', 1, parname='root')
    TEST.createnode('5R', 5, parname='root', right=True)
    TEST.createnode('0L', 0, parname='1L')
    TEST.createnode('2R', 2, parname='1L', right=True)
    TEST.createnode('4L', 4, parname='5R')
    TEST.createnode('6R', 6, parname='5R', right=True)
    # root=0L
    # {'0L': {'node': ('0L', 0), 'L': None, 'R': None, 'P': None}}
    print(TEST.subtree('0L', {}, 2))
    # root=2R
    # {'2R': {'node': ('2R', 2), 'L': None, 'R': None, 'P': None}}
    print(TEST.subtree('2R', {}, 2))
    # root=4L
    # {'4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': None}}
    print(TEST.subtree('4L', {}, 2))
    # root=6R
    # {'6R': {'node': ('6R', 6), 'L': None, 'R': None, 'P': None}}
    print(TEST.subtree('6R', {}, 2))
    # root=1L
    # {'0L': {'node': ('0L', 0), 'L': None, 'R': None, 'P': ('1L', 1)},
    #  '2R': {'node': ('2R', 2), 'L': None, 'R': None, 'P': ('1L', 1)},
    #  '1L': {'node': ('1L', 1), 'L': ('0L', 0), 'R': ('2R', 2), 'P': None}}
    print(TEST.subtree('1L', {}, 1))
    # root=5R
    # {'4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('5R', 5)},
    #  '6R': {'node': ('6R', 6), 'L': None, 'R': None, 'P': ('5R', 5)},
    #  '5R': {'node': ('5R', 5), 'L': ('4L', 4), 'R': ('6R', 6), 'P': None}}
    print(TEST.subtree('5R', {}, 1))
    # root=root
    # {'0L': {'node': ('0L', 0), 'L': None, 'R': None, 'P': ('1L', 1)},
    #  '2R': {'node': ('2R', 2), 'L': None, 'R': None, 'P': ('1L', 1)},
    #  '1L': {'node': ('1L', 1), 'L': ('0L', 0), 'R': ('2R', 2),
    #         'P': ('root', 3)},
    #  '4L': {'node': ('4L', 4), 'L': None, 'R': None, 'P': ('5R', 5)},
    #  '6R': {'node': ('6R', 6), 'L': None, 'R': None, 'P': ('5R', 5)},
    #  '5R': {'node': ('5R', 5), 'L': ('4L', 4), 'R': ('6R', 6),
    #         'P': ('root', 3)},
    # 'root': {'node': ('root', 3), 'L': ('1L', 1), 'R': ('5R', 5), 'P': None}}
    print(TEST.subtree('root', {}, 0))

    assert search(TEST, 3, 'root') == ['root']
    assert search(TEST, 1, 'root') == ['root', '1L']
    assert search(TEST, 5, 'root') == ['root', '5R']
    assert search(TEST, 0, 'root') == ['root', '1L', '0L']
    assert search(TEST, 2, 'root') == ['root', '1L', '2R']
    assert search(TEST, 4, 'root') == ['root', '5R', '4L']
    assert search(TEST, 6, 'root') == ['root', '5R', '6R']
    assert minmax(TEST, 'root') == '0L'
    assert minmax(TEST, 'root', maxm=True) == '6R'

    # testing pred and succ
    assert pred(TEST, 'root') == '2R'
    assert pred(TEST, '1L') == '0L'
    assert pred(TEST, '5R') == '4L'
    assert pred(TEST, '2R') == '1L'
    assert pred(TEST, '4L') == 'root'
    assert pred(TEST, '0L') is None
    assert pred(TEST, '6R') == '5R'
    assert succ(TEST, 'root') == '4L'
    assert succ(TEST, '1L') == '2R'
    assert succ(TEST, '5R') == '6R'
    assert succ(TEST, '2R') == 'root'
    assert succ(TEST, '4L') == '5R'
    assert succ(TEST, '0L') == '1L'
    assert succ(TEST, '6R') is None

    # should print 0L, 1L, 2R, root, 4L, 5R, 6R in order
    output_sorted(TEST, 'root')

    print("Tests pass.")
