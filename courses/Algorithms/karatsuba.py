# -*- coding: utf-8 -*-
"""
Created on Wed Sep  4 09:55:31 2019.

@author: spamegg1
"""

# KARATSUBA ALGORITHM FOR INTEGER MULTIPLICATION
# Given two integers x and y of n digits. Want to quickly calculate x*y.
# As a challenge I am only allowed to use Python's multiplication operator
# on single digit numbers. Assume n is a power of 2.
# We can divide x and y's digits in two halves:
# x = a*10^(n/2) + b, and y = c*10^(n/2) + d for some a, b, c, d.
# Then x*y = ac*10^n + (ad + bc)*10^(n/2) + bd
# We compute ac, bd, (a + b)(c + d) and (ad + bc) by subtracting the first two
# from the third: (a + b)(c + d) - ac - bd = (ad + bc)
# Finally we put them together to compute x*y.


def zero_pad(number_string, zeros, left=True):
    """Return the string with zeros added to the left or right."""
    padding = ''.join(['0' for i in range(zeros)])
    if left:
        number_string = padding + number_string
    else:
        number_string += padding
    return number_string


def karatsuba(first, second):
    """Take two n-digit positive integers as input.

    Return their product.
    """
    # Convert to strings to access digits
    fst_str = str(first)
    sec_str = str(second)
    fst_len = len(fst_str)
    sec_len = len(sec_str)

    # Base case: both numbers are single digit
    if fst_len == 1 and sec_len == 1:
        return first * second

    # At least one of them has more than 1 digits but they have unequal digits
    # We must pad the shorter number with zeros on the left
    if fst_len < sec_len:
        fst_str = zero_pad(fst_str, sec_len - fst_len)
    if sec_len < fst_len:
        sec_str = zero_pad(sec_str, fst_len - sec_len)

    # after padding, they have the same number of digits
    digits = len(fst_str)
    mid = digits//2  # half of digits
    if digits % 2 == 1:
        mid += 1  # add 1 to midpoint for odd digits

    fst_a = int(fst_str[:mid])  # a: first half of x
    fst_b = int(fst_str[mid:])  # b: last half of x
    sec_c = int(sec_str[:mid])  # c: first half of y
    sec_d = int(sec_str[mid:])  # d: last half of y

    # Compute p = a + b and q = c + d using grade school addition
    a_plus_b = fst_a + fst_b  # this is p
    c_plus_d = sec_c + sec_d  # this is q

    # recursively compute ac, bd, pq
    a_times_c = karatsuba(fst_a, sec_c)
    b_times_d = karatsuba(fst_b, sec_d)
    p_times_q = karatsuba(a_plus_b, c_plus_d)

    # compute ad + bc = pq - ac - bd using grade school subtraction
    ad_plus_bc = p_times_q - a_times_c - b_times_d

    # add (digits - mid) trailing zeros to ad_plus_bc
    padding = digits - mid
    ad_plus_bc = int(zero_pad(str(ad_plus_bc), padding, False))

    # add 2*(digits - mid) trailing zeros to a_times_c
    a_times_c = int(zero_pad(str(a_times_c), padding + padding, False))

    return a_times_c + ad_plus_bc + b_times_d


if __name__ == "__main__":
    FIRST = 3141592653589793238462643383279502884197169399375105820974944592
    SECOND = 2718281828459045235360287471352662497757247093699959574966967627
    print(karatsuba(FIRST, SECOND))
