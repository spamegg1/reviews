# -*- coding: utf-8 -*-
"""
Created on Sat Sep 21 23:34:54 2019.

@author: spamegg1
"""
import treelib


# Assume that binary search trees do not have repeated keys.
# This enables us to use the "identifier" field of nodes in the treelib
# module as keys, which makes coding and testing much easier,
# since the tree.show() method displays identifiers.
# Without this, testing "insert" and "delete" becomes impossible.

def haschild(tree, nodeid, right=False):
    """Take a binary search tree, a node ID and a Boolean as input.

    Boolean is False by default, corresponds to "left" child of node.
    Set 'right' to True if you want to check the right child of node.
    Return True if given node has a left (right) child, False otherwise.
    """
    children = tree.children(nodeid)
    if not children:
        return False

    chcount = len(children)
    if chcount >= 2:                    # node has both left and right children
        return True
    if chcount == 1:        # node has either left or right child, but not both
        childid = children[0].identifier
        if right:
            return childid > nodeid
        return childid < nodeid  # if left


def getchildid(tree, nodeid, right=False):
    """Take a binary search tree, a nodeid and a Boolean as input.

    Boolean is False by default, corresponds to "left" child of node.
    Set 'right' to True if you want to get the right child of node.
    Return nodeid of left (right) child of given node, or None.
    """
    hasleft = haschild(tree, nodeid)
    hasright = haschild(tree, nodeid, right=True)
    if hasleft and hasright:
        if right:
            return tree.children(nodeid)[1].identifier
        return tree.children(nodeid)[0].identifier
    if hasleft:
        if right:
            return None
        return tree.children(nodeid)[0].identifier
    if hasright:
        if right:
            return tree.children(nodeid)[0].identifier
        return None
    return None


def search(tree, key):
    """Take a binary search tree with distinct data and a key as input.

    Return a path (of keys/nodeIDs) to the key in the tree, or None.
    """
    rootid = tree.root                                    # id/key of root node
    if rootid == key:                                 # we are lucky, found key
        return [rootid]                        # return path in tree to the key

    children = tree.children(rootid)           # list of children nodes of root
    if not children:                             # if list of children is empty
        return None                      # root was a leaf, we did not find key

    if len(children) == 1:         # handle the case when there is only 1 child
        childid = children[0].identifier
        subtree = tree.subtree(childid)
        if childid < rootid:                     # child is on the left of root
            if key < rootid:         # key we are searching is also on the left
                return [tree.root] + search(subtree, key)
            return None    # key we are searching is on right, so can't find it
        if rootid < childid:                    # child is on the right of root
            if rootid < key:        # key we are searching is also on the right
                return [tree.root] + search(subtree, key)
            return None     # key we are searching is on left, so can't find it

    subtree = None                          # main case: 2 children at the root
    if key < rootid:                   # key is smaller than node, go down left
        leftchild = children[0].identifier              # recurse to left child
        subtree = tree.subtree(leftchild)                # left child's subtree
    elif key > rootid:                 # key is bigger than node, go down right
        rightchild = children[1].identifier            # recurse to right child
        subtree = tree.subtree(rightchild)              # right child's subtree

    result = search(subtree, key)        # search one of the children's subtree
    if not result:                                          # if result is None
        return None
    return [tree.root] + result                           # add root id to path


def minmax(tree, maxm=False):
    """Take binary search tree as input. Return node ID with min or max key.

    Take optional Boolean argument for min or max (False/min by default).
    Set maxm to True to find maximum value instead of minimum.
    """
    currentid = tree.root                                 # id/key of root node
    while True:
        children = tree.children(currentid)    # list of children nodes of root
        if not children:                               # current node is a leaf
            return currentid                            # return min/max id/key
        if maxm:                                              # looking for max
            if not haschild(tree, currentid, right=True):  # has no right child
                return currentid           # no right subtree, currentid is max
            currentid = getchildid(tree, currentid, right=True)   # go to right
        else:
            if not haschild(tree, currentid):     # currentid has no left child
                return currentid            # no left subtree, currentid is min
            currentid = getchildid(tree, currentid)  # go to left child for min


def pred(tree, nodeid):
    """Take binary search tree and a nodeid as input. Return a node ID or None.

    Returned node's key is given node's key's predecessor in tree.
    If given node has min key in tree, return None.
    """
    if haschild(tree, nodeid):                 # if given node has a left child
        leftchild = getchildid(tree, nodeid)                # get left child ID
        leftsubtree = tree.subtree(leftchild)         # get leftchild's subtree
        return minmax(leftsubtree, maxm=True)  # max of subtree is predeccessor

    # given node does not have a left child, traverse upward
    # given node becomes "child", we check its parent
    # if child is a right child of parent, return parent.
    childid = nodeid
    parentid = tree.parent(nodeid).identifier
    while True:
        if haschild(tree, parentid, right=True):     # parent has a right child
            rightchildid = getchildid(tree, parentid, right=True)
            if childid == rightchildid:     # if child is right child of parent
                return parentid
        if parentid == tree.root:  # we ran out of parents
            return None
        # otherwise keep traversing up
        childid = parentid
        parentid = tree.parent(parentid).identifier


def succ(tree, nodeid):
    """Take binary search tree and a nodeid as input. Return a node ID or None.

    Returned node's key is given node's key's successor in tree.
    If given node has max key in tree, return None.
    """
    if haschild(tree, nodeid, right=True):    # if given node has a right child
        rightchild = getchildid(tree, nodeid, right=True)  # get right child ID
        rightsubtree = tree.subtree(rightchild)      # get rightchild's subtree
        return minmax(rightsubtree)               # min of subtree is successor

    # given node does not have a right child, traverse upward
    # given node becomes "child", we check its parent
    # if child is a left child of parent, return parent.
    childid = nodeid
    parentid = tree.parent(nodeid).identifier
    while True:
        if haschild(tree, parentid):                  # parent has a left child
            leftchildid = getchildid(tree, parentid)
            if childid == leftchildid:       # if child is left child of parent
                return parentid
        if parentid == tree.root:  # we ran out of parents
            return None
        # otherwise keep traversing up
        childid = parentid
        parentid = tree.parent(parentid).identifier


def output_sorted(tree):
    """Take binary search tree as input. Print keys in increasing order."""
    curnodeid = tree.root
    if haschild(tree, curnodeid):
        leftchildid = getchildid(tree, curnodeid)
        leftsubtree = tree.subtree(leftchildid)
        output_sorted(leftsubtree)
    print(curnodeid)
    if haschild(tree, curnodeid, right=True):
        rightchildid = getchildid(tree, curnodeid, right=True)
        rightsubtree = tree.subtree(rightchildid)
        output_sorted(rightsubtree)


def insert(tree, key):
    """Take a binary search tree and a value as input.

    Add a new node to the tree with the given value as data,
    while maintaining binary search tree property.
    If a node with the same key already exists, the insert is ignored.
    """
    rootid = tree.root                                    # id/key of root node
    if rootid == key:                           # duplicate key being inserted!
        return                                      # ignore insert, do nothing

    children = tree.children(rootid)           # list of children nodes of root
    if not children:                             # if list of children is empty
        tree.create_node(tag=key, identifier=key, parent=rootid)
        return                                                           # done
    if len(children) == 1:             # handle case when there is only 1 child
        tree.create_node(tag=key, identifier=key, parent=rootid)
        if key < rootid:    # new node is left, so swap left and right children
            children[0], children[1] = children[1], children[0]
        return                                                           # done

    subtree = None                          # main case: 2 children at the root
    if key < rootid:                   # key is smaller than node, go down left
        leftchild = children[0].identifier              # recurse to left child
        subtree = tree.subtree(leftchild)                # left child's subtree
    elif key > rootid:                 # key is bigger than node, go down right
        rightchild = children[1].identifier            # recurse to right child
        subtree = tree.subtree(rightchild)              # right child's subtree
    return insert(subtree, key)     # insert into one of the children's subtree


def delete(tree, key):
    """Take binary search tree and a key as input.

    Delete node with key from tree if it exists.
    """
    nodeids = search(tree, key)
    if not nodeids:
        return                # given key is not in the tree, nothing to delete
    nodeid = nodeids[-1]         # last node on path is the node with given key
    node = tree[nodeid]                                       # get node object
    children = tree.children(nodeid)                     # get list of children
    if not children:                    # node is a leaf, can be safely deleted
        tree.remove_node(nodeid)
        return

    if len(children) == 1:
        childid = children[0].identifier
        parent = tree.parent(nodeid)
        parentid = parent.identifier
        subtree = tree.subtree(childid)                   # get child's subtree
        tree.paste(parentid, subtree, deep=True)  # paste sbtree to parent node
        tree.remove_node(nodeid)       # now delete node along with its subtree
        return

    if len(children) == 2:
        predid = pred(tree, nodeid)                    # get node's predecessor
        prednode = tree[predid]                   # get predecessor node object
        predtag = prednode.tag
        predchildren = tree.children(predid)       # get predecessor's children
        # predecessor is in node's left subtree. It has max key in that subtree
        # predecessor cannot have a right child,
        # because pred calculates by following down right children.
        # predecessor might or might not have a left child.
        # this reduces us to the previous case.
        # Swap node's key with its predecessor, then repeat above algorithm.
        if not predchildren:
            tree.remove_node(predid)
            node.identifier = predid
            node.tag = predtag
            return

        if len(predchildren) == 1:
            predchildid = predchildren[0].identifier
            parent = tree.parent(predid)
            parentid = parent.identifier
            subtree = tree.subtree(predchildid)
            tree.paste(parentid, subtree, deep=True)
            tree.remove_node(predid)
            node.identifier = predid
            node.tag = predtag
            return


# TESTING
if __name__ == "__main__":
    # smaller test tree
    # 3 (root)
    # |__1 (L)
    # |  |__2 (R)
    # |__5 (R)
    #    |__4 (L)
    TEST = treelib.Tree()
    TEST.create_node(identifier=3)  # root, level 0
    TEST.create_node(identifier=1, parent=3)  # level 1, left of root
    TEST.create_node(identifier=5, parent=3)  # level 1, right of root
    TEST.create_node(identifier=2, parent=1)  # level 2, right of 1
    TEST.create_node(identifier=4, parent=5)  # level 2, left of 5

    # testing haschild
    assert haschild(TEST, 3)            # root has both left and right children
    assert haschild(TEST, 3, right=True)
    assert haschild(TEST, 1) is False                # 1 has only right child 2
    assert haschild(TEST, 1, right=True)
    assert haschild(TEST, 5)                          # 5 has only left child 4
    assert haschild(TEST, 5, right=True) is False
    assert haschild(TEST, 2) is False                       # 2 has no children
    assert haschild(TEST, 2, right=True) is False
    assert haschild(TEST, 4) is False                       # 4 has no children
    assert haschild(TEST, 4, right=True) is False

    # testing getchildid
    assert getchildid(TEST, 3) == 1
    assert getchildid(TEST, 3, right=True) == 5
    assert getchildid(TEST, 1) is None
    assert getchildid(TEST, 1, right=True) == 2
    assert getchildid(TEST, 5) == 4
    assert getchildid(TEST, 5, right=True) is None
    assert getchildid(TEST, 2) is None
    assert getchildid(TEST, 2, right=True) is None
    assert getchildid(TEST, 4) is None
    assert getchildid(TEST, 4, right=True) is None

    # testing pred and succ
    assert pred(TEST, 3) == 2
    assert pred(TEST, 1) is None
    assert pred(TEST, 5) == 4
    assert pred(TEST, 2) == 1
    assert pred(TEST, 4) == 3
    assert succ(TEST, 3) == 4
    assert succ(TEST, 1) == 2
    assert succ(TEST, 5) is None
    assert succ(TEST, 2) == 3
    assert succ(TEST, 4) == 5

    # testing search
    assert search(TEST, 1) == [3, 1]
    assert search(TEST, 2) == [3, 1, 2]
    assert search(TEST, 3) == [3]
    assert search(TEST, 4) == [3, 5, 4]
    assert search(TEST, 5) == [3, 5]

    # testing delete (no good way to test with assert, test visually instead)
    # this is the hardest case (deleted node has 2 children), deleting the root
    # this should draw:
    # 3
    # |__1
    # |  |__2
    # |__5
    #    |__4
    # TEST.show()
    # delete(TEST, 3)  # delete root
    # this should draw:
    # 2
    # |__1
    # |__5
    #    |__4
    # TEST.show()
    # TEST.create_node(tag=3, identifier=3, parent=5)
    # TEST.show()

    # main tests
    # 3 (root)
    # |__1 (L)
    # |  |__0 (L)
    # |  |__2 (R)
    # |__5 (R)
    #    |__4 (L)
    #    |__6 (R)
    TEST = treelib.Tree()
    TEST.create_node(identifier=3)  # root, level 0
    TEST.create_node(identifier=1, parent=3)  # level 1
    TEST.create_node(identifier=5, parent=3)  # level 1
    TEST.create_node(identifier=0, parent=1)  # level 2
    TEST.create_node(identifier=2, parent=1)  # level 2
    TEST.create_node(identifier=4, parent=5)  # level 2
    TEST.create_node(identifier=6, parent=5)  # level 2

    # testing search
    ANSWERS = [[3, 1, 0], [3, 1], [3, 1, 2], [3], [3, 5, 4], [3, 5], [3, 5, 6],
               None, None]
    for i in range(9):
        assert search(TEST, i) == ANSWERS[i]

    # testing min/max
    assert minmax(TEST) == 0
    assert minmax(TEST, maxm=True) == 6

    # testing pred and succ
    assert pred(TEST, 0) is None
    assert pred(TEST, 1) == 0
    assert pred(TEST, 2) == 1
    assert pred(TEST, 3) == 2
    assert pred(TEST, 4) == 3
    assert pred(TEST, 5) == 4
    assert pred(TEST, 6) == 5
    assert succ(TEST, 0) == 1
    assert succ(TEST, 1) == 2
    assert succ(TEST, 2) == 3
    assert succ(TEST, 3) == 4
    assert succ(TEST, 4) == 5
    assert succ(TEST, 5) == 6
    assert succ(TEST, 6) is None

    output_sorted(TEST)         # should print out 0, 1, 2, 3, 4, 5, 6 in order

    # TEST.show()
    # delete(TEST, 5)
    # TEST.show()

    print("tests pass")
