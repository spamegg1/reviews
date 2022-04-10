# [Hello](https://cs50.harvard.edu/college/2022/spring/psets/6/hello/#hello)

Implement a program that prints out a simple greeting to the user, per the below.

```bash
$ python hello.py
What is your name?
David
hello, David
```

## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/6/hello/#getting-started)

Log into [code.cs50.io](https://code.cs50.io/), click on your terminal window, and execute `cd` by itself. You should find that your terminal window’s prompt resembles the below:

```bash
$
```

Next execute

```bash
wget https://cdn.cs50.net/2021/fall/psets/6/sentimental-hello.zip
```

in order to download a ZIP called `sentimental-hello.zip` into your codespace.

Then execute

```bash
unzip sentimental-hello.zip
```

to create a folder called `sentimental-hello`. You no longer need the ZIP file, so you can execute

```bash
rm sentimental-hello.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd sentimental-hello
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
sentimental-hello/ $
```

Execute `ls` by itself, and you should see `hello.py`. If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Specification](https://cs50.harvard.edu/college/2022/spring/psets/6/hello/#specification)

Write, in a file called `hello.py`, a program that prompts a user for their name, and then prints `hello, so-and-so`, where `so-and-so` is their provided name, exactly as you did in [Problem Set 1](https://cs50.harvard.edu/college/2022/spring/psets/1/), except that your program this time should be written in Python.

## [Usage](https://cs50.harvard.edu/college/2022/spring/psets/6/hello/#usage)

Your program should behave per the example below.

```bash
$ python hello.py
What is your name?
Emma
hello, Emma
```

## [Testing](https://cs50.harvard.edu/college/2022/spring/psets/6/hello/#testing)

While `check50` is available for this problem, you’re encouraged to first test your code on your own for each of the following.

- Run your program as `python hello.py`, and wait for a prompt for input. Type in `David` and press enter. Your program should output `hello, David`.
- Run your program as `python hello.py`, and wait for a prompt for input. Type in `Bernie` and press enter. Your program should output `hello, Bernie`.
- Run your program as `python hello.py`, and wait for a prompt for input. Type in `Carter` and press enter. Your program should output `hello, Carter`.

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/problems/2022/spring/sentimental/hello
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 hello.py
```

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/6/hello/#how-to-submit)

1. Download your `hello.py` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 6: Sentimental (Hello)”.
4. Drag and drop your `hello.py` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 6: Sentimental  (Hello) submitted successfully!” You may not see a score just yet, but  if you see the message then we’ve received your submission!