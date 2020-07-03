# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 12:33:30 2016

@author: ericgrimson
"""


def is_palindrome(string):
    '''Returns true if given string is a palindrome'''

    def to_chars(string):
        string = string.lower()
        ans = ''
        for char in string:
            if char in 'abcdefghijklmnopqrstuvwxyz':
                ans = ans + char
        return ans

    def is_pal(string):
        if len(string) <= 1:
            return True
        return string[0] == string[-1] and is_pal(string[1:-1])

    return is_pal(to_chars(string))


print("")
print('Is eve a palindrome?')
print(is_palindrome('eve'))

print('')
print('Is able was I ere I saw Elba a palindrome?')
print(is_palindrome('Able was I, ere I saw Elba'))
