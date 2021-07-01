def months(
    salary: int, save: float, total: int, semi: float, down: float, rate: float
) -> int:
    """
    Calculates how many months it takes to save for a down payment.
    salary: int: your annual salary.
    save: float: the percentage of your salary that you save each month.
        For example save = 0.15
    total: int: the total cost of your dream home.
        total * down would give the cost of the down payment.
    semi: float : your semi-annual salary raise (as a percentage of salary).
        For example semi = 0.03
    down: float : percentage of the total cost that is required as down payment.
        For example down = 0.15
    rate: float : annual interest rate that you receive on your investments.
        For example rate = 0.04
    """
    month: int = 0
    current_savings: float = 0.0
    updated_salary: float = float(salary)

    while current_savings < total * down:
        invest_return: float = current_savings * rate / 12
        monthly_save: float = updated_salary / 12 * save
        current_savings += monthly_save + invest_return

        month += 1
        if month % 6 == 0:
            updated_salary += updated_salary * semi

    return month


def test():
    assert months(120000, 0.05, 500000, 0.03, 0.25, 0.04) == 142
    assert months(80000, 0.1, 800000, 0.03, 0.25, 0.04) == 159
    assert months(75000, 0.05, 1500000, 0.05, 0.25, 0.04) == 261
    print("Tests pass.")


if __name__ == "__main__":
    test()

    portion_down_payment: float = 0.25
    annual_rate: float = 0.04
    annual_salary: int = int(input("Enter your annual salary: "))

    portion_saved: float = float(
        input("Enter the percent of your salary to save, as a decimal: ")

    )

    total_cost: int = int(input("Enter the cost of your dream home: "))

    semi_annual_raise: float = float(
        input("Enter the semi-annual raise, as a decimal: ")
    )

    result = months(
        annual_salary,
        portion_saved,
        total_cost,
        semi_annual_raise,
        portion_down_payment,
        annual_rate,
    )
    print("Number of months:", result)
