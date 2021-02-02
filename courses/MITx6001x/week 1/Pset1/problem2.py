# Assume s is a string of lower case characters.
# Write a program that prints the number of times the string 'bob' occurs in s.
# For example, if s = 'azcbobobegghakl', then your program should print
# Number of times bob occurs is: 2
occurrences = 0
start = 0
while start < len(s):
    index = s[start:].find('bob')
    if index == -1:
        break
    else:
        occurrences += 1
        start += index + 2
print("Number of times bob occurs is: ", occurrences)
