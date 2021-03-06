"""
author: spamegg

Write a procedure called oddTuples, which takes a tuple as input,
and returns a new tuple as output, where every other element of
the input tuple is copied, starting with the first one.
So if test is the tuple ('I', 'am', 'a', 'test', 'tuple'),
then evaluating oddTuples on this input would return the tuple
('I', 'a', 'tuple'). 

"""


def oddTuples(aTup):
    """
    aTup: a tuple

    returns: tuple, every other element of aTup.
    """
    # Your Code Here
    if len(aTup) in [0, 1]:
        return aTup
    return (aTup[0],) + oddTuples(aTup[2:])


if __name__ == "__main__":
    testFun = 'oddTuples'
    assert (oddTuples(()) == ())
    assert (oddTuples((10,)) == (10,))
    assert (oddTuples((13, 11, 17, 20, 8, 8, 8))
            == (13, 17, 8, 8))
    assert (oddTuples((0, 12, 1, 19, 9, 8, 0, 17, 12, 18))
            == (0, 1, 9, 0, 12))
    assert (oddTuples((4, 17, 20, 5)) == (4, 20))
    assert (oddTuples((4, 1, 2, 19, 3, 9, 1, 10))
            == (4, 2, 3, 1))
    assert (oddTuples((20, 14, 5, 13, 15, 18, 6, 4, 13))
            == (20, 5, 15, 6, 13))
    print(f"{testFun}: Tests passed!")
