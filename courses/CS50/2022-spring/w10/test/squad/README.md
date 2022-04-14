# [Harvard Pep Squad](https://cs50.harvard.edu/college/2022/spring/test/squad/#harvard-pep-squad)

[watch](https://www.youtube.com/embed/kOZohorpS3c?modestbranding=0&amp;rel=0&amp;showinfo=0)

Recall the [2004 Harvard–Yale prank](https://youtu.be/a2OXfFu4efs), in which the “Harvard Pep Squad” (i.e., Yale) tricked Harvard fans into spelling “GO HARVARD” with red placards, held above their heads like a  bitmap:

![GO HARVARD](https://cs50.harvard.edu/college/2022/spring/test/squad/bitmap.png)

Let’s simplify the prank a bit. Included in `squad/` are six `.txt` files, each of which contains a “bitmap” that’s 6 “pixels” tall and 6  “pixels” wide, wherein each “pixel” is implemented as a hash (`#`) or a space (` `). Assume that each hash represents a red placard and that each space represents a white placard.

1. (4 points.) Let’s determine how many red placards Yale should bring to Harvard for a given bitmap. In `squad/red.py`, write a program that prints the number of hashes therein. The program  should accept as its sole command-line argument the name of a `.txt` file. For instance, executing

   ```bash
    python red.py G.txt
   ```

   in `squad/` should print the number of hashes in `G.txt`. If the program is executed without a command-line argument or more than one command-line argument, it should exit with a status code of `1`.

1. (6 points.) Let’s give Harvard fans something to spell. In `squad/prank.py`, implement a program that prints, from left to right, the contents of  its command-line arguments, with one column of spaces between each  letter. No need for additional spaces between words. For instance,  executing

   ```bash
    python prank.py G.txt O.txt H.txt AR.txt VA.txt RD.txt
   ```

   in `squad/` should presumably print:

   ```bash
     ####   ####  #    #   ##   #####  #    #   ##   #####  #####
    #    # #    # #    #  #  #  #    # #    #  #  #  #    # #    #
    #      #    # ###### #    # #    # #    # #    # #    # #    #
    #  ### #    # #    # ###### #####  #    # ###### #####  #    #
    #    # #    # #    # #    # #   #   #  #  #    # #   #  #    #
     ####   ####  #    # #    # #    #   ##   #    # #    # #####
   ```

   Well, ideally.