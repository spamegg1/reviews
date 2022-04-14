# [Emojicode](https://cs50.harvard.edu/college/2022/spring/test/emojicode/#emojicode)

If unable to view some emoji below, your operating system might not be the latest. Rather than update your operating system just for this test,  try viewing this page on your phone or another computer instead, albeit  in accordance with the course’s policy on academic honesty.

Odds are you’ll have to teach yourself a new programming language  some day, perhaps even installing some software for it. Let’s prepare  you for that day!

[Emojicode](https://www.emojicode.org/) is “an  open-source, full-blown programming language consisting of emojis.”  Here, for instance, is a now-familiar program, implemented in Emojicode:

```emojicode
🏁 🍇
  😀 🔤hello, world🔤❗️
🍉
```



## [Installing](https://cs50.harvard.edu/college/2022/spring/test/emojicode/#installing)

In order to compile that program from Emojicode to machine code, you’ll need a compiler, `emojicodec`. Here’s how to install it [VS Code](https://code.cs50.io/):

1. Install some “dependencies” that Emojicode’s compiler requires:

   ```bash
   sudo apt install -y libncurses5 rsync
   ```

2. Download a “gzipped tarball” (which is similar to a Zip file) of Emojicode’s installer:

   ```bash
   wget https://github.com/emojicode/emojicode/releases/download/v1.0-beta.2/Emojicode-1.0-beta.2-Linux-x86_64.tar.gz
   ```

3. Decompress that gzipped tarball:

   ```bash
    tar -xzf Emojicode-1.0-beta.2-Linux-x86_64.tar.gz 
   ```

4. Change into the decompressed directory:

   ```bash
    cd Emojicode-1.0-beta.2-Linux-x86_64
   ```

5. Run the installer therein:

   ```bash
    sudo ./install.sh
   ```

   And answer `y` to any questions when prompted.

If you then run

```bash
emojicodec
```

without any command-line arguments, you should see:

```bash
👉  Option 'file' is required
```

If not, best to retry these steps!



## [Compiling](https://cs50.harvard.edu/college/2022/spring/test/emojicode/#compiling)

Execute

```bash
code hello.🍇
```

to create a new file called `hello.🍇`. (You’ll probably find it easier to copy/paste than type manually.) That’s right, Emojicode uses a file extension of `.🍇`!

Next, copy/paste this now-familiar program into the file:

```emojicode
🏁 🍇
  😀 🔤hello, world🔤 ❗️
🍉
```

Then compile the program with:

```bash
emojicodec hello.🍇
```

And run it with:

```bash
./hello
```

You should see a familiar greeting. If not, best to retry these steps!



## [Understanding](https://cs50.harvard.edu/college/2022/spring/test/emojicode/#understanding)

To understand this new language, start by reading Emojicode’s [guide](https://www.emojicode.org/docs/guides/compile-and-run.html). Then answer each of the below.

1. (1 point.) What is 🏁 similar to in C?

**ANSWER:** it’s similar to the `main` function in C, which serves as the entry point into the program.

2. (1 point.) What are 🍇 and 🍉 similar to in C?

**ANSWER:** Similar to `{` and `}` in C. Beginning and end of code blocks.

3. (1 point.) What is 😀 similar to in C?

**ANSWER** It’s similar to `printf` in C.

4. (1 point.) What are 🔤 and 🔤 similar to in C?

**ANSWER** Similar to double quotation marks in C that mark the beginning/end of a string.

5. (1 point.) What is ❗️ similar to in C?

**ANSWER** Similar to a right parenthesis, maybe? Basically it ends the parameter list of a function.

------

Keeping Emojicode’s [guide](https://www.emojicode.org/docs/guides/compile-and-run.html) still in mind, consider the program below. You might find it helpful to copy/paste it, too, into a `.🍇` file so that you can compile and run it a few times.

```
🏁 🍇
  🍿 1 2 3 4 5 6 7 8 9 10 🍆 ➡️ 🖍️🆕 numbers
  🐹 numbers ❗️
  😀 🔡 🐽 numbers 0 ❗️ ❗️ ❗️
🍉
```

6. (2 points.) In `emojicode/6.py`, re-implement the program in Python.

**ANSWER** This program prints a random integer from 1 to 10 inclusive.

```python
from random import randint
print(randint(1, 10))
```



------

Let’s now stray a bit from the guide. Consider the program below. You might find it helpful to copy/paste it, too, into a `.🍇` file so that you can compile and run it. You might also find it helpful to skim Emojicode’s [package index](https://www.emojicode.org/docs/packages/s/1f521.html) for strings.

```emojicode
🏁 🍇
  👄 🔤🤷 🔤❗️
  🆕 🔡▶️👂🏼 ❗️ ➡️ s
  👄 🔤👋 🔤 ❗️
  😀 s ❗️
🍉
```

7. (2 points.) In `emojicode/7.py`, re-implement the program in Python.

**ANSWER** This program takes user input and prints back a “hello, user” kind of message.

```python
x = input("🤷 ")
print("👋", x)
```



------

Finally, consider the program below. You might find it helpful to copy/paste it, too, into a `.🍇` file so that you can compile and run it. You might also find it helpful to skim Emojicode’s [language reference](https://www.emojicode.org/docs/reference/controlflow.html) for control flow.

```
🏁 🍇
  🔂 i 🆕⏩ 10 0 -1 ❗️ 🍇
    😀 🔡i❗️ ❗️
  🍉
🍉
```

8. (2 points.) In `emojicode/8.py`, re-implement the program in Python.

**ANSWER** This program prints 1, …, 10 in reverse order, each on a separate line.

```python
for i in range(10, 0, -1):
    print(i)
```

