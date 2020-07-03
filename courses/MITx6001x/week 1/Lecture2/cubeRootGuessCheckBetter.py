# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 11:41:14 2016

@author: ericgrimson
"""

cube = -28
for guess in range(abs(cube)+1):
    if guess**3 >= abs(cube):
        break
if guess**3 != abs(cube):
    print(cube, 'is not a perfect cube')
else:
    if cube < 0:
        guess = -guess
    print('Cube root of ' + str(cube) + ' is ' + str(guess))
