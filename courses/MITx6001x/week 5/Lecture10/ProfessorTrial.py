# -*- coding: utf-8 -*-
"""
Created on Sat Jun 11 15:13:22 2016

@author: ericgrimson
"""
from MITPerson import MITPerson


class Professor(MITPerson):
    def __init__(self, name, department):
        MITPerson.__init__(self, name)
        self.department = department

    def speak(self, utterance):
        newUtterance = 'In course ' + self.department + ' we say '
        return MITPerson.speak(self, newUtterance + utterance)

    def lecture(self, topic):
        return self.speak('it is obvious that ' + topic)


faculty = Professor('Doctor Arrogant', 'six')
print(faculty.speak('hi there'))
print(faculty.lecture("hi there"))
