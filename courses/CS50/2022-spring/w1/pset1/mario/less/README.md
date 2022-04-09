# [Mario](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#mario)



## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2021/fall/psets/1/mario-less.zip
```

followed by Enter in order to download a ZIP called `mario-less.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip mario-less.zip
```

to create a folder called `mario-less`. You no longer need the ZIP file, so you can execute

```bash
rm mario-less.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd mario-less
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
mario-less/ $
```

If all was successful, you should execute

```bash
ls
```

and see a file named `mario.c`. Executing `code mario.c` should open the file where you will type your code for this problem  set. If not, retrace your steps and see if you can determine where you  went wrong!

## [World 1-1](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#world-1-1)

Toward the end of World 1-1 in Nintendo’s Super Mario Brothers, Mario must ascend right-aligned pyramid of blocks, a la the below.

![screenshot of Mario jumping up a right-aligned pyramid](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/pyramid.png)

Let’s recreate that pyramid in C, albeit in text, using hashes (`#`) for bricks, a la the below. Each hash is a bit taller than it is wide,  so the pyramid itself is also be taller than it is wide.

```bash
       #
      ##
     ###
    ####
   #####
  ######
 #######
########
```

The program we’ll write will be called `mario`. And let’s allow the user to decide just how tall the pyramid should be  by first prompting them for a positive integer between, say, 1 and 8,  inclusive.

Here’s how the program might work if the user inputs `8` when prompted:

```bash
$ ./mario
Height: 8
       #
      ##
     ###
    ####
   #####
  ######
 #######
########
```

Here’s how the program might work if the user inputs `4` when prompted:

```bash
$ ./mario
Height: 4
   #
  ##
 ###
####
```

Here’s how the program might work if the user inputs `2` when prompted:

```bash
$ ./mario
Height: 2
 #
##
```

And here’s how the program might work if the user inputs `1` when prompted:

```bash
$ ./mario
Height: 1
#
```

If the user doesn’t, in fact, input a positive integer between 1 and  8, inclusive, when prompted, the program should re-prompt the user until they cooperate:

```bash
$ ./mario
Height: -1
Height: 0
Height: 42
Height: 50
Height: 4
   #
  ##
 ###
####
```

How to begin? Let’s approach this problem one step at a time.

## [Walkthrough](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#walkthrough)

[walkthrough](https://www.youtube.com/embed/NAs4FIWkJ4s?modestbranding=0&amp;rel=0&amp;showinfo=0)

## [Pseudocode](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#pseudocode)

First, execute

```bash
cd
```

to ensure you’re in your codespace’s default directory.

Then, execute

```bash
cd mario-less
```

to change to your `mario-less` directory.

Then, execute

```bash
code pseudocode.txt
```

to open the file called `pseudocode.txt` inside that directory.

Write in `pseudocode.txt` some pseudocode that implements this program, even if not (yet!) sure  how to write it in code. There’s no one right way to write pseudocode,  but short English sentences suffice. Recall how we wrote [pseudocode for finding someone in a phone book](https://docs.google.com/presentation/d/1X3AMSenwZGSE6WxGpzoALAfMg2hmh1LYIJp3N2a1EYI/edit#slide=id.g41907da2bc_0_265). Odds are your pseudocode will use (or imply using!) one or more  functions, conditionals, Boolean expressions, loops, and/or variables.

<details><summary>Spoiler</summary>

There’s more than one way to do this, so here’s just one!

- Prompt user for height
- If height is less than 1 or greater than 8 (or not an integer at all), go back one step
- Iterate from 1 through height:
  - On iteration i, print i hashes and then a newline

It’s okay to edit your own after seeing this pseudocode here, but don’t simply copy/paste ours into your own!

</details>



## [Prompting for Input](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#prompting-for-input)

Whatever your pseudocode, let’s first write only the C code that  prompts (and re-prompts, as needed) the user for input. Open the file  called `mario.c` inside of your `mario` directory. (Remember how?)

Now, modify `mario.c` in such a way that it prompts the user for the pyramid’s height,  storing their input in a variable, re-prompting the user again and again as needed if their input is not a positive integer between 1 and 8,  inclusive. Then, simply print the value of that variable, thereby  confirming (for yourself) that you’ve indeed stored the user’s input  successfully, a la the below.

```bash
$ ./mario
Height: -1
Height: 0
Height: 42
Height: 50
Height: 4
Stored: 4
```

<details><summary>Hints</summary>

- Recall that you can compile your program with make.
- Recall that you can print an int with printf using %i.
- Recall that you can get an integer from the user with get_int.
- Recall that get_int is declared in cs50.h.
- Recall that we prompted the user for a positive integer in lecture using a do while loop in `mario.c`.

</details>

## [Building the Opposite](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#building-the-opposite)

Now that your program is (hopefully!) accepting input as prescribed, it’s time for another step.

It turns out it’s a bit easier to build a left-aligned pyramid than right-, a la the below.

```bash
#
##
###
####
#####
######
#######
########
```

So let’s build a left-aligned pyramid first and then, once that’s working, right-align it instead!

Modify `mario.c` at right such that it no longer simply prints the user’s input but instead prints a left-aligned pyramid of that height.

<details><summary>Hints</summary>

- Keep in mind that a hash is just a character like any other, so you can print it with `printf`.
- Just as Scratch has a repeat block, so does C have a for loop, via which you can iterate some number times. Perhaps on each iteration, i, you could print that many hashes?
- You can actually “nest” loops, iterating with one variable (e.g., i) in the “outer” loop and another (e.g., j) in the “inner” loop. For instance, here’s how you might print a square of height and width n, below. Of course, it’s not a square that you want to print!

```c
  for (int i = 0; i < n; i++)
  {
      for (int j = 0; j < n; j++)
      {
          printf("#");
      }
      printf("\n");
  }
```

</details>

## [Right-Aligning with Dots](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#right-aligning-with-dots)

Let’s now right-align that pyramid by pushing its hashes to the right by prefixing them with dots (i.e., periods), a la the below.

```bash
.......#
......##
.....###
....####
...#####
..######
.#######
########
```

Modify `mario.c` in such a way that it does exactly that!

<details><summary>Hint</summary>

Notice how the number of dots needed on each line is the “opposite” of the number of that line’s hashes. For a pyramid of height 8, like the above, the first line has but 1 hash and thus 7 dots. The bottom line, meanwhile, has 8 hashes and thus 0 dots. Via what formula (or arithmetic, really) could you print that many dots?

</details>

### [How to Test Your Code](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#how-to-test-your-code)

Does your code work as prescribed when you input

- `-1` (or other negative numbers)?
- `0`?
- `1` through `8`?
- `9` or other positive numbers?
- letters or words?
- no input at all, when you only hit Enter?

## [Removing the Dots](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#removing-the-dots)

All that remains now is a finishing flourish! Modify `mario.c` in such a way that it prints spaces instead of those dots!

### [How to Test Your Code](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#how-to-test-your-code-1)

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/problems/2022/spring/mario/less
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 mario.c
```

<details><summary>Hint</summary>

A space is just a press of your space bar, just as a period is just a press of its key! Just remember that printf requires that you surround both with double quotes!

</details>

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/less/#how-to-submit)

1. Download your `mario.c` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 1: Mario (Less)”.
4. Drag and drop your `mario.c` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 1: Mario (Less)  submitted successfully!” You may not see a score just yet, but if you  see the message then we’ve received your submission!