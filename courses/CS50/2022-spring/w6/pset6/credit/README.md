# [Credit](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#credit)

Implement a program that determines whether a provided credit card number is valid according to Luhn’s algorithm.

```bash
$ python credit.py
Number: 378282246310005
AMEX
```

## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#getting-started)

Log into [code.cs50.io](https://code.cs50.io/), click on your terminal window, and execute `cd` by itself. You should find that your terminal window’s prompt resembles the below:

```bash
$
```

Next execute

```bash
wget https://cdn.cs50.net/2021/fall/psets/6/sentimental-credit.zip
```

in order to download a ZIP called `sentimental-credit.zip` into your codespace.

Then execute

```bash
unzip sentimental-credit.zip
```

to create a folder called `sentimental-credit`. You no longer need the ZIP file, so you can execute

```bash
rm sentimental-credit.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd sentimental-credit
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
sentimental-credit/ $
```

Execute `ls` by itself, and you should see `credit.py`. If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Specification](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#specification)

- In `credit.py`, write a program that prompts the user for a credit card number and then reports (via `print`) whether it is a valid American Express, MasterCard, or Visa card number, exactly as you did in [Problem Set 1](https://cs50.harvard.edu/college/2022/spring/psets/1/), except that your program this time should be written in Python.
- So that we can automate some tests of your code, we ask that your program’s last line of output be `AMEX\n` or `MASTERCARD\n` or `VISA\n` or `INVALID\n`, nothing more, nothing less.
- For simplicity, you may assume that the user’s input will be entirely  numeric (i.e., devoid of hyphens, as might be printed on an actual  card).
- Best to use `get_int` or `get_string` from CS50’s library to get users’ input, depending on how you to decide to implement this one.

## [Usage](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#usage)

Your program should behave per the example below.

```bash
$ python credit.py
Number: 378282246310005
AMEX
```

## [Hints](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#hints)

- It’s possible to use regular expressions to validate user input. You might use Python’s [`re`](https://docs.python.org/3/library/re.html) module, for example, to check whether the user’s input is indeed a sequence of digits of the correct length.

## [Testing](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#testing)

While `check50` is available for this problem, you’re encouraged to first test your code on your own for each of the following.

- Run your program as `python credit.py`, and wait for a prompt for input. Type in `378282246310005` and press enter. Your program should output `AMEX`.
- Run your program as `python credit.py`, and wait for a prompt for input. Type in `371449635398431` and press enter. Your program should output `AMEX`.
- Run your program as `python credit.py`, and wait for a prompt for input. Type in `5555555555554444` and press enter. Your program should output `MASTERCARD`.
- Run your program as `python credit.py`, and wait for a prompt for input. Type in `5105105105105100` and press enter. Your program should output `MASTERCARD`.
- Run your program as `python credit.py`, and wait for a prompt for input. Type in `4111111111111111` and press enter. Your program should output `VISA`.
- Run your program as `python credit.py`, and wait for a prompt for input. Type in `4012888888881881` and press enter. Your program should output `VISA`.
- Run your program as `python credit.py`, and wait for a prompt for input. Type in `1234567890` and press enter. Your program should output `INVALID`.

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/problems/2022/spring/sentimental/credit
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 credit.py
```

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/6/credit/#how-to-submit)

1. Download your `credit.py` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 6: Sentimental (Credit)”.
4. Drag and drop your `credit.py` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 6: Sentimental  (Credit) submitted successfully!” You may not see a score just yet, but  if you see the message then we’ve received your submission!