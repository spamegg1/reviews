# [Mario](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/#mario)



## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2021/fall/psets/1/mario-more.zip
```

followed by Enter in order to download a ZIP called `mario-more.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip mario-more.zip
```

to create a folder called `mario-more`. You no longer need the ZIP file, so you can execute

```bash
rm mario-more.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd mario-more
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
mario-more/ $
```

If all was successful, you should execute

```bash
ls
```

and see a file named `mario.c`. Executing `code mario.c` should open the file where you will type your code for this problem  set. If not, retrace your steps and see if you can determine where you  went wrong!

## [World 1-1](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/#world-1-1)

Toward the beginning of World 1-1 in Nintendo’s Super Mario Brothers, Mario must hop over adjacent pyramids of blocks, per the below.

![screenshot of Mario jumping over adjacent pyramids](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/pyramids.png)

Let’s recreate those pyramids in C, albeit in text, using hashes (`#`) for bricks, a la the below. Each hash is a bit taller than it is wide,  so the pyramids themselves are also be taller than they are wide.

```bash
   #  #
  ##  ##
 ###  ###
####  ####
```

The program we’ll write will be called `mario`. And let’s allow the user to decide just how tall the pyramids should be by first prompting them for a positive integer between, say, 1 and 8,  inclusive.

Here’s how the program might work if the user inputs `8` when prompted:

```bash
$ ./mario
Height: 8
       #  #
      ##  ##
     ###  ###
    ####  ####
   #####  #####
  ######  ######
 #######  #######
########  ########
```

Here’s how the program might work if the user inputs `4` when prompted:

```bash
$ ./mario
Height: 4
   #  #
  ##  ##
 ###  ###
####  ####
```

Here’s how the program might work if the user inputs `2` when prompted:

```bash
$ ./mario
Height: 2
 #  #
##  ##
```

And here’s how the program might work if the user inputs `1` when prompted:

```bash
$ ./mario
Height: 1
#  #
```

If the user doesn’t, in fact, input a positive integer between 1 and  8, inclusive, when prompted, the program should re-prompt the user until they cooperate:

```bash
$ ./mario
Height: -1
Height: 0
Height: 42
Height: 50
Height: 4
   #  #
  ##  ##
 ###  ###
####  ####
```

Notice that width of the “gap” between adjacent pyramids is equal to  the width of two hashes, irrespective of the pyramids’ heights.

Open your `mario.c` file to implement this problem as described!

### [Walkthrough](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/#walkthrough)

[walkthrough](https://www.youtube.com/embed/FzN9RAjYG_Q?modestbranding=0&amp;rel=0&amp;showinfo=0)

### [How to Test Your Code](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/#how-to-test-your-code)

Does your code work as prescribed when you input

- `-1` (or other negative numbers)?
- `0`?
- `1` through `8`?
- `9` or other positive numbers?
- letters or words?
- no input at all, when you only hit Enter?

You can also execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/problems/2022/spring/mario/more
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 mario.c
```

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/1/mario/more/#how-to-submit)

1. Download your `mario.c` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 1: Mario (More)”.
4. Drag and drop your `mario.c` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 1: Mario (More)  submitted successfully!” You may not see a score just yet, but if you  see the message then we’ve received your submission!