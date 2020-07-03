# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 22:17:48 2018

@author:
"""


def credit_balance(balance, annual_interest_rate, monthly_payment_rate):
    """
    balance is: float
    annual_interest_rate is: float
    monthly_payment_rate is: float
    returns: float, rounded to 2 decimal places

    Returns the credit card balance after one year if a person only pays the
    minimum monthly payment required by the credit card company each month."""

    # this variable will iterate through 12 months of the year
    month = 0

    while month < 12:
        monthly_payment = balance * monthly_payment_rate
        unpaid_balance = balance - monthly_payment

        # The bank charges interest each month on unpaid balance
        balance = unpaid_balance + (annual_interest_rate/12.0) * unpaid_balance
        month += 1

    # Don't forget to round answer to two decimal places
    balance = round(balance, 2)

    print("Remaining balance: " + str(balance))
    return balance


def test_credit_balance():
    """Tests the credit_balance function."""
    assert credit_balance(42, 0.2, 0.04) == 31.38
    assert credit_balance(484, 0.2, 0.04) == 361.61
    print("tests pass")


test_credit_balance()
