# -*- coding: utf-8 -*-
"""
Created on Sat Oct  5 21:29:08 2019.

@author: spamegg1
"""
from collections import deque
from operator import itemgetter
import bst


def tree_to_code(tree):
    """Take binary search tree (with symbols on leaves) as input.

    Return encoding of symbols based on the tree.
    """
    # Traverse tree with a BFS, building up labels along the way.
    # when we hit a leaf, we attach the label built up so far, add it to result
    result = {}
    frontier = deque([(tree.root, '')])

    while frontier:
        new = frontier.popleft()
        node, label = new[0], new[1]

        children = tree.children(node.name)
        left, right = children['L'], children['R']
        leftlabel, rightlabel = label + '0', label + '1'

        if tree.childcount(left.name) == 0:              # left child is a leaf
            result[left.name] = leftlabel
        else:
            frontier.append((left, leftlabel))
        if tree.childcount(right.name) == 0:            # right child is a leaf
            result[right.name] = rightlabel
        else:
            frontier.append((right, rightlabel))

    return result


def huffman(alphabet):
    """Take an alphabet (dictionary of symbol: frequency pairs) as input.

    Return an optimal binary encoding of the alphabet.
    Input alphabet looks something like this:
    {'A': 0.6, 'B': 0.25, 'C': 0.1, 'D': 0.05}
    Return value looks something like this:
    {'A': '00', 'B': '01', 'C': '10', 'D': '110', 'E': '111'}
    Uses two deques, which can beat O(nlogn).
    Only uses one sorting plus O(n) additional work.
    """
    # freq, and consequently first, look like this:
    # [('D', 0.05), ('C', 0.1), ('B', 0.25), ('A', 0.6)]
    freq = sorted(alphabet.items(), key=itemgetter(1))  # sort by incrsing freq
    first = deque(freq)                                     # single node trees
    second = deque()                                         # multi node trees
    counter = 0
    tree1first = False    # True if tree1 came from first, False if from second
    tree2first = False    # True if tree2 came from first, False if from second

    while first or second:
        # Maintain following invariants:
        # (1) the elements of first correspond to single-node trees in current
        # forest F, stored in increasing order of frequency.
        # (2) the elements of second correspond to the multi-node trees of F,
        # stored in increasing order of sum of symbol frequencies.

        # Case 1: there are no multi-node trees in second,
        #         so we must pick two single-node trees from first for merging.
        # If alphabet consists of exactly one symbol, return it with code '0'.
        # Otherwise assume first has at least 2 elements in this case.
        if not second:
            tree1 = first.popleft()
            tree1first = True
            if not first:
                return {tree1[0]: '0'}
            tree2 = first.popleft()
            tree2first = True
        # Case 2: there are no single-node trees in first,
        #         so we must pick two multi-node trees from second for merging.
        elif not first:
            tree1 = second.popleft()
            tree1first = False
            if second:
                tree2 = second.popleft()
                tree2first = False
            else:            # we popped last element, so nothing left to merge
                return tree_to_code(tree1[0])
        # Case 3: both first and second are nonempty.
        # so we must choose 2 smallest freqs from first and second for merging.
        # Depending on whether first and/or second have 1 or more elements,
        # get their first 1 or 2 elements, then compare their frequencies.
        # Subcase 3.1: first has exactly one element
        elif len(first) == 1:
            # Subcase 3.1.1: second has exactly one element
            if len(second) == 1:
                tree1 = first.popleft()
                tree1first = True
                tree2 = second.popleft()
                tree2first = False
            # Subcase 3.1.2: second has two or more elements, comparison needed
            else:
                if first[0][1] < second[1][1]:
                    tree1 = first.popleft()
                    tree1first = True
                    tree2 = second.popleft()
                    tree2first = False
                else:
                    tree1 = second.popleft()
                    tree1first = False
                    tree2 = second.popleft()
                    tree2first = False
        # Subcase 3.2: first has two or more elements
        else:
            # Subcase 3.2.1: second has exactly one element, comparison needed
            if len(second) == 1:
                if second[0][1] < first[1][1]:
                    tree1 = first.popleft()
                    tree1first = True
                    tree2 = second.popleft()
                    tree2first = False
                else:
                    tree1 = first.popleft()
                    tree1first = True
                    tree2 = first.popleft()
                    tree2first = True
            # Subcase 3.2.2: second has two or more elements, comparison needed
            else:
                if first[0][1] < second[0][1]:
                    if first[1][1] < second[0][1]:
                        tree1 = first.popleft()
                        tree1first = True
                        tree2 = first.popleft()
                        tree2first = True
                    else:
                        tree1 = first.popleft()
                        tree1first = True
                        tree2 = second.popleft()
                        tree2first = False
                elif first[0][1] < second[1][1]:
                    tree1 = first.popleft()
                    tree1first = True
                    tree2 = second.popleft()
                    tree2first = False
                else:
                    tree1 = second.popleft()
                    tree1first = False
                    tree2 = second.popleft()
                    tree2first = False

        # after selection, we merge tree1 and tree2, add it to second
        label = str(counter)
        counter += 1
        sumfreq = tree1[1] + tree2[1]

        # Case 1: both tree1 and tree2 are single nodes. Create new tree.
        if tree1first and tree2first:
            tree = bst.Bst()
            tree.createnode(label, sumfreq)
            tree.createnode(tree1[0], tree1[1], parname=label)
            tree.createnode(tree2[0], tree2[1], parname=label, right=True)
            second.append((tree, sumfreq))

        # Case 2: tree1 in first, tree2 in second. Add 2 nodes to tree2.
        elif tree1first and not tree2first:
            oldroot = tree2[0].root
            tree2[0].createnode(label, sumfreq)                      # new root
            tree2[0].createnode(tree1[0], tree1[1], parname=label)
            tree2[0].tree[oldroot.name]['P'] = tree2[0].getnode(label)
            tree2[0].tree[label]['R'] = oldroot
            second.append((tree2[0], sumfreq))
        # Case 3: both tree1 and tree2 in second. Merge two trees with new root
        elif not tree1first and not tree2first:
            oldroot1, oldroot2 = tree1[0].root, tree2[0].root
            tree = bst.Bst()
            tree.tree = {**tree1[0].tree, **tree2[0].tree}
            tree.createnode(label, sumfreq)                          # new root
            tree.tree[oldroot1.name]['P'] = tree.getnode(label)
            tree.tree[label]['L'] = oldroot1
            tree.tree[oldroot2.name]['P'] = tree.getnode(label)
            tree.tree[label]['R'] = oldroot2
            second.append((tree, sumfreq))


# TESTING
if __name__ == "__main__":
    # testing tree_to_code
    TREE = bst.Bst()
    TREE.createnode('root', '')
    TREE.createnode('0', '0', parname='root')
    TREE.createnode('1', '1', parname='root', right=True)
    TREE.createnode('A', '00', parname='0')
    TREE.createnode('B', '01', parname='0', right=True)
    TREE.createnode('C', '10', parname='1')
    TREE.createnode('11', '11', parname='1', right=True)
    TREE.createnode('D', '110', parname='11')
    TREE.createnode('E', '111', parname='11', right=True)
    CODE = {'A': '00', 'B': '01', 'C': '10', 'D': '110', 'E': '111'}
    assert tree_to_code(TREE) == CODE

    # too many possible optimal prefix-free codes, so just print and check
    ALPHABET = {'A': 0.6, 'B': 0.25, 'C': 0.1, 'D': 0.05}
    # should be something like: {'A': '0', 'B': '10', 'D': '110', 'C': '111'}
    print(huffman(ALPHABET))
    ALPHABET = {'A': 0.32, 'B': 0.25, 'C': 0.20, 'D': 0.18, 'E': 0.05}
    # {'C': '00', 'B': '10', 'A': '11', 'E': '010', 'D': '011'}
    print(huffman(ALPHABET))
    ALPHABET = {'A': 0.16, 'B': 0.08, 'C': 0.35, 'D': 0.07, 'E': 0.34}
    # {'C': '0', 'E': '10', 'A': '110', 'D': '1110', 'B': '1111'}
    print(huffman(ALPHABET))
    ALPHABET = {'A': 3, 'B': 2, 'C': 6, 'D': 8, 'E': 2, 'F': 6}
    # {'A': '000', 'B': '0010', 'C': '10', 'D': '01', 'E': 0011, 'F': 11}
    print(huffman(ALPHABET))

    ALPHABET = {'A': 0.28, 'B': 0.27, 'C': 0.2, 'D': 0.15, 'E': 0.1}
    RESULT = huffman(ALPHABET)
    AVERAGE = 0
    for letter in RESULT:
        AVERAGE += len(RESULT[letter]) * ALPHABET[letter]
    print(AVERAGE)

    print("Tests pass.")
