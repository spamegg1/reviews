# -*- coding: utf-8 -*-
"""
Created on Sat Jun 11 11:14:45 2016

@author: ericgrimson
"""
import random


class Animal(object):
    def __init__(self, age):
        self.age = age
        self.name = None

    def get_age(self):
        return self.age

    def get_name(self):
        return self.name

    def set_age(self, newage):
        self.age = newage

    def set_name(self, newname=""):
        self.name = newname

    def __str__(self):
        return "animal:"+str(self.name)+":"+str(self.age)


class Cat(Animal):
    def speak(self):
        print("meow")

    def __str__(self):
        return "cat:"+str(self.name)+":"+str(self.age)


class Rabbit(Animal):
    def speak(self):
        print("meep")

    def __str__(self):
        return "rabbit:"+str(self.name)+":"+str(self.age)


class Person(Animal):
    def __init__(self, name, age):
        Animal.__init__(self, age)
        Animal.set_name(self, name)
        self.friends = []

    def get_friends(self):
        return self.friends

    def add_friend(self, fname):
        if fname not in self.friends:
            self.friends.append(fname)

    def speak(self):
        print("hello")

    def age_diff(self, other):
        # alternate way: diff = self.age - other.age
        diff = self.get_age() - other.get_age()
        if self.age > other.age:
            print(self.name, "is", diff, "years older than", other.name)
        else:
            print(self.name, "is", -diff, "years younger than", other.name)

    def __str__(self):
        return "person:"+str(self.name)+":"+str(self.age)


class Student(Person):
    def __init__(self, name, age, major=None):
        Person.__init__(self, name, age)
        self.major = major

    def change_major(self, major):
        self.major = major

    def speak(self):
        r = random.random()
        if r < 0.25:
            print("i have homework")
        elif 0.25 <= r < 0.5:
            print("i need sleep")
        elif 0.5 <= r < 0.75:
            print("i should eat")
        else:
            print("i am watching tv")

    def __str__(self):
        return "student:"+str(self.name)+":"+str(self.age)+":"+str(self.major)


jelly = Cat(1)
jelly.set_name('Jelly')
tiger = Cat(1)
tiger.set_name('Tiger')
bean = Cat(0)
bean.set_name('Bean')
print(jelly)
jelly.speak()
blob = Animal(1)
peter = Rabbit(3)
peter.speak()
eric = Person('Eric', 45)
eric.speak()
john = Person('John', 55)
eric.age_diff(john)

fred = Student('Fred', 18, 'Course VI')
