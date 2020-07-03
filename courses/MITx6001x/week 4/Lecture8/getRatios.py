# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 21:08:39 2016

@author: ericgrimson
"""


def get_ratios(L1, L2):
    """ Assumes: L1 and L2 are lists of equal length of numbers
        Returns: a list containing L1[i]/L2[i] """
    ratios = []
    for index in range(len(L1)):
        try:
            ratios.append(L1[index]/float(L2[index]))
        except ZeroDivisionError:
            ratios.append(float('NaN'))  # NaN = Not a Number
        except:
            raise ValueError('get_ratios called with bad arg')
    return ratios
