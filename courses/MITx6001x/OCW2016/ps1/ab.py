salary = float(input("Enter the starting salary: "))
salary_const = salary
total_cost = 1000000

x = total_cost * 0.25
epsilon = 100
numGuesses = 0
low = 0
high = 10000
ans = (high + low) / 2.0
current_savings = 0

while abs(current_savings - x) > epsilon:

    salary = salary_const
    current_savings = 0.0
    r = 0.04
    semi_anual_raise = 0.07
    numGuesses += 1
    months = 0

    while current_savings < x:
        current_savings = current_savings + current_savings*r/12
        current_savings = current_savings + (salary/12) * float(ans / 10000)
        months += 1
        if months % 6 == 0:
           salary = salary + salary * semi_anual_raise

    if ans >= 10000:
        print("It is not possible to pay the down payment in three years")
        quit()

    if months > 36:
        low = ans
    else:
        high = ans
    ans = (high + low)/2.0

print('Best savings rate', float(ans/10000))
print("Steps in bisection search:", numGuesses)
