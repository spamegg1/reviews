# -*- coding: utf-8 -*-
"""
Created on Tue Jun 21 22:49:10 2016

@author: ericgrimson
"""


def selection_sort(L):
    suffixStart = 0
    while suffixStart != len(L):
        print(L)
        for i in range(suffixStart, len(L)):
            if L[i] < L[suffixStart]:
                L[suffixStart], L[i] = L[i], L[suffixStart]
        suffixStart += 1


test = [1, 5, 3, 8, 4, 9, 6, 2]
selection_sort(test)
