# -*- coding: utf-8 -*-
"""
Created on Fri Jun 10 13:03:44 2016

@author: ericgrimson
"""

def find_elem_recur(e, L):
    if L == []:
        return False
    elif len(L) == 1:
        return L[0] == e
    else:
        half = len(L)//2
        if L[half] > e:
            return find_elem_recur(e, L[:half])
        else:
            return find_elem_recur(e, L[half:])


print(find_elem_recur(1, [1, 2, 3, 5, 7, 8, 9, 15]))