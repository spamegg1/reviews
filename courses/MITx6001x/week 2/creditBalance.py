# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 22:17:48 2018

@author:
"""


"""
balance is: float
annualInterestRate is: float
monthlyPaymentRate is: float
returns: float, rounded to 2 decimal places

Returns the credit card balance after one year if a person only pays the
minimum monthly payment required by the credit card company each month."""

# this variable will iterate through 12 months of the year
month = 0

while month < 12:
    monthlyPayment = balance * monthlyPaymentRate
    unpaidBalance = balance - monthlyPayment

    # The bank charges interest each month on unpaid balance
    balance = unpaidBalance + (annualInterestRate / 12.0) * unpaidBalance
    month += 1

# Don't forget to round answer to two decimal places
balance = round(balance, 2)

print("Remaining balance: " + str(balance))
