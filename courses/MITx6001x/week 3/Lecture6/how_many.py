# -*- coding: utf-8 -*-
"""
Created on Wed Sep 12 23:11:01 2018

@author:
"""


def how_many(a_dict):
    '''
    a_dict: A dictionary, where all the values are lists.

    returns: int, how many values are in the dictionary.
    '''
    result = 0
    for value in a_dict.values():
        result += len(value)
    return result


def biggest(a_dict):
    '''
    a_dict: A dictionary, where all the values are lists.

    returns: The key with the largest number of values associated with it
    '''
    if not a_dict.values():
        return None
    largest = len(max(a_dict.values(), key=len))
    for key in a_dict:
        if len(a_dict[key]) == largest:
            return key
