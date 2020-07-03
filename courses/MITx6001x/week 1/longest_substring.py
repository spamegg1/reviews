# -*- coding: utf-8 -*-
"""
Created on Sat Sep  1 12:35:13 2018

@author:
"""
S = 'aaaaaaaaaaaabaaaaaaaaaazaaaaaaaaaafaaaaaaaaayaaaaaaaa'


def longest_substring(string):
    """Returns longest substring in string"""
    if not string:
        print("Longest substring in alphabetical order is: " + string)
    else:
        start = 0
        end = 1
        longest = 1
        longest_string = string[0]
        current = string[start:end]
        while end < len(string):
            if current[-1] <= string[end]:
                current = current + string[end]
                if len(current) > longest:
                    longest = len(current)
                    longest_string = current
                end += 1
            else:
                start = end
                end = start + 1
                current = string[start:end]
        print("Longest substring in alphabetical order is: " + longest_string)


longest_substring(S)
