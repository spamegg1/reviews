# -*- coding: utf-8 -*-
"""
Created on Fri Sep  7 12:46:42 2018

@author:
"""


def creditBalance(balance, annualInterestRate, monthlyPayment):
    """
    balance is: float
    annualInterestRate is: float
    monthlyPayment is: float
    returns: float, rounded to 2 decimal places

    Returns the credit card balance after one year if a person only pays the
    minimum monthly payment required by the credit card company each month."""

    # this variable will iterate through 12 months of the year
    month = 0

    while month < 12:
        unpaid_balance = balance - monthlyPayment

        # The bank charges interest each month on unpaid balance
        balance = unpaid_balance + (annualInterestRate/12.0) * unpaid_balance
        month += 1

    # Don't forget to round answer to two decimal places
    balance = round(balance, 2)

    return balance


# Bisection search to find the minimum monthly payment that pays off balance
balance = 999999
annualInterestRate = 0.18
left = balance / 12
right = (balance * (1 + annualInterestRate / 12.0)**12) / 12.0
result = (left + right) / 2
epsilon = 0.01

while abs(creditBalance(balance, annualInterestRate, result)) >= epsilon:
    attempt = creditBalance(balance, annualInterestRate, result)
    if attempt > 0:
        left = result
    else:
        right = result
    result = (left + right) / 2

print("Lowest payment: " + str(round(result, 2)))
