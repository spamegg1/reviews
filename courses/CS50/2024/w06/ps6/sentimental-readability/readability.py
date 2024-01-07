# TODO
from cs50 import get_string


def main():
    # get input
    text = get_string("Text: ")

    # calculate letter, word, sentence count
    letters = count_letters(text)
    words = count_words(text)
    sentences = count_sentences(text)

    # calculate index and print result
    score = coleman_liau(letters, words, sentences)
    print_score(score)


def count_letters(text):
    count = 0
    for letter in text:
        if letter.isalpha():
            count += 1
    return count


# Assume that a sentence will not start or end with a space, and
# assume that a sentence will not have multiple spaces in a row
def count_words(text):
    count = 1
    for letter in text:
        if letter.isspace():
            count += 1
    return count


# Assume that a sentence will not start or end with a space, and
# assume that a sentence will not have multiple spaces in a row
def count_sentences(text):
    count = 0
    for letter in text:
        if letter in '.?!':
            count += 1
    return count


def coleman_liau(letters, words, sentences):
    L = letters / words * 100.0
    S = sentences / words * 100.0
    return 0.0588 * L - 0.296 * S - 15.8


def print_score(score):
    if score >= 16.0:
        print("Grade 16+")
    elif score < 1.0:
        print("Before Grade 1")
    else:
        print(f"Grade {int(round(score))}")


if __name__ == "__main__":
    main()
