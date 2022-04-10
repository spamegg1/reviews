[Lab 5: Inheritance](https://cs50.harvard.edu/college/2022/spring/labs/5/#lab-5-inheritance)

Labs are practice problems which, time permitting, may be started or  completed in your section, and are assessed on correctness only. You are encouraged to collaborate with classmates on this lab, though each  member in a group collaborating is expected to contribute equally to the lab.

Simulate the inheritance of blood types for each member of a family.

```bash
$ ./inheritance
Child (Generation 0): blood type OO
    Parent (Generation 1): blood type AO
        Grandparent (Generation 2): blood type OA
        Grandparent (Generation 2): blood type BO
    Parent (Generation 1): blood type OB
        Grandparent (Generation 2): blood type AO
        Grandparent (Generation 2): blood type BO
```

## [Background](https://cs50.harvard.edu/college/2022/spring/labs/5/#background)

A person’s blood type is determined by two alleles (i.e., different  forms of a gene). The three possible alleles are A, B, and O, of which  each person has two (possibly the same, possibly different). Each of a  child’s parents randomly passes one of their two blood type alleles to  their child. The possible blood type combinations, then, are: OO, OA,  OB, AO, AA, AB, BO, BA, and BB.

For example, if one parent has blood type AO and the other parent has blood type BB, then the child’s possible blood types would be AB and  OB, depending on which allele is received from each parent. Similarly,  if one parent has blood type AO and the other OB, then the child’s  possible blood types would be AO, OB, AB, and OO.

## [Getting Started](https://cs50.harvard.edu/college/2022/spring/labs/5/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2021/fall/labs/5/inheritance.zip
```

followed by Enter in order to download a ZIP called `inheritance.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip inheritance.zip
```

to create a folder called `inheritance`. You no longer need the ZIP file, so you can execute

```bash
rm inheritance.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd inheritance
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
inheritance/ $
```

If all was successful, you should execute

```bash
ls
```

and you should see `inheritance.c`.

If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Understanding](https://cs50.harvard.edu/college/2022/spring/labs/5/#understanding)

Take a look at the distribution code in `inheritance.c`.

Notice the definition of a type called `person`. Each person has an array of two `parents`, each of which is a pointer to another `person` struct. Each person also has an array of two `alleles`, each of which is a `char` (either `'A'`, `'B'`, or `'O'`).

Now, take a look at the `main` function. The function begins by “seeding” (i.e., providing some  initial input to) a random number generator, which we’ll use later to  generate random alleles. The `main` function then calls the `create_family` function to simulate the creation of `person` structs for a family of 3 generations (i.e. a person, their parents, and their grandparents). We then call `print_family` to print out each of those family members and their blood types. Finally, the function calls `free_family` to `free` any memory that was previously allocated with `malloc`.

The `create_family` and `free_family` functions are left to you to write!

## [Implementation Details](https://cs50.harvard.edu/college/2022/spring/labs/5/#implementation-details)

Complete the implementation of `inheritance.c`, such that it creates a family of a specified generation size and  assigns blood type alleles to each family member. The oldest generation  will have alleles assigned randomly to them.

- The 

  ```c
  create_family
  ```

   function takes an integer (

  ```c
  generations
  ```

  ) as input and should allocate (as via 

  ```c
  malloc
  ```

  ) one 

  ```c
  person
  ```

   for each member of the family of that number of generations, returning a pointer to the 

  ```c
  person
  ```

   in the youngest generation.    

  - For example, `create_family(3)` should return a pointer to a person with two parents, where each parent also has two parents.
  - Each `person` should have `alleles` assigned to them. The oldest generation should have alleles randomly chosen (as by calling the `random_allele` function), and younger generations should inherit one allele (chosen at random) from each parent.
  - Each `person` should have `parents` assigned to them. The oldest generation should have both `parents` set to `NULL`, and younger generations should have `parents` be an array of two pointers, each pointing to a different parent.

We’ve divided the `create_family` function into a few `TODO`s for you to complete.

- First, you should allocate memory for a new person. Recall that you can use `malloc` to allocate memory, and `sizeof(person)` to get the number of bytes to allocate.

- Next, we’ve included a condition to check if 

  ```c
  generations > 1
  ```

  .    

  - If `generations > 1`, then there are more generations that still need to be allocated. We’ve already created two new `parents`, `parent0` and `parent1`, by recursively calling `create_family`. Your `create_family` function should then set the parent pointers of the new person you created. Finally, assign both `alleles` for the new person by randomly choosing one allele from each parent.
  - Otherwise (if `generations == 1`), then there will be no parent data for this person. Both `parents` of your new person should be set to `NULL`, and each `allele` should be generated randomly.

- Finally, your function should return a pointer for the `person` that was allocated.

The `free_family` function should accept as input a pointer to a `person`, free memory for that person, and then recursively free memory for all of their ancestors.

- Since this is a recursive function, you should first handle the base case. If the input to the function is `NULL`, then there’s nothing to free, so your function can return immediately.
- Otherwise, you should recursively `free` both of the person’s parents before `free`ing the child.

### [Walkthrough](https://cs50.harvard.edu/college/2022/spring/labs/5/#walkthrough)

This video was recorded when the course was still using CS50 IDE for writing code. Though the interface may look different from your codespace, the  behavior of the two environments should be largely similar!

[walkthrough](https://video.cs50.io/9p7ddI3ozTY)

### [Hints](https://cs50.harvard.edu/college/2022/spring/labs/5/#hints)

- You might find the 

  ```c
rand()
  ```
  
   function useful for randomly assigning alleles. This function returns an integer between 

  ```c
0
  ```
  
   and 

  ```c
RAND_MAX
  ```
  
  , or 

  ```c
32767
  ```
  
  .    

  - In particular, to generate a pseudorandom number that is either `0` or `1`, you can use the expression `rand() % 2`.

- Remember, to allocate memory for a particular person, we can use `malloc(n)`, which takes a size as argument and will allocate `n` bytes of memory.

- Remember, to access a variable via a pointer, we can use arrow notation.    

  - For example, if `p` is a pointer to a person, then a pointer to this person’s first parent can be accessed by `p->parents[0]`.

<details><summary>Not sure how to solve?</summary>

[help](https://youtu.be/H7LULatPwcQ)

</details>



### [How to Test Your Code](https://cs50.harvard.edu/college/2022/spring/labs/5/#how-to-test-your-code)

Upon running `./inheritance`, your program should adhere to the rules described in the background.  The child should have two alleles, one from each parent. The parents  should each have two alleles, one from each of their parents.

For example, in the example below, the child in Generation 0 received an O allele from both Generation 1 parents. The first parent received  an A from the first grandparent and a O from the second grandparent.  Similarly, the second parent received an O and a B from their  grandparents.

```bash
$ ./inheritance
Child (Generation 0): blood type OO
    Parent (Generation 1): blood type AO
        Grandparent (Generation 2): blood type OA
        Grandparent (Generation 2): blood type BO
    Parent (Generation 1): blood type OB
        Grandparent (Generation 2): blood type AO
        Grandparent (Generation 2): blood type BO
```

Execute the below to evaluate the correctness of your code using `check50`. But be sure to compile and test it yourself as well!

```bash
check50 cs50/labs/2022/spring/inheritance
```

Execute the below to evaluate the style of your code using `style50`.

```bash
style50 inheritance.c
```

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/labs/5/#how-to-submit)

1. Download your `inheritance.c` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Lab 5: Inheritance”.
4. Drag and drop your `inheritance.c` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Lab 5: Inheritance submitted successfully!”