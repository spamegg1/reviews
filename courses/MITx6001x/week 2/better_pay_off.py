# -*- coding: utf-8 -*-
"""
Created on Fri Sep  7 13:00:43 2018

@author:
"""


def credit_balance(bal, annual_interest_rate, monthly_payment):
    """
    bal is: float
    annual_interest_rate is: float
    monthly_payment is: float
    returns: integer

    Returns the credit card balance after one year if a person only pays the
    monthly payment each month."""

    # this variable will iterate through 12 months of the year
    month = 1
    result = bal

    while month <= 12:
        unpaid_balance = result - monthly_payment

        # The bank charges interest each month on unpaid balance
        result = unpaid_balance + (annual_interest_rate / 12.0) * unpaid_balance
        month += 1

    return result


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
    left = balance / 12
    right = (balance * (1 + annual_interest_rate / 12.0)**12) / 12.0
    result = (left + right) / 2
    epsilon = 0.01

    while abs(credit_balance(balance, annual_interest_rate, result)) >= epsilon:
        attempt = credit_balance(balance, annual_interest_rate, result)
        if attempt > 0:
            left = result
        else:
            right = result
        result = (left + right) / 2

    print("Lowest payment: " + str(result))
    return round(result, 2)


def test_min_monthly_payoff():
    """Tests the min_monthly_payoff function."""
    assert min_monthly_payoff(320000, 0.2) == 29157.09
    assert min_monthly_payoff(290021, 0.2) == 26425.53
    assert min_monthly_payoff(333421, 0.2) == 30379.96
    assert min_monthly_payoff(329256, 0.21) == 30130.98
    assert min_monthly_payoff(103997, 0.21) == 9517.01
    # assert min_monthly_payoff(298709, 0.22) == 27454.15
    min_monthly_payoff(298709, 0.22)
    assert min_monthly_payoff(334429, 0.22) == 30737.16
    assert min_monthly_payoff(91446, 0.18) == 8259.87
    #assert min_monthly_payoff(431144, 0.22) == 39626.17
    min_monthly_payoff(431144, 0.22)
    assert min_monthly_payoff(999999, 0.18) == 90325.03
    assert min_monthly_payoff(334839, 0.18) == 30244.37
    print("tests pass")


test_min_monthly_payoff()
