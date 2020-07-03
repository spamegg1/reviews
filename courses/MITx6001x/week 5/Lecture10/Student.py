# -*- coding: utf-8 -*-
"""
Created on Wed May 18 08:16:50 2016

@author: ericgrimson
"""

class Student(MITPerson):
    pass

class UG(Student):
    def __init__(self, name, classYear):
        MITPerson.__init__(self, name)
        self.year = classYear

    def getClass(self):
        return self.year

class Grad(Student):
    pass

class TransferStudent(Student):
    pass

def isStudent(obj):
    return isinstance(obj,Student)



s1 = UG('Matt Damon', 2017)
s2 = UG('Ben Affleck', 2017)
s3 = UG('Arash Ferdowsi', 2018)
s4 = Grad('Drew Houston')
s5 = UG('Mark Zuckerberg', 2019)
p1 = MITPerson('Eric Grimson')
p2 = MITPerson('John Guttag')
p3 = MITPerson('Ana Bell')
q1 = Person('Bill Gates')
q2 = Person('Travis Kalanick')

studentList = [s1, s2, s3, s5, s4]

allList = studentList + [p1, p2, p3] + [q1, q2]
