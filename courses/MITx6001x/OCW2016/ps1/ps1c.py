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

def bisearch_one_step(saving, down_pay, left, right, portion, steps):
    new_left, new_right, new_steps = left, right, steps + 1

    if saving < down_pay:
        new_left = portion
    else:
        new_right = portion

    new_portion = (new_left + new_right) // 2

    return (new_left, new_right, new_portion, new_steps)


def bisearch(salary, inv_rate, semi_raise, down_pay,
    left, right, portion, steps):

    saving = savings(salary, portion, inv_rate, semi_raise)
    if abs(saving - down_pay) < 100:
        return portion, steps

    result = bisearch_one_step(saving, down_pay, left, right, portion, steps)
    new_left, new_right, new_portion, new_steps = result
    
    return bisearch(salary, inv_rate, semi_raise, down_pay,
                    new_left, new_right, new_portion, new_steps)
    

def optimal_saving_rate(salary, inv_rate, semi_raise, down_pay):
    max_saving = savings(salary, 10000, inv_rate, semi_raise)

    if max_saving < down_pay:
        print("It is not possible to pay the down payment in three years.")
    else:
        left, right, portion, steps = 0, 10000, 5000, 1
        portion, steps = bisearch(salary, inv_rate, semi_raise, down_pay,
                                  left, right, portion, steps)
        print("Best savings rate:", portion / 10000)
        print("Steps in bisection search:", steps)


def tests():
    inv_rate, semi_raise, down_pay = 0.04, 0.07, 250000
    left, right, portion, steps = 0, 10000, 5000, 1

    print("Testing savings:")
    assert savings(150000, 5000, inv_rate, semi_raise) == 283387.20615677064
    assert savings(150000, 2500, inv_rate, semi_raise) == 141693.60307838532
    assert savings(150000, 3750, inv_rate, semi_raise) == 212540.40461757814
    assert savings(300000, 5000, inv_rate, semi_raise) == 566774.4123135413
    assert savings(300000, 2500, inv_rate, semi_raise) == 283387.20615677064
    assert savings(300000, 1250, inv_rate, semi_raise) == 141693.60307838532
    print("Testing savings: passed!")

    print("Testing bisearch_one_step:")
    assert (0, 5000, 2500, 2) == bisearch_one_step(
        283387.20615677064, down_pay, 0, 10000, 5000, 1)
    assert (2500, 5000, 3750, 3) == bisearch_one_step(
        141693.60307838532, down_pay, 0, 5000, 2500, 2)
    assert (3750, 5000, 4375, 4) == bisearch_one_step(
        212540.40461757814, down_pay, 2500, 5000, 3750, 3)
    assert (0, 5000, 2500, 2) == bisearch_one_step(
        283387.20615677064, down_pay, 0, 10000, 5000, 1)
    assert (2500, 5000, 3750, 3) == bisearch_one_step(
        141693.60307838532, down_pay, 0, 5000, 2500, 2)
    assert (3750, 5000, 4375, 4) == bisearch_one_step(
        212540.40461757814, down_pay, 2500, 5000, 3750, 3)
    print("Testing bisearch_one_step: passed!")

    print("Testing bisearch:")
    assert (4411, 12) == bisearch(150000, inv_rate, semi_raise, down_pay,
                                  left, right, portion, steps)
    assert (2206,  9) == bisearch(300000, inv_rate, semi_raise, down_pay,
                                  left, right, portion, steps)
    print("Testing bisearch: passed!")

    print("All tests pass!")


def main():
    # Constants given to us by the problem
    SEMI_RAISE = 0.07
    INV_RATE = 0.04
    TOTAL_COST = 1000000
    DOWNPAY = TOTAL_COST * 0.25

    SALARY = float(input("Enter the starting salary: "))
    optimal_saving_rate(SALARY, INV_RATE, SEMI_RAISE, DOWNPAY)


if __name__ == "__main__":
    tests()
    # main()
