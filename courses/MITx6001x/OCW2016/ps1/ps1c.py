# Constants given to us by the problem
SEMI_ANNUAL_RAISE = 0.07
INV_RATE = 0.04
TOTAL_COST = 1000000
DOWN_PAYMENT = TOTAL_COST * 0.25


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


def bisection_search(salary, inv_rate, semi_raise, down_payment):
    # Variables for bisection search
    left, right = 0, 10000
    portion_saved = 5000
    steps_in_search = 0

    # do-while
    saving = savings(salary, portion_saved, inv_rate, semi_raise)
    steps_in_search += 1

    while abs(saving - down_payment) >= 100.0:
        if saving < down_payment:
            left = portion_saved
        else:
            right = portion_saved

        portion_saved = (left + right) // 2
        steps_in_search += 1
        saving = savings(salary, portion_saved, inv_rate, semi_raise)

    print("Best savings rate:", portion_saved / 10000)
    print("Steps in bisection search:", steps_in_search)


def optimal_saving_rate(salary, inv_rate, semi_raise, down_payment):
    max_saving = savings(salary, 10000, inv_rate, semi_raise)

    if max_saving < down_payment:
        print("It is not possible to pay the down payment in three years.")
    else:
        bisection_search(salary, inv_rate, semi_raise, down_payment)


if __name__ == "__main__":
    SALARY = float(input("Enter the starting salary: "))
    optimal_saving_rate(SALARY, INV_RATE, SEMI_ANNUAL_RAISE, DOWN_PAYMENT)
