# TODO
from cs50 import get_float

change = get_float("Change owed: ")
while change < 0:
    change = get_float("Change owed: ")

change_int = int(change * 100)

quarters, remaining1 = change_int // 25, change_int % 25
nickels, remaining2 = remaining1 // 10, remaining1 % 10
dimes, remaining3 = remaining2 // 5, remaining2 % 5

print(quarters + nickels + dimes + remaining3)
