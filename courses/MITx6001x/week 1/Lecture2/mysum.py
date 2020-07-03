# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 11:21:27 2016

@author: ericgrimson
"""

mysum = 0
for i in range(5, 11, 2):
    mysum += i
    if mysum == 5:
        break 
print(mysum)
