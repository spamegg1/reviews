'''
author: spamegg

Consider the following function definition: 

def integerDivision(x, a):
    """
    x: a non-negative integer argument
    a: a positive integer argument

    returns: integer, the integer division of x divided by a.
    """
    while x >= a:
        count += 1
        x = x - a
    return count

When we call

    print(integerDivision(5, 3))

we get the following error message:

File "temp.py", line 9, in integerDivision
    count += 1
UnboundLocalError: local variable 'count' referenced before assignment

Your task is to modify the code for integerDivision so that this error
does not occur. 
'''

def integerDivision(x, a):
    """
    x: a non-negative integer argument
    a: a positive integer argument

    returns: integer, the integer division of x divided by a.
    """
    count = 0  # THIS IS THE LINE I ADDED
    while x >= a:
        count += 1
        x = x - a
    return count


if __name__ == "__main__":
    fun = 'integerDivision'
    print(integerDivision(5, 3))
    print(f"{fun}: Tests passed!")
