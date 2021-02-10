# Paste your code into this box
lower_bound, upper_bound = 0, 100
low, high = lower_bound, upper_bound
print("Please think of a number between " +  str(low) + " and " + str(high) + "!")

guess = (low + high) // 2

print("Is your secret number " + str(guess) + " ?")
user_input = input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")

while str(user_input) != 'c':
    if user_input not in ('h', 'l'):
        print("Sorry, I did not understand your input.")
    elif user_input == 'h':
        high = guess
        guess = (low + high) // 2
    elif user_input == 'l':
        low = guess
        guess = (low + high) // 2

    print("Is your secret number " + str(guess) + "?")
    user_input = input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")
print("Game over. Your secret number was: " + str(guess))
