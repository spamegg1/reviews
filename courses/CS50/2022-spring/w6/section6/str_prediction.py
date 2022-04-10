def main():
    phrase = input("Give me a string: ")

    # Round 1
    for i in range(0, len(phrase), 2):
        print(phrase[i], end="")
    print("")

    # Round 2
    for i in range(1, len(phrase) - 1):
        print(phrase[i], end="")
    print("")

    # Round 3
    for character in phrase:
        print(character, end="")
    print("")

    # Round 4
    for character in phrase[1:]:
        print(character, end="")
    print("")

    # Round 5
    i = 0
    while i < len(phrase):
        print(phrase[i], end="")
        i += 1
    print("")

    # TODO: Round 6
    # Reverse phrase, only by changing the range function's arguments
    for i in range(0, len(phrase), 1):
        print(phrase[i], end="")
    print("")

main()