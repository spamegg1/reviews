# -*- coding: utf-8 -*-
"""
Created on Wed May 18 20:32:38 2016

@author: ericgrimson
"""


def selSort(L):
    for i in range(len(L) - 1):
        print(L)
        minIndx = i
        minVal = L[i]
        j = i + 1
        while j < len(L):
            if minVal > L[j]:
                minIndx = j
                minVal = L[j]
            j += 1
        temp = L[i]
        L[i] = L[minIndx]
        L[minIndx] = temp


test = [1, 5, 3, 8, 4, 9, 6, 2]
selSort(test)
