def savings_36(start_salary, portion, inv_rate, semi_raise):
    # This should calculate and return the 36 month savings.
    # Make sure to count the months from 0 to 35, not 1 to 36.
    # Apply the semi-annual raise whenever the month is
    # positive and divisible by 6.
    # Do NOT MUTATE (update) the start_salary variable.
    # Create a new fresh variable named salary,
    # set its initial value to start_salary, and mutate that instead.

def bisearch_one_step(saving, down_pay, left, right, portion, steps):
    # This should return the next values of left, right, portion, steps.
    # Do NOT check the loop-stopping condition here.
    # We will do that in the next function.
    # Just use the bisection search logic, regardless of the stopping condition.
    # Use INTEGER DIVISION for the new portion (with // 2 instead of / 2).
    # Do NOT mutate (update) left, right, portion, steps.
    # Create new, fresh variables new_left, new_right, new_portion, new_steps
    # and return those instead.
    # This function is very useful, because you can see the "next step results"
    # without inserting a bunch of print statements everywhere.
    # Because it only advances the search by 1 STEP ONLY.

def bisearch(salary, inv_rate, semi_raise, down_pay, left, right, portion, steps):
    # Use the saving_36 function to calculate the savings for the current portion.
    # If absolute value of (saving - down_pay) is less than 100,
    # you can stop, and return portion and steps.
    # Otherwise, call bisearch_one_step with the savings.
    # This will give you new_left, new_right, new_portion, new_steps.
    # Then call bisearch with these new values. (loop)
    # salary, inv_rate, semi_raise, down_pay stay unchanged.

def optimal_saving_rate(salary, inv_rate, semi_raise, down_pay):
    # Calculate maximum possible 36-month savings with portion = 10000.
    # If this value is less than down_pay, then it's not possible to
    # pay the down payment in 3 years.
    # Otherwise, call bisearch with starting values of
    # left, right, portion = 0, 10000, 5000
    # That will return portion and steps.
    # Then print best savings rate as: portion / 10000,
    # and the number of steps in bisection search.

if __name__ == "__main__":
    # Here define the global constants of semi_raise, inv_rate, down_pay.
    # Then ask user for starting salary.
    # Then call optimal_saving_rate with that salary, and the above 3 constants.

# Here is some testing data:
# ~
# ~ starting salary = 150000
# ~ Left Right Portion Steps 3-year saving
# ~ 0    10000 5000     1   283387.20615677064
# ~ 0    5000  2500     2   141693.60307838532
# ~ 2500 5000  3750     3   212540.40461757814
# ~ 3750 5000  4375     4   247963.8053871745
# ~ 4375 5000  4687     5   265647.1670513571
# ~ 4375 4687  4531     6   256805.48621926573
# ~ 4375 4531  4453     7   252384.6458032201
# ~ 4375 4453  4414     8   250174.22559519726
# ~ 4375 4414  4394     9   249040.6767705701
# ~ 4394 4414  4404     10  249607.45118288381
# ~ 4404 4414  4409     11  249890.83838904058
# ~ 4409 4414  4411     12  250004.19327150314

# ~ starting salary = 300000
# ~ Left Right Portion Steps 3-year saving
# ~ 0    10000 5000     1   566774.4123135413
# ~ 0    5000  2500     2   283387.20615677064
# ~ 0    2500  1250     3   141693.60307838532
# ~ 1250 2500  1875     4   212540.40461757814
# ~ 1875 2500  2187     5   247907.12794594327
# ~ 2187 2500  2343     6   265590.4896101256
# ~ 2187 2343  2265     7   256748.8087780344
# ~ 2187 2265  2226     8   252327.96836198878
# ~ 2187 2226  2206     9   250060.8707127346
