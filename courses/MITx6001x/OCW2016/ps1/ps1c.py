# Constants given to us by the problem
semi_annual_raise = 0.07
investment_rate = 0.04
total_cost = 1000000
down_payment = total_cost * 0.25


def savings(starting_salary, portion_saved):
    months = 0
    current_savings = 0
    salary = starting_salary

    while months < 36:
        if months > 0 and months % 6 == 0:
            annual_raise = salary * semi_annual_raise
            salary = salary + annual_raise

        investment_return = current_savings * investment_rate / 12
        current_savings += salary * portion_saved / 120000
        current_savings += investment_return

        months += 1

    return current_savings


def optimal_saving_rate():
    # Variables for bisection search
    left = 0
    right = 10000
    portion_saved = 5000
    steps_in_search = 0

    starting_salary = float(input("Enter the starting salary: "))

    if savings(starting_salary, 10000) < down_payment:
        print("It is not possible to pay the down payment in three years.")
    else:
        current_savings = savings(starting_salary, portion_saved)
        steps_in_search += 1

        while abs(current_savings - down_payment) >= 100.0:
            if current_savings < down_payment:
                left = portion_saved
            else:
                right = portion_saved

            portion_saved = (left + right) // 2
            steps_in_search += 1
            current_savings = savings(starting_salary, portion_saved)

        print("Best savings rate:", portion_saved / 10000)
        print("Steps in bisection search:", steps_in_search)


if __name__ == "__main__":
    optimal_saving_rate()
