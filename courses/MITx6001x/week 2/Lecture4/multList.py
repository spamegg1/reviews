# -*- coding: utf-8 -*-
"""
Created on Fri Jun 10 13:02:37 2016

@author: ericgrimson
"""


def mult_list_recur(L):
    if len(L) == 1:
        return L[0]
    else:
        return L[0]*mult_list_recur(L[1:])


print(mult_list_recur([1, 3, 5, 7, 9]))
