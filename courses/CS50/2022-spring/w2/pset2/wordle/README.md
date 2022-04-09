# [Wordle50](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#wordle50)

For this problem, you’ll implement a program that behaves similarly to the popular [Wordle](https://www.powerlanguage.co.uk/wordle/) daily word game.

```bash
$ ./wordle 5
This is WORDLE50
You have 6 tries to guess the 5-letter word I'm thinking of
Input a 5-letter word: crash
Guess 1: crash
Input a 5-letter word: scone
Guess 2: scone
Input a 5-letter word: since
Guess 3: since
You won!
```



## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2022/spring/psets/2/wordle.zip
```

followed by Enter in order to download a ZIP called `wordle.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip wordle.zip
```

to create a folder called `wordle`. You no longer need the ZIP file, so you can execute

```bash
rm wordle.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd wordle
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
wordle/ $
```

If all was successful, you should execute

```bash
ls
```

and see a file named `wordle.c`, as well as `5.txt`, `6.txt`, `7.txt` and `8.txt`. Executing `code wordle.c` should open the file where you will type your code for this problem  set. If not, retrace your steps and see if you can determine where you  went wrong! If you try to compile the game now, it will do so without  errors, but when you try to run it you will see this error:

```bash
Error opening file 0.txt.
```

‘Tis normal, though, since you haven’t yet implemented part of the code we need to make that error message go away!



## [Background](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#background)

Odds are, if you’re a Facebook user, at least one of your friends has posted something looking like this in the last few weeks:

![Wordle results](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/wordle.png)

If so, your friend has played Wordle, and are sharing their results  for that day! Each day, a new “secret word” is chosen (the same for  everyone) and the object is to guess what the secret word is within six  tries. Fortunately, given that there are more than six five-letter words in the English language, you may get some clues along the way, and the  image above actually shows your friend’s progression through their  guesses, using those clues to try to home in on the correct word. Using a scheme similar to the game [Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game)), if after you guess that letter turns green, it means not only is that  letter in the secret word that day, but it  is also in the correct  position. If it turns yellow, it means that the letter guessed appears *somewhere* in the word, but not in that spot. Letters that turn gray aren’t in the word at all and can be omitted from future guesses.

Let’s finish writing a program called `wordle` that enables us to recreate this game and play it in our terminal  instead. We’ll make a few slight changes to the game (for example, the  way it handles a letter appearing twice in a word isn’t the same as how  the real game handles it, but for simplicity’s sake, we’ll err on the  side of ease of understanding rather than a perfectly faithful  interpretation), and we’ll use red text instead of gray to indicate  letters that aren’t in the word at all. At the time the user executes  the program, they should decide, by providing a command-line argument,  what the length of the word they want to guess is, between 5 and 8  letters.

Here are a few examples of how the program should work. For example, if the user omits a command line argument entirely:

```bash
$ ./wordle
Usage: ./wordle wordsize
```

If the user instead does provide a command-line argument, but it’s not in the correct range:

```bash
$ ./wordle 4
Error: wordsize must be either 5, 6, 7, or 8
```

Here’s how the program might work if the user provides a key of `5`:

```bash
$ ./wordle 5
This is WORDLE50
You have 6 tries to guess the 5-letter word I'm thinking of
Input a 5-letter word:
```

At which point, the user should type in a 5-letter word. Of course,  the user could well be stubborn, and we should make sure they’re  following the rules:

```bash
$ ./wordle 5
This is WORDLE50
You have 6 tries to guess the 5-letter word I'm thinking of
Input a 5-letter word: wordle
Input a 5-letter word: computer
Input a 5-letter word: okay
Input a 5-letter word: games
Guess 1: games
Input a 5-letter word:
```

Notice that we didn’t even count any of those invalid attempts as  guesses. But as soon as they made a legitimate attempt, we counted it as a guess and reported on the status of the word. Looks like the user has a few clues now; they know the word contains an `a` and an `e` somewhere, but not in the exact spots they appear in the word `games`. And they know that `g`, `m`, and `s` don’t appear in the word at all, so future guesses can omit them. Perhaps they might try, say, `heart` next! ❤️



## [Specification](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#specification)

Design and implement a program, `wordle`, that completes the implementation of our Wordle50 clone of the game.  You’ll notice that some large pieces of this program have already been  written for you–you are not allowed to modify any of those parts of the  program. Instead, your work should be constrained to the seven `TODO`s we’ve left behind for you to fill in. Each one of those parts solves a  specific problem, and we recommend you tackle them in order from 1 to 7. Each numbered `TODO` corresponds to the same item in the below list.

1. In the first `TODO`, you should ensure the program accepts a single command-line argument. Let’s call it `k` for the sake of discussion. If the program was not run with a single  command-line argument, you should print the error message as we  demonstrate above and return `1`, ending the program.
1. In the second `TODO`, you should make sure that `k` is one of the acceptable values (5, 6, 7, or 8), and store that value in `wordsize`; we’ll need to make use of that later. If the value of `k` is not one of those four values exactly, you should print the error message as we demonstrate above and return `1`, ending the program.

After that, the staff has already written some code that will go  through and open the word list for the length of word the user wants to  guess and randomly selects one from the 1000 options available. Don’t  worry about necessarily understanding all of this code, it’s not  important for purposes of this assignment. We’ll see something similar  though in a later assignment, and it will make a lot more sense then!  This is a good place to stop and test, before proceeding to the next `TODO`, that your code behaves as expected. It’s always easier to debug programs if you do so methodically!

1. For the third `TODO`, you should help defend against stubborn users by making sure their  guess is the correct length. For that, we’ll turn our attention to the  function `get_guess`, which you’ll need to implement in full. A user should be prompted (as via `get_string`) to type in a `k`-letter word (remember, that value is passed in as a parameter to `get_guess`) and if they supply a guess of the wrong length, they should be re-prompted (much like in [Mario](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/)) until they provide exactly the value you expect from them. Right now,  the distribution code doesn’t do that, so you’ll have to make that fix!  Note that unlike the real Wordle, we actually don’t check that the  user’s guess is a real word, so in that sense the game is perhaps a  little bit easier. All guesses in this game should be in **lowercase** characters, and it is acceptable for you to assume that the user will  not be so stubborn as to provide anything other than lowercase  characters when making a guess. Once a legitimate guess has been  obtained, it can be `return`ed.

1. Next, for the fourth `TODO`, we need to keep track of a user’s “score” in the game. We do this both  on a per-letter basis—by assigning a score of 2 (which we `#define`d as `EXACT`) to a letter in the correct place, 1 (which we `#define`d as `CLOSE`) to a letter that’s in the word but in the wrong place, or 0 (which we `#define`d as `WRONG`)—and a per-word basis, to help us detect when we’ve potentially triggered  the end of the game by winning. We’ll use the individual letter scores  when we color-code the printing. In order to store those scores, we need an array, which we’ve called `status`. At the start of the game, with no guesses having taken place, it should contain all 0s.

This is another good place to stop and test your code, particularly  as it pertains to item 3, above! You’ll notice that at this point, when  you finally enter a legitimate guess (that is to say, one that’s the  correct length), your program will likely look something like the below:

```bash
Input a 5-letter word: computer
Input a 5-letter word: games
Guess 1:
Input a 5-letter word:
```

That’s normal, though! Implementing `print_word` is `TODO` number 6, so we should not expect the program to do any processing of  that guess at this time. Of course, you can always add additional `printf` calls (just make sure to remove them before you submit) as part of your debugging strategy!

1. The fifth `TODO` is definitely the largest and probably most challenging. Inside of the `check_word` function, it’s up to you to compare each of the letters of the `guess` with each of the letters of the `choice` (which, recall, is the “secret word” for this game), and assign scores. If the letters match, award `EXACT` (2) points and `break` out of the loop—there’s no need to continue looping if you already  determined the letter is in the right spot. Technically, if that letter  appears in the word twice, this could result in a bit of a bug, but  fixing that bug overcomplicates this problem a bit more than we want to  now, so we’re going to accept that as a feature of our version! If you  find that the letter is in the word but not in the right spot, award `CLOSE` (1) points but don’t `break`! After all, that letter might later show up in the right spot in the `choice` word, and if we `break` too soon, the user would never know it! You don’t actually need to explicitly set `WRONG` (0) points here, since you handled that early in Step 4. Ultimately  though, you should also be summing up the total score of the word when  you know it, because that’s what this function is supposed to ultimately return. Again, don’t be afraid to use `debug50` and/or `printf`s as necessary in order to help you figure out what the values of different variables are at this point – until you implement `print_word`, below, the program won’t be offering you much in the way of a visual checkpoint!
2. For the sixth `TODO` you will complete the implementation of `print_word`. That function should look through the values you populated the `status` array with and print out, character by character, each letter of the `guess` with the correct color code. You may have noticed some (scary-looking!) `#define`s at the top of the file wherein we provide a simpler way of representing what’s called an [ANSI color code](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors), which is basically a command to change the font color of the terminal.  You don’t need to worry about how to implement those four values (`GREEN`, `YELLOW`, `RED`, and `RESET`, the latter of which simply returns to the terminal’s default font) or  exactly what they mean; instead, you can just use them (the power of  abstraction!). Note as well that we provide an example in the  distribution code up where we print some green text and then reset the  color, as part of the game’s introduction. Accordingly, you should feel  free to use the below line of code for inspiration as to how you might  try to toggle colors:

```c
printf(GREEN"This is WORDLE50"RESET"\n");
```

Of course, unlike our example, you probably don’t want to print a  newline after each character of the word (instead, you just want one  newline at the end, also resetting the font color!), lest it end up  looking like the below:

```bash
Input a 5-letter word: games
Guess 1: g
a
m
e
s
Input a 5-letter word:
```

1. Finally, the seventh `TODO` is just a bit of tidying up before the program terminates. Whether the main `for` loop has ended normally, by the user running out of guesses, or because we broke out of it by getting the word exactly right, it’s time to  report to the user on the game’s outcome. If the user did win the game, a simple `You won!` suffices to print here. Otherwise, you should print a message telling  the user what the target word was, so they know the game was being  honest with them (and so that you have a means to debug if you look back and realize your code was providing improper clues along the way!)



## [How to Test Your Code](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#how-to-test-your-code)

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/problems/2022/spring/wordle
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 wordle.c
```



## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#how-to-submit)

1. Download your `wordle.c` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 2: Wordle”.
4. Drag and drop your `wordle.c` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 2: Wordle submitted  successfully!” You may not see a score just yet, but if you see the  message then we’ve received your submission!



## [Acknowledgements](https://cs50.harvard.edu/college/2022/spring/psets/2/wordle50/#acknowledgements)

The word lists (`5.txt`, `6.txt`, `7.txt`, and `8.txt`) were generated from the data provided [here](https://github.com/first20hours/google-10000-english/), specifically from the `no-swears.txt` file.