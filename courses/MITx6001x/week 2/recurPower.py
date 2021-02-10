def recurPower(base, exp):
    '''
    base: int or float.
    exp: int >= 0
 
    returns: int or float, base^exp
    '''
    # Your code here
    if base == 0:
        return base
    if exp == 0:
        return 1
    return base * recurPower(base, exp - 1)
