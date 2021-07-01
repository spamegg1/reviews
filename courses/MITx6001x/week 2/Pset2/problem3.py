# Test case 1
# balance = 320000
# annualInterestRate = 0.2
# Lowest Payment: 29157.09

# Test case 2
# balance = 999999
# annualInterestRate = 0.18
# Lowest Payment: 90325.03


left = balance / 12
right = (balance * (1 + annualInterestRate / 12.0)**12) / 12.0
monthlyPayment = (left + right) / 2
epsilon = 0.01
attempt = balance

while abs(attempt) >= epsilon:
    attempt = balance
    month = 1    
    while month <= 12:
        unpaid_balance = attempt - monthlyPayment
        attempt = unpaid_balance + (annualInterestRate / 12.0) * unpaid_balance
        month += 1

    if attempt > 0:
        left = monthlyPayment
    else:
        right = monthlyPayment
    monthlyPayment = (left + right) / 2

print("Lowest payment: " + str(round(monthlyPayment, 2)))
