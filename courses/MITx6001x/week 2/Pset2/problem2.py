# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 22:17:48 2018

@author: spamegg
"""
from math import ceil
"""
balance is: float
annualInterestRate is: float
returns: integer, rounded up to the closest multiple of 10

Calculates the minimum fixed monthly payment needed in
order pay off a credit card balance within 12 months.
"""  
# Test Case 1:
#balance = 3329
#annualInterestRate = 0.2
# Result Your Code Should Generate:
# -------------------
# Lowest Payment: 310
                  
# Test Case 2:
#balance = 4773
#annualInterestRate = 0.2
# Result Your Code Should Generate:
# -------------------
# Lowest Payment: 440
                        
# Test Case 3:
#balance = 3926
#annualInterestRate = 0.2
# Result Your Code Should Generate:
# -------------------
# Lowest Payment: 360
             

# Bisection search to find the minimum
# monthly payment that pays off balance
monthlyInterestRate = annualInterestRate / 12.0
left = 0
right = balance
monthlyPayment = (left + right) / 2
epsilon = 1
attempt = balance

while abs(attempt) >= epsilon:
    attempt = balance
    month = 1
    while month <= 12:
        unpaid_balance = attempt - monthlyPayment
        attempt = unpaid_balance + monthlyInterestRate * unpaid_balance
        month += 1
    attempt = round(attempt, 2)
    
    if attempt == 0:
        break
    if attempt > 0:
        left = monthlyPayment
    else:
        right = monthlyPayment
    monthlyPayment = (left + right) / 2

# This line of code rounds the result to a multiple of 10
monthlyPayment = ceil(ceil(monthlyPayment) / 10) * 10


print("Lowest payment: " + str(monthlyPayment))
