"""
Your task is to define the following two methods for the Coordinate class:

    Add an __eq__ method that returns True if coordinates refer to same point in
    the plane (i.e., have the same x and y coordinate).

    Define __repr__, a special method that returns a string that looks like a
    valid Python expression that could be used to recreate an object with the
    same value. In other words, eval(repr(c)) == c given the definition of
    __eq__ from part 1.

    For more on __repr__, see this SO post:
    https://stackoverflow.com/questions/452300/python-object-repr-self-should-be-an-expression

"""


class Coordinate(object):
    def __init__(self,x,y):
        self.x = x
        self.y = y

    def getX(self):
        # Getter method for a Coordinate object's x coordinate.
        # Getter methods are better practice than just accessing an attribute directly
        return self.x

    def getY(self):
        # Getter method for a Coordinate object's y coordinate
        return self.y

    def __str__(self):
        return '<' + str(self.getX()) + ',' + str(self.getY()) + '>'

    def __eq__(self, other):
        # First make sure `other` is of the same type
        assert type(other) == type(self)
        # Since `other` is the same type, test if coordinates are equal
        return self.getX() == other.getX() and self.getY() == other.getY()

    def __repr__(self):
        return 'Coordinate(' + str(self.getX()) + ',' + str(self.getY()) + ')'
