'''
author: spamegg

Consider the following function definition:

def f(n):
   """
   n: integer, n >= 0.
   """
   if n == 0:
      return n
   else:
      return n * f(n-1)

When we call f(3) we expect the result 6, but we get 0.

When we call f(1) we expect the result 1, but we get 0.

When we call f(0) we expect the result 1, but we get 0.

Using this information, choose what line of code should
be changed from the following choices: 
'''

def f(n):
   """
   n: integer, n >= 0.
   """
   if n == 0:
      return 1  # THIS IS THE LINE I CHANGED
   else:
      return n * f(n-1)


if __name__ == "__main__":
    fun = 'f'
    assert(f(3) == 6)
    assert(f(1) == 1)
    assert(f(0) == 1)
    print(f"{fun}: Tests passed!")
