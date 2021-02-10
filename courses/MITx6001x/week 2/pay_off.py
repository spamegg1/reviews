# -*- coding: utf-8 -*-
"""
Created on Fri Sep  7 12:09:28 2018

@author:
"""
from math import ceil


def credit_balance(balance, annual_interest_rate, monthly_payment):
    """
    balance is: float
    annual_interest_rate is: float
    monthly_payment is: float
    returns: integer

    Returns the credit card balance after one year if a person only pays the
    monthly payment each month."""

    # this variable will iterate through 12 months of the year
    month = 0

    while month < 12:
        unpaid_balance = balance - monthly_payment

        # The bank charges interest each month on unpaid balance
        balance = unpaid_balance + \
            (annual_interest_rate/12.0) * unpaid_balance
        month += 1

    # Don't forget to round answer to two decimal places
    balance = round(balance)

    return balance


def min_monthly_payoff(balance, annual_interest_rate):
    """
    balance is: float
    annual_interest_rate is: float
    returns: positive integer
    Returns the minimum fixed monthly payment that is required
    to pay off all balance.
    """

    # Bisection search to find the minimum fixed monthly payment
    # that pays off balance
    left = 0
    right = balance
    result = (left + right) / 2
    epsilon = 1

    while abs(credit_balance(balance, annual_interest_rate, result)) >= epsilon:
        attempt = credit_balance(balance, annual_interest_rate, result)
        if attempt == 0:
            break
        if attempt > 0:
            left = result
        else:
            right = result
        result = (left + right) / 2

    # This line of code rounds the result to a multiple of 10
    result = ceil(ceil(result) / 10) * 10

    print("Lowest payment: " + str(result))
    return result


def test_min_monthly_payoff():
    """Tests the min_monthly_payoff function."""
    assert min_monthly_payoff(3329, 0.2) == 310
    assert min_monthly_payoff(4773, 0.2) == 440
    assert min_monthly_payoff(3926, 0.2) == 360
    assert min_monthly_payoff(610, 0.2) == 60
    assert min_monthly_payoff(824, 0.2) == 80
    assert min_monthly_payoff(263, 0.25) == 30
    assert min_monthly_payoff(794, 0.25) == 80
    assert min_monthly_payoff(909, 0.25) == 90
    assert min_monthly_payoff(230, 0.18) == 30
    assert min_monthly_payoff(307, 0.25) == 30
    assert min_monthly_payoff(751, 0.2) == 70
    assert min_monthly_payoff(4822, 0.18) == 440
    assert min_monthly_payoff(3031, 0.2) == 280
    assert min_monthly_payoff(3248, 0.2) == 300
    assert min_monthly_payoff(3492, 0.2) == 320
    assert min_monthly_payoff(4356, 0.04) == 370
    assert min_monthly_payoff(4080, 0.15) == 370
    assert min_monthly_payoff(4633, 0.15) == 420
    assert min_monthly_payoff(4668, 0.15) == 420
    assert min_monthly_payoff(4146, 0.18) == 380
    assert min_monthly_payoff(3632, 0.18) == 330
    assert min_monthly_payoff(3765, 0.18) == 350
    assert min_monthly_payoff(3265, 0.18) == 300
    assert min_monthly_payoff(4763, 0.18) == 440
    assert min_monthly_payoff(4781, 0.04) == 410
    assert min_monthly_payoff(4593, 0.18) == 420
    assert min_monthly_payoff(4211, 0.2) == 390
    assert min_monthly_payoff(4142, 0.2) == 380
    print("tests pass")


test_min_monthly_payoff()
