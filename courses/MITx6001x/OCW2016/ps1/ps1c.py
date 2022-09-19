def savings(start, portion, inv_rate, semi_raise):
    saving = 0
    salary = start

    for months in range(36):
        if months > 0 and months % 6 == 0:
            salary += salary * semi_raise

        inv_return = saving * inv_rate / 12
        saving += salary * portion / 120000
        saving += inv_return

    return saving


def bisearch(salary, inv_rate, semi_raise, down_pay):
    left, portion, right = 0, 5000, 10000
    steps = 1
    saving = savings(salary, portion, inv_rate, semi_raise)
    #print(f'Down pay: {down_pay}, Salary: {salary}')
    #print(f'{left} {right} {portion} {steps} {saving}')

    while abs(saving - down_pay) >= 100.0:
        if saving < down_pay:
            left = portion
        else:
            right = portion

        portion = (left + right) // 2
        steps += 1
        saving = savings(salary, portion, inv_rate, semi_raise)
        #print(f'{left} {right} {portion} {steps} {saving}')

    return portion, steps


def optimal_saving_rate(salary, inv_rate, semi_raise, down_pay):
    max_saving = savings(salary, 10000, inv_rate, semi_raise)

    if max_saving < down_pay:
        print("It is not possible to pay the down payment in three years.")
    else:
        portion, steps = bisearch(salary, inv_rate, semi_raise, down_pay)
        print("Best savings rate:", portion / 10000)
        print("Steps in bisection search:", steps)


def tests(inv_rate, semi_raise, down_pay):
    assert (4411, 12) == bisearch(150000, inv_rate, semi_raise, down_pay)
    assert (2206,  9) == bisearch(300000, inv_rate, semi_raise, down_pay)


if __name__ == "__main__":
    # Constants given to us by the problem
    SEMI_RAISE = 0.07
    INV_RATE = 0.04
    TOTAL_COST = 1000000
    DOWNPAY = TOTAL_COST * 0.25

    tests(INV_RATE, SEMI_RAISE, DOWNPAY)    

    SALARY = float(input("Enter the starting salary: "))
    optimal_saving_rate(SALARY, INV_RATE, SEMI_RAISE, DOWNPAY)
    
