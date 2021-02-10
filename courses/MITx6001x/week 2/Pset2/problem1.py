# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 22:17:48 2018

@author: spamegg
"""
### Test Case 1:
##balance = 42
##annualInterestRate = 0.2
##monthlyPaymentRate = 0.04
### Result Your Code Should Generate Below:
###Remaining balance: 31.38
###Test Case 2:
##balance = 484
##annualInterestRate = 0.2
##monthlyPaymentRate = 0.04
##
###Result Your Code Should Generate Below:
###Remaining balance: 361.61

"""
balance is: float
annualInterestRate is: float
monthlyPaymentRate is: float
returns: float, rounded to 2 decimal places

Returns the credit card balance after one year if a person only pays the
minimum monthly payment required by the credit card company each month."""

monthlyInterestRate = annualInterestRate / 12.0

# this variable will iterate through 12 months of the year
month = 1

while month <= 12:
    minimumMonthlyPayment = balance * monthlyPaymentRate
    unpaid_balance = balance - minimumMonthlyPayment

    # The bank charges interest each month on unpaid balance
    balance = unpaid_balance + monthlyInterestRate * unpaid_balance
    month += 1

# Don't forget to round answer to two decimal places
balance = round(balance, 2)
print("Remaining balance: " + str(balance))
