# -*- coding: utf-8 -*-
"""
Created on Wed Sep  5 21:27:54 2018

@author: spamegg
"""


def guessing_game(lower_bound, upper_bound):
    """Play a number guessing game with the computer."""

    low, high = lower_bound, upper_bound
    print("Please think of a number between", low, "and", str(high) + "!")

    guess = (low + high) // 2

    print("Is your secret number", str(guess) + "?")
    user_input = input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly.")

    while str(user_input) != 'c':
        if user_input not in ('h', 'l'):
            print("Sorry, I did not understand your input.")
        elif user_input == 'h':
            high = guess
            guess = (low + high) // 2
        elif user_input == 'l':
            low = guess
            guess = (low + high) // 2

        print("Is your secret number", str(guess) + "?")
        user_input = input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly.")
    print("Game over. Your secret number was:", guess)


if __name__ == "__main__":
    guessing_game(0, 100)
