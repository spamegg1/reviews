# -*- coding: utf-8 -*-
"""
Created on Sat Oct  5 21:29:08 2019.

@author: spamegg1
"""
import bst
import heapdict as h
from huffman import tree_to_code


def huffmanheap(alphabet):
    """Take an alphabet (dictionary of symbol: frequency pairs) as input.

    Return an optimal binary encoding of the alphabet.
    Input alphabet looks something like this:
    {'A': 0.6, 'B': 0.25, 'C': 0.1, 'D': 0.05}
    Return value looks something like this:
    {'A': '00', 'B': '01', 'C': '10', 'D': '110', 'E': '111'}
    """
    counter = 0
    heap = h.HeapDict()
    for letter, frequency in alphabet.items():
        heap[letter] = frequency

    while heap:
        tree1 = heap.popitem()
        if not heap:         # we popped last element, so nothing left to merge
            return tree_to_code(tree1[0])
        tree2 = heap.popitem()

        label = str(counter)
        counter += 1
        sumfreq = tree1[1] + tree2[1]

        # Case 1: both tree1 and tree2 are single nodes. Create new tree.
        if type(tree1[0]) == str and type(tree2[0]) == str:
            tree = bst.Bst()
            tree.createnode(label, sumfreq)
            tree.createnode(tree1[0], tree1[1], parname=label)
            tree.createnode(tree2[0], tree2[1], parname=label, right=True)
            heap[tree] = sumfreq

        # Case 2: tree1 is single node, tree2 is not. Add 2 nodes to tree2.
        elif type(tree1[0]) == str and type(tree2[0]) != str:
            oldroot = tree2[0].root
            tree2[0].createnode(label, sumfreq)  # new root
            tree2[0].createnode(tree1[0], tree1[1], parname=label)
            tree2[0].tree[oldroot.name]['P'] = tree2[0].getnode(label)
            tree2[0].tree[label]['R'] = oldroot
            heap[tree2[0]] = sumfreq

        # Case 3: tree2 is single node, tree1 is not. Add 2 nodes to tree1.
        elif type(tree1[0]) != str and type(tree2[0]) == str:
            oldroot = tree1[0].root
            tree1[0].createnode(label, sumfreq)  # new root
            tree1[0].createnode(tree2[0], tree2[1], parname=label)
            tree1[0].tree[oldroot.name]['P'] = tree1[0].getnode(label)
            tree1[0].tree[label]['R'] = oldroot
            heap[tree1[0]] = sumfreq

        # Case 4: both tree1 and tree2 multinode. Merge two trees with new root
        elif type(tree1[0]) != str and type(tree2[0]) != str:
            oldroot1, oldroot2 = tree1[0].root, tree2[0].root
            tree = bst.Bst()
            tree.tree = {**tree1[0].tree, **tree2[0].tree}
            tree.createnode(label, sumfreq)  # new root
            tree.tree[oldroot1.name]['P'] = tree.getnode(label)
            tree.tree[label]['L'] = oldroot1
            tree.tree[oldroot2.name]['P'] = tree.getnode(label)
            tree.tree[label]['R'] = oldroot2
            heap[tree] = sumfreq

    return alphabet


# TESTING
if __name__ == "__main__":
    # too many possible optimal prefix-free codes, so just print and check
    ALPHABET = {'A': 0.6, 'B': 0.25, 'C': 0.1, 'D': 0.05}
    # should be something like: {'A': '0', 'B': '10', 'D': '110', 'C': '111'}
    print(huffmanheap(ALPHABET))
    ALPHABET = {'A': 0.32, 'B': 0.25, 'C': 0.20, 'D': 0.18, 'E': 0.05}
    # {'C': '00', 'B': '10', 'A': '11', 'E': '010', 'D': '011'}
    print(huffmanheap(ALPHABET))
    ALPHABET = {'A': 0.16, 'B': 0.08, 'C': 0.35, 'D': 0.07, 'E': 0.34}
    # {'C': '0', 'E': '10', 'A': '110', 'D': '1110', 'B': '1111'}
    print(huffmanheap(ALPHABET))
    ALPHABET = {'A': 3, 'B': 2, 'C': 6, 'D': 8, 'E': 2, 'F': 6}
    # {'A': '000', 'B': '0010', 'C': '10', 'D': '01', 'E': 0011, 'F': 11}
    print(huffmanheap(ALPHABET))

    print("Tests pass.")
