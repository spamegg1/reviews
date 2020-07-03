# -*- coding: utf-8 -*-
"""
Created on Wed May 18 08:10:59 2016

@author: ericgrimson
"""
from PersonTrial import Person


class MITPerson(Person):
    nextIdNum = 0  # next ID number to assign

    def __init__(self, name):
        Person.__init__(self, name)  # initialize Person attributes
        # new MITPerson attribute: a unique ID number
        self.idNum = MITPerson.nextIdNum
        MITPerson.nextIdNum += 1

    def getIdNum(self):
        return self.idNum

    # sorting MIT people uses their ID number, not name!
    def __lt__(self, other):
        return self.idNum < other.idNum

    def speak(self, utterance):
        return (self.getLastName() + " says: " + utterance)


# example usage

m3 = MITPerson('Mark Zuckerberg')
Person.setBirthday(m3, 5, 14, 84)
m2 = MITPerson('Drew Houston')
Person.setBirthday(m2, 3, 4, 83)
m1 = MITPerson('Bill Gates')
Person.setBirthday(m1, 10, 28, 55)


MITPersonList = [m1, m2, m3]


for e in MITPersonList:
    print(e)

MITPersonList.sort()

print()

for e in MITPersonList:
    print(e)


p1 = MITPerson('Eric')
p2 = MITPerson('John')
p3 = MITPerson('John')
p4 = Person('John')
print()
print(p1 < p2)
print(p4 < p1)
print(p1 < p4)
