# (2 points.) File is never closed in `load` function

# In `load`, file is opened but never closed:
def load(dictionary):
    file = open(dictionary, "r")
    for line in file:
        words.append(line.rstrip())

    file.close()  # <- ADD THIS LINE!!!

    return True

# (2 points.) Capitalized words are always considered misspelled

# Even though, e.g., `cat` is in `dictionaries/large`,
# `check` thinks that `Cat` is misspelled:
def check(word):
    #if word in words:         # CHANGE THIS LINE
    if word.lower() in words:  # <- TO THIS
        return True
    else:
        return False


# (2 points.) Implementation of `check` is longer than it needs to be
def check(word):
    # CHANGE THESE LINES:
    # if word in words:
    #     return True
    # else:
    #     return False
    return word.lower() in words  # <- TO THIS

# (2 points.) Determining size of dictionary always takes linear time

# Calling size is taking O(n) time, where n is the number of words in words,
# even though getting the length of a list should take O(1) time on average,
# according to https://wiki.python.org/moin/TimeComplexity:
def size():
    n = 0
    for word in words:
        n += 1
    return n

# SOLUTION: instead of using this size() function, calculate the length only
# once at the beginning of the code, and store it in a variable like this:
size = len(words)  # and afterward, simply use this variable where needed.

# (2 points.) Checking words always takes linear time

# It seems check always takes O(n) time, where n is the number of words in words
# which is especially slow for large files:
def check(word):
    if word in words:
        return True
    else:
        return False

# SOLUTION: at the beginning of the code, convert `words` into a dictionary:
words_dict = {words[i]: i for i in range(len(words))}
# this conversion takes linear time, but all the look-ups afterwards take O(1).
