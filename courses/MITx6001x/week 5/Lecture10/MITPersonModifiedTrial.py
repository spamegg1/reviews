# -*- coding: utf-8 -*-
"""
Created on Wed May 18 08:10:59 2016

@author: ericgrimson
"""
from Person import Person


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
        return (self.name + " says: " + utterance)


# example usage

m1 = MITPerson('Mark Zuckerberg')
m1.setBirthday(5, 14, 84)
m2 = MITPerson('Drew Houston')
m2.setBirthday(3, 4, 83)
m3 = MITPerson('Bill Gates')
m3.setBirthday(10, 28, 55)
m4 = Person('Travis Kalanik')
m5 = Person('Steve Wozniak')


MITPersonList = [m1, m2, m3, m4, m5]

for e in MITPersonList:
    print(e)

MITPersonList.sort()

print()

for e in MITPersonList:
    print(e)
