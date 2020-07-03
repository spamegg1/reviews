# -*- coding: utf-8 -*-
"""
Created on Wed May 18 08:14:58 2016

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


class TransferStudent(MITPerson):
    pass


def isStudent(obj):
    return isinstance(obj, UG) or isinstance(obj, Grad)
