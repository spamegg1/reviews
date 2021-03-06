"""
author: spamegg

Consider the following sequence of expressions: 

animals = { 'a': ['aardvark'], 'b': ['baboon'], 'c': ['coati']}

animals['d'] = ['donkey']
animals['d'].append('dog')
animals['d'].append('dingo')

We want to write some simple procedures that work on dictionaries
to return information.

First, write a procedure, called how_many, which returns the sum of
the number of values associated with a dictionary. For example:
>>> print(how_many(animals))
6
"""


def how_many(aDict):
    '''
    aDict: A dictionary, where all the values are lists.

    returns: int, how many values are in the dictionary.
    '''
    # Your Code Here
    result = 0
    for thing in aDict.values():
        result += len(thing)
    return result


if __name__ == "__main__":
    func = 'how_many'
    assert(how_many({'B': [15], 'u': [10, 15, 5, 2, 6]}) == 6)
    print(f"{func}: Tests passed!")
