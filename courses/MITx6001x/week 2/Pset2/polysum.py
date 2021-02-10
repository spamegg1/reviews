# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 18:47:02 2018

@author: spamegg
"""

import math


def polysum(n, s):
    """
    Returns the sum of the area and the square of the perimeter of
    a regular polygon.
    n is a positive integer, denotes the number of sides of polygon
    s is a positive float, denotes the length of one side of polygon
    """
    # Area of a regular polygon is given by the below formula:
    area = 0.25 * n * s**2 / math.tan(math.pi / n)

    # Perimeter of a regular polygon is: n * s
    perim_sq = (n * s)**2

    # Return the sum of area and perim_sq, rounded to 4 decimal places
    return round(area + perim_sq, 4)
