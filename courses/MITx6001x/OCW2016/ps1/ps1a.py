def months(salary: int, save: float, total: int, down: float, rate: float) -> int:
    """
    Calculates how many months it takes to save for a down payment.
    salary: int: your annual salary.
    save: float: the percentage of your salary that you save each month.
        For example save = 0.15
    total: int: the total cost of your dream home.
        total * down would give the cost of the down payment.
    down: float : percentage of the total cost that is required as down payment.
        For example down = 0.15
    rate: float : annual interest rate that you receive on your investments.
        For example rate = 0.04
    """
    month: int = 0
    current_savings: float = 0.0

    while current_savings < total * down:
        invest_return: float = current_savings * rate / 12
        monthly_save: float = salary / 12 * save
        current_savings += monthly_save + invest_return
        month += 1

    return month


def test():
    assert months(120000, 0.1, 1000000, 0.25, 0.04) == 183
    assert months(80000, 0.15, 500000, 0.25, 0.04) == 105
    print("Tests pass.")


if __name__ == "__main__":
    # test()
    portion_down_payment: float = 0.25
    annual_rate: float = 0.04
    annual_salary: int = int(input("Enter your annual salary: "))
    portion_saved: float = float(
        input("Enter the percent of your salary to save, as a decimal: ")
    )
    total_cost: int = int(input("Enter the cost of your dream home: "))
    down_payment: float = total_cost * portion_down_payment
    result = months(
        annual_salary, portion_saved, total_cost, portion_down_payment, annual_rate
    )
    print("Number of months:", result)
