# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 22:17:48 2018

@author: spamegg
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

if __name__ == "__main__":                
    # Test Case 1:
    # balance = 3329
    # annualInterestRate = 0.2
    # Result Your Code Should Generate:
    # -------------------
    # Lowest Payment: 310
                  
    # Test Case 2:
    # balance = 4773
    # annualInterestRate = 0.2
    # Result Your Code Should Generate:
    # -------------------
    # Lowest Payment: 440

    # Test Case 3:
    # balance = 3926
    # annualInterestRate = 0.2
    # Result Your Code Should Generate:
    # -------------------
    # Lowest Payment: 360

    # Test Case 4:
    balance = 794
    annualInterestRate = 0.25
    # Result Your Code Should Generate:
    # -------------------
    # Lowest Payment: 80

    # Bisection search to find the minimum
    # monthly payment that pays off balance
    left = 0
    right = balance
    result = (left + right) / 2
    epsilon = 10
    attempt = balance

    while abs(attempt) >= epsilon:
        attempt = creditBalance(balance, annualInterestRate, result)
        if attempt == 0:
            break
        if attempt > 0:
            left = result
        else:
            right = result
        result = (left + right) / 2

    # This line of code rounds the result to a multiple of 10
    #result = int((result + 9) // 10 * 10)


    print("Lowest payment: " + str(result))
