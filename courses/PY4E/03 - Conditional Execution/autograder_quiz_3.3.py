# 3.3 Write a program to prompt for a score between 0.0 and 1.0.
# If the score is out of range, print an error.
# If the score is between 0.0 and 1.0,
# print a grade using the following table:
# Score Grade
# >= 0.9 A
# >= 0.8 B
# >= 0.7 C
# >= 0.6 D
# < 0.6 F
# If the user enters a value out of range,
# print a suitable error message and exit.
# For the test, enter a score of 0.85.
# Desired output:
# B

score = float(input("Enter Score: "))

if score < 0 or score > 1:
    print("Error! Score out of range [0.0, 1.0]")
    quit()
else:
    grade = ''
    if score >= 0.9:
        grade = 'A'
    elif score >= 0.8:
        grade = 'B'
    elif score >= 0.7:
        grade = 'C'
    elif score >= 0.6:
        grade = 'D'
    else:
        grade = 'F'
    print(grade)
