# -*- coding: utf-8 -*-
"""
Created on Wed May 18 08:13:18 2016

@author: ericgrimson
"""
from MITPerson import MITPerson


class UG(MITPerson):
    def __init__(self, name, classYear):
        MITPerson.__init__(self, name)
        self.year = classYear

    def getClass(self):
        return self.year


class Grad(MITPerson):
    pass


def isStudent(obj):
    return isinstance(obj, UG) or isinstance(obj, Grad)


s1 = UG('Matt Damon', 2017)
s2 = UG('Ben Affleck', 2017)
s3 = UG('Arash Ferdowsi', 2018)
s4 = Grad('Drew Houston')
s5 = UG('Mark Zuckerberg', 2019)

studentList = [s1, s2, s3, s5, s4]
