# -*- coding: utf-8 -*-
"""
Created on Sat Sep  1 12:35:13 2018

@author: spamegg

Assume s is a string of lower case characters.

Write a program that prints the longest substring of s in which the letters
occur in alphabetical order. For example, if s = 'azcbobobegghakl', then your
program should print

Longest substring in alphabetical order is: beggh

In the case of ties, print the first substring. For example, if s = 'abcbcd',
then your program should print

Longest substring in alphabetical order is: abc

Note: This problem may be challenging. We encourage you to work smart.
If you've spent more than a few hours on this problem,
we suggest that you move on to a different part of the course.
If you have time, come back to this problem after you've had a break and cleared
your head.
"""


def longest_substring(s):
    """Returns the longest substring in string"""
    # start = 0
    # end = 1
    # longest = 1
    # longest_string = s[0]
    # current = s[start:end]
    # while end < len(s):
    #     if current[-1] <= s[end]:
    #         current = current + s[end]
    #         if len(current) > longest:
    #             longest = len(current)
    #             longest_string = current
    #         end += 1
    #     else:
    #         start = end
    #         end = start + 1
    #         current = s[start:end]
    # print("Longest substring in alphabetical order is: " + longest_string)
    substring = s[0]
    longest = s[0]

    for i in range(1, len(s)):
        if s[i] >= s[i - 1]:
            substring += s[i]
            if len(substring) > len(longest):
                longest = substring
        else:
            substring = s[i]

    print("Longest substring in alphabetical order is:", longest)
    return longest


# TESTING
if __name__ == "__main__":
    WORDS = [
        'dbxuofuzgon', 'fsagwlltxifaqg', 'bogvxlzntxzurfdxqiz',
        'twameqnktryeugaufxic', 'kapyrljsstow', 'kdfopywicsstu',
        'upqnbtxfywnuvisrpg', 'abcdefghijklmnopqrstuvwxyz', 'lsbjatmsvwwrdxw',
        'wjhprvhwneixskmgpyj', 'hjfnqvttoqrp', 'zyxwvutsrqponmlkjihgfedcba',
        'rvdaorualoonojnzt', 'grqdjfzworbuiededct', 'zqfreprxpbdfxkcjaj',
        'jccuqkkb', 'iodrczqeih', 'iaysxzeidqgwdc', 'aiikeudqfyikcooed',
        'ligwdpsszgbzjw', 'azcbobobegghakl', 'abcbcd', 'abczhefghijtatuvwxy',
        'abczhefghijtatuvwxyz',
        'aaaaaaaaaaaabaaaaaaaaaazaaaaaaaaaafaaaaaaaaayaaaaaaaa'
    ]
    ANSWERS = [
        'fuz', 'lltx', 'ntxz', 'tw', 'jsst', 'dfopy', 'btx',
        'abcdefghijklmnopqrstuvwxyz', 'msvww', 'hprv', 'fnqv', 'z', 'aoru', 'gr',
        'eprx', 'ccu', 'io', 'sxz', 'aiik', 'dpssz', 'beggh', 'abc', 'efghijt',
        'atuvwxyz', 'aaaaaaaaaaaab'
    ]

    for (word, answer) in zip(WORDS, ANSWERS):
        assert(longest_substring(word) == answer)
    print("All tests passed!")
