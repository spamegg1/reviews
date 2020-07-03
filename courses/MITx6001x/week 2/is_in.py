# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 18:11:10 2018

@author:
"""


def is_in(char, a_str):
    '''
    char: a single character
    aStr: an alphabetized string

    returns: True if char is in aStr; False otherwise
    '''
    if a_str == '':
        return False
    if len(a_str) == 1:
        return char == a_str
    mid = len(a_str) // 2
    if a_str[mid] == char:
        return True
    if a_str[mid] < char:
        return is_in(char, a_str[mid + 1:])
    return is_in(char, a_str[:mid])


print(is_in('g', 'abdefghijklop'))
