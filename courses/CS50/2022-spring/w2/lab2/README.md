# [Lab 2: Scrabble](https://cs50.harvard.edu/college/2022/spring/labs/2/#lab-2-scrabble)

Labs are practice problems which, time permitting, may be started or  completed in your section, and are assessed on correctness only. You are encouraged to collaborate with classmates on this lab, though each  member in a group collaborating is expected to contribute equally to the lab.

Determine which of two Scrabble words is worth more.

```bash
$ ./scrabble
Player 1: COMPUTER
Player 2: science
Player 1 wins!
```



## [Getting Started](https://cs50.harvard.edu/college/2022/spring/labs/2/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2021/fall/labs/2/scrabble.zip
```

followed by Enter in order to download a ZIP called `scrabble.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip scrabble.zip
```

to create a folder called `scrabble`. You no longer need the ZIP file, so you can execute

```bash
rm scrabble.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd scrabble
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
scrabble/ $
```

If all was successful, you should execute

```bash
ls
```

and you should see a file called `scrabble.c`. Open that file by executing the below:

```bash
code scrabble.c
```

If you run into any trouble, follow these same steps steps again and see if you can determine where you went wrong!



## [Background](https://cs50.harvard.edu/college/2022/spring/labs/2/#background)

In the game of [Scrabble](https://scrabble.hasbro.com/en-us/rules), players create words to score points, and the number of points is the sum of the point values of each letter in the word.

​                                  

| A    | B    | C    | D    | E    | F    | G    | H    | I    | J    | K    | L    | M    | N    | O    | P    | Q    | R    | S    | T    | U    | V    | W    | X    | Y    | Z    |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 1    | 3    | 3    | 2    | 1    | 4    | 2    | 4    | 1    | 8    | 5    | 1    | 3    | 1    | 1    | 3    | 10   | 1    | 1    | 1    | 1    | 4    | 4    | 8    | 4    | 10   |

For example, if we wanted to score the word `Code`, we would note that in general Scrabble rules, the `C` is worth `3` points, the `o` is worth `1` point, the `d` is worth `2` points, and the `e` is worth `1` point. Summing these, we get that `Code` is worth `3 + 1 + 2 + 1 = 7` points.



## [Implementation Details](https://cs50.harvard.edu/college/2022/spring/labs/2/#implementation-details)

Complete the implementation of `scrabble.c`, such that it determines the winner of a short scrabble-like game, where two players each enter their word, and the higher scoring player wins.

- Notice that we’ve stored the point values of each letter of the alphabet in an integer array named   `POINTS`  .    

  - For example, `A` or `a` is worth 1 point (represented by `POINTS[0]`), `B` or `b` is worth 3 points (represented by `POINTS[1]`), etc.

- Notice that we’ve created a prototype for a helper function called `compute_score()` that takes a string as input and returns an `int`. Whenever we would like to assign point values to a particular word, we  can call this function. Note that this prototype is required for C to  know that `compute_score()` exists later in the program.

- In `main()`, the program prompts the two players for their words using the `get_string()` function. These values are stored inside variables named `word1` and `word2`.

- In  `  compute_score()`    , your program should compute, using the   `  POINTS`    array, and return the score for the string argument. Characters that  are not letters should be given zero points, and uppercase and lowercase letters should be given the same point values.    

  - For example, `!` is worth `0` points while `A` and `a` are both worth `1` point.
  - Though Scrabble rules normally require that a word be in the dictionary, no need to check for that in this problem!
  
- In `main()`, your program should print, depending on the players’ scores, `Player 1 wins!`, `Player 2 wins!`, or `Tie!`.



### [Walkthrough](https://cs50.harvard.edu/college/2022/spring/labs/2/#walkthrough)

This video was recorded when the course was still using CS50 IDE for writing code. Though the interface may look different from your codespace, the  behavior of the two environments should be largely similar!
[walkthrough](https://video.cs50.io/RtjxxxlN1gc)

### [Hints](https://cs50.harvard.edu/college/2022/spring/labs/2/#hints)

- You may find the functions `isupper()` and `islower()` to be helpful to you. These functions take in a character as the argument and return a boolean.

- To find the value at the `n`th index of an array called `arr`, we can write `arr[n]`. We can apply this to strings as well, as strings are arrays of characters.

- Recall that computers represent characters using [ASCII](http://asciitable.com/), a standard that represents each character as a number.

<details><summary>Not sure how to solve?</summary>

[help](https://video.cs50.io/USiLkXuXJEg)

</details>

### [How to Test Your Code](https://cs50.harvard.edu/college/2022/spring/labs/2/#how-to-test-your-code)

Your program should behave per the examples below.

```bash
$ ./scrabble
Player 1: Question?
Player 2: Question!
Tie!
$ ./scrabble
Player 1: Oh,
Player 2: hai!
Player 2 wins!
$ ./scrabble
Player 1: COMPUTER
Player 2: science
Player 1 wins!
$ ./scrabble
Player 1: Scrabble
Player 2: wiNNeR
Player 1 wins!
```

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/labs/2022/spring/scrabble
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 scrabble.c
```



## [How to Submit](https://cs50.harvard.edu/college/2022/spring/labs/2/#how-to-submit)

1. Download your `scrabble.c` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Lab 2: Scrabble”.
4. Drag and drop your `scrabble.c` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Lab 2: Scrabble submitted successfully!”