# -*- coding: utf-8 -*-
"""
Created on Fri Oct 26 18:57:25 2018

@author:
"""


def is_triangular(k):
    """
    k, a positive integer
    returns True if k is triangular and False if not
    """
    # YOUR CODE HERE
    next_triangular = 0
    next_addition = 1

    while next_triangular <= k:
        next_triangular += next_addition
        next_addition += 1
        if k == next_triangular:
            return True
    return False


def test_is_triangular():
    """
    Tests the is_triangular function.
    """
    assert(is_triangular(1) == True)
    assert(is_triangular(2) == False)
    assert(is_triangular(3) == True)
    assert(is_triangular(4) == False)
    assert(is_triangular(5) == False)
    assert(is_triangular(6) == True)
    assert(is_triangular(7) == False)
    assert(is_triangular(8) == False)
    assert(is_triangular(9) == False)
    assert(is_triangular(10) == True)
    assert(is_triangular(11) == False)
    assert(is_triangular(12) == False)
    assert(is_triangular(13) == False)
    assert(is_triangular(14) == False)
    assert(is_triangular(15) == True)
    assert(is_triangular(16) == False)
    assert(is_triangular(17) == False)
    assert(is_triangular(18) == False)
    assert(is_triangular(19) == False)
    assert(is_triangular(20) == False)
    assert(is_triangular(21) == True)

    assert(is_triangular(5050) == True)
    assert(is_triangular(5051) == False)

    assert(is_triangular(20100) == True)
    assert(is_triangular(20101) == False)

    print("tests pass: is_triangular")


def largest_odd_times(L):
    """ Assumes L is a non-empty list of ints
        Returns the largest element of L that occurs an odd number
        of times in L. If no such element exists, returns None """
    # Your code here
    # First remove all elements of L that occur an even number of times
    odd_list = []
    for elt in L:
        if L.count(elt) % 2 == 1:
            odd_list.append(elt)

    # Now return the max of odd_list, else None
    if odd_list:
        return max(odd_list)
    return None


def test_largest_odd_times():
    """
    Tests the largest_odd_times function.
    """
    assert(largest_odd_times([]) == None)
    assert(largest_odd_times([1]) == 1)
    assert(largest_odd_times([1, 2]) == 2)
    assert(largest_odd_times([1, 1]) == None)
    assert(largest_odd_times([1, 2, 2]) == 1)
    assert(largest_odd_times([1, 1, 2, 2]) == None)
    assert(largest_odd_times([1, 2, 2, 3]) == 3)
    assert(largest_odd_times([1, 2, 3, 3]) == 2)
    assert(largest_odd_times([3, 9, 5, 3, 5, 3]) == 9)
    assert(largest_odd_times([2, 2, 4, 4]) == None)

    print("tests pass: largest_odd_times")


def dict_interdiff(d1, d2, f):
    '''
    d1, d2: dicts whose keys and values are integers
    Returns a tuple of dictionaries according to the instructions above
    '''
    # Your code here
    def dict_intersect(d1, d2):
        result = {}
        for key in d1.keys():
            if key in d2.keys():
                result[key] = f(d1[key], d2[key])
        return result

    def dict_difference(d1, d2):
        result = {}
        for key in d1.keys():
            if key not in d2.keys():
                result[key] = d1[key]
        for key in d2.keys():
            if key not in d1.keys():
                result[key] = d2[key]
        return result

    return (dict_intersect(d1, d2), dict_difference(d1, d2))


def test_dict_interdiff():
    """
    Tests the dict_interdiff function.
    """
    def f(a, b): return a + b

    def g(a, b): return a < b

    d1 = {1: 30, 2: 20, 3: 30, 5: 80}
    d2 = {1: 40, 2: 50, 3: 60, 4: 70, 6: 90}
    d3 = {2: 30, 4: 10, 6: 100, 7: 20}

    assert(dict_interdiff(d1, d1, f) == ({1: 60, 2: 40, 3: 60, 5: 160}, {}))
    assert(dict_interdiff(d2, d2, f) ==
           ({1: 80, 2: 100, 3: 120, 4: 140, 6: 180}, {}))
    assert(dict_interdiff(d3, d3, f) == ({2: 60, 4: 20, 6: 200, 7: 40}, {}))
    assert(dict_interdiff(d1, d1, g) ==
           ({1: False, 2: False, 3: False, 5: False}, {}))
    assert(dict_interdiff(d2, d2, g) ==
           ({1: False, 2: False, 3: False, 4: False, 6: False}, {}))
    assert(dict_interdiff(d3, d3, g) ==
           ({2: False, 4: False, 6: False, 7: False}, {}))

    assert(dict_interdiff(d2, d1, f) ==
           ({1: 70, 2: 70, 3: 90}, {4: 70, 5: 80, 6: 90}))
    assert(dict_interdiff(d1, d3, f) ==
           ({2: 50}, {1: 30, 3: 30, 5: 80, 4: 10, 6: 100, 7: 20}))
    assert(dict_interdiff(d3, d2, f) ==
           ({2: 80, 4: 80, 6: 190}, {1: 40, 3: 60, 7: 20}))
    assert(dict_interdiff(d1, d2, g) ==
           ({1: True, 2: True, 3: True}, {4: 70, 5: 80, 6: 90}))
    assert(dict_interdiff(d3, d1, g) ==
           ({2: False}, {1: 30, 3: 30, 5: 80, 4: 10, 6: 100, 7: 20}))
    assert(dict_interdiff(d2, d3, g) ==
           ({2: False, 4: False, 6: True}, {1: 40, 3: 60, 7: 20}))

    print("tests pass: dict_interdiff")


# Do not change the Location or Campus classes. ###
# Location class is the same as in lecture.     ###
class Location(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def move(self, deltaX, deltaY):
        return Location(self.x + deltaX, self.y + deltaY)

    def getX(self):
        return self.x

    def getY(self):
        return self.y

    def dist_from(self, other):
        xDist = self.x - other.x
        yDist = self.y - other.y
        return (xDist**2 + yDist**2)**0.5

    def __eq__(self, other):
        return (self.x == other.x and self.y == other.y)

    def __str__(self):
        return '<' + str(self.x) + ',' + str(self.y) + '>'


class Campus(object):
    def __init__(self, center_loc):
        self.center_loc = center_loc

    def __str__(self):
        return str(self.center_loc)


class MITCampus(Campus):
    """ A MITCampus is a Campus that contains tents """
    def __init__(self, center_loc, tent_loc=Location(0, 0)):
        """ Assumes center_loc and tent_loc are Location objects
        Initializes a new Campus centered at location center_loc
        with a tent at location tent_loc """
        Campus.__init__(self, center_loc)
        self.tents = [tent_loc]

    def add_tent(self, new_tent_loc):
        """ Assumes new_tent_loc is a Location
        Adds new_tent_loc to the campus only if the tent is
        at least 0.5 distance
        away from all other tents already there. Campus is unchanged otherwise.
        Returns True if it could add the tent, False otherwise. """
        if all(new_tent_loc.dist_from(tent_loc) >= 0.5
                for tent_loc in self.tents):
            self.tents.append(new_tent_loc)
            return True
        return False

    def remove_tent(self, tent_loc):
        """ Assumes tent_loc is a Location
        Removes tent_loc from the campus.
        Raises a ValueError if there is not a tent at tent_loc.
        Does not return anything """
        found = False
        for other_tent_loc in self.tents:
            if tent_loc.__eq__(other_tent_loc):
                self.tents.remove(tent_loc)
                found = True
                break
        if not found:
            raise ValueError

    def get_tents(self):
        """ Returns a list of all tents on the campus. The list should contain
        the string representation of the Location of a tent. The list should
        be sorted by the x coordinate of the location. """
        return sorted([tent_loc.__str__() for tent_loc in self.tents])


def test_MITCampus():
    """
    Tests the MITCampus class.
    """
    c = MITCampus(Location(1, 2))

    assert(c.add_tent(Location(2, 3)) == True)
    assert(c.add_tent(Location(1, 2)) == True)
    assert(c.add_tent(Location(0, 0)) == False)
    assert(c.add_tent(Location(2, 3)) == False)

    c.add_tent(Location(1, 1))
    c.remove_tent(Location(1, 1))

    assert(c.get_tents() == ['<0,0>', '<1,2>', '<2,3>'])

    print("tests pass: MITCampus")


# Main
test_is_triangular()
test_largest_odd_times()
test_dict_interdiff()
test_MITCampus()
