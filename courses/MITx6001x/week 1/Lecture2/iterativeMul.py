# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 11:23:28 2016

@author: ericgrimson
"""

x = 5
ans = 0
itersLeft = x
while (itersLeft != 0):
    ans = ans + x
    itersLeft = itersLeft - 1
print(str(x) + '*' + str(x) + ' = ' + str(ans))
