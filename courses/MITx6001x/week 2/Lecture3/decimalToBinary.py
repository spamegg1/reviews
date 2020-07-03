# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 12:09:59 2016

@author: ericgrimson
"""

num = 19

if num < 0:
    isNeg = True
    num = abs(num)
else:
    isNeg = False
result = ''
if num == 0:
    result = '0'
while num > 0:
    result = str(num%2) + result
    num = num//2
if isNeg:
    result = '-' + result
