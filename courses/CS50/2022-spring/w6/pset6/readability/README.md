# [Readability](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#readability)

Implement a program that computes the approximate grade level needed to comprehend some text, per the below.

```bash
$ python readability.py
Text: Congratulations! Today is your day. You're off to Great Places! You're off and away!
Grade 3
```

## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#getting-started)

Log into [code.cs50.io](https://code.cs50.io/), click on your terminal window, and execute `cd` by itself. You should find that your terminal window’s prompt resembles the below:

```bash
$
```

Next execute

```bash
wget https://cdn.cs50.net/2021/fall/psets/6/sentimental-readability.zip
```

in order to download a ZIP called `sentimental-readability.zip` into your codespace.

Then execute

```bash
unzip sentimental-readability.zip
```

to create a folder called `sentimental-readability`. You no longer need the ZIP file, so you can execute

```bash
rm sentimental-readability.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd sentimental-readability
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
sentimental-readability/ $
```

Execute `ls` by itself, and you should see `readability.py`. If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Specification](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#specification)

- Write, in a file called 

  ```bash
  readability.py
  ```

  , a program that first asks the user to type in some text, and then  outputs the grade level for the text, according to the Coleman-Liau  formula, exactly as you did in Problem Set 2, except that your program this time should be written in Python.    

  - Recall that the Coleman-Liau index is computed as `0.0588 * L - 0.296 * S - 15.8`, where `L` is the average number of letters per 100 words in the text, and `S` is the average number of sentences per 100 words in the text.

- Use `get_string` from the CS50 Library to get the user’s input, and `print` to output your answer.

- Your program should count the number of letters, words, and sentences in the text. You may assume that a letter is any lowercase character from `a` to `z` or any uppercase character from `A` to `Z`, any sequence of characters separated by spaces should count as a word,  and that any occurrence of a period, exclamation point, or question mark indicates the end of a sentence.

- Your program should print as output `"Grade X"` where `X` is the grade level computed by the Coleman-Liau formula, rounded to the nearest integer.

- If the resulting index number is 16 or higher (equivalent to or greater  than a senior undergraduate reading level), your program should output `"Grade 16+"` instead of giving the exact index number. If the index number is less than 1, your program should output `"Before Grade 1"`.

## [Usage](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#usage)

Your program should behave per the example below.

```bash
$ python readability.py
Text: Congratulations! Today is your day. You're off to Great Places! You're off and away!
Grade 3
```

## [Hints](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#hints)

- You might find the [`ord`](https://docs.python.org/3/library/functions.html?highlight=ord#ord) and [`chr`](https://docs.python.org/3/library/functions.html#chr) functions useful for this problem!

## [Testing](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#testing)

While `check50` is available for this problem, you’re encouraged to first test your code on your own for each of the following.

- Run your program as `python readability.py`, and wait for a prompt for input. Type in `One fish. Two fish. Red fish. Blue fish.` and press enter. Your program should output `Before Grade 1`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `Would you like them here or there? I would not like them here or there. I would not like them anywhere.` and press enter. Your program should output `Grade 2`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `Congratulations! Today is your day. You're off to Great Places! You're off and away!` and press enter. Your program should output `Grade 3`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `Harry Potter was a highly unusual boy in many ways. For one thing, he hated  the summer holidays more than any other time of year. For another, he  really wanted to do his homework, but was forced to do it in secret, in  the dead of the night. And he also happened to be a wizard.` and press enter. Your program should output `Grade 5`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `In my younger and more vulnerable years my father gave me some advice that I've been turning over in my mind ever since.` and press enter. Your program should output `Grade 7`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `Alice was beginning to get very tired of sitting by her sister on the bank,  and of having nothing to do: once or twice she had peeped into the book  her sister was reading, but it had no pictures or conversations in it,  "and what is the use of a book," thought Alice "without pictures or  conversation?"` and press enter. Your program should output `Grade 8`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `When he was nearly thirteen, my brother Jem got his arm badly broken at the  elbow. When it healed, and Jem's fears of never being able to play  football were assuaged, he was seldom self-conscious about his injury.  His left arm was somewhat shorter than his right; when he stood or  walked, the back of his hand was at right angles to his body, his thumb  parallel to his thigh.` and press enter. Your program should output `Grade 8`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `There are more things in Heaven and Earth, Horatio, than are dreamt of in your philosophy.` and press enter. Your program should output `Grade 9`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `It was a bright cold day in April, and the clocks were striking thirteen.  Winston Smith, his chin nuzzled into his breast in an effort to escape  the vile wind, slipped quickly through the glass doors of Victory  Mansions, though not quickly enough to prevent a swirl of gritty dust  from entering along with him.` and press enter. Your program should output `Grade 10`.
- Run your program as `python readability.py`, and wait for a prompt for input. Type in `A large class of computational problems involve the determination of  properties of graphs, digraphs, integers, arrays of integers, finite  families of finite sets, boolean formulas and elements of other  countable domains.` and press enter. Your program should output `Grade 16+`.

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/problems/2022/spring/sentimental/readability
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 readability.py
```

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/6/readability/#how-to-submit)

1. Download your `readability.py` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 6: Sentimental (Readability)”.
4. Drag and drop your `readability.py` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 6: Sentimental  (Readability) submitted successfully!” You may not see a score just yet, but if you see the message then we’ve received your submission!