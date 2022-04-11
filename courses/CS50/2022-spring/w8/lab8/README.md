# [Lab 8: Trivia](https://cs50.harvard.edu/college/2022/spring/labs/8/#lab-8-trivia)

Labs are practice problems which, time permitting, may be started or  completed in your section, and are assessed on correctness only. You are encouraged to collaborate with classmates on this lab, though each  member in a group collaborating is expected to contribute equally to the lab.

Write a webpage that lets users answer trivia questions.

![screenshot of trivia questions](https://cs50.harvard.edu/college/2022/spring/labs/8/questions.png)



## [Getting Started](https://cs50.harvard.edu/college/2022/spring/labs/8/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2021/fall/labs/8/trivia.zip
```

followed by Enter in order to download a ZIP called `trivia.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip trivia.zip
```

to create a folder called `trivia`. You no longer need the ZIP file, so you can execute

```bash
rm trivia.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd trivia
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
trivia/ $
```

If all was successful, you should execute

```bash
ls
```

and you should see an `index.html` file and a `styles.css` file.

If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Implementation Details](https://cs50.harvard.edu/college/2022/spring/labs/8/#implementation-details)

Design a webpage using HTML, CSS, and JavaScript to let users answer trivia questions.

- In 

  ```bash
  index.html
  ```

  , add beneath “Part 1” a multiple-choice trivia question of your choosing with HTML.    

  - You should use an `h3` heading for the text of your question.
  - You should have one `button` for each of the possible answer choices. There should be at least three answer choices, of which exactly one should be correct.

- Using JavaScript, add logic so that the buttons change colors when a user clicks on them.    

  - If a user clicks on a button with an incorrect answer, the button should  turn red and text should appear beneath the question that says  “Incorrect”.
  - If a user clicks on a button with the correct answer, the button should  turn green and text should appear beneath the question that says  “Correct!”.

- In 

  ```bash
  index.html
  ```

  , add beneath “Part 2” a text-based free response question of your choosing with HTML.    

  - You should use an `h3` heading for the text of your question.
  - You should use an `input` field to let the user type a response.
  - You should use a `button` to let the user confirm their answer.

- Using JavaScript, add logic so that the text field changes color when a user confirms their answer.    

  - If the user types an incorrect answer and presses the confirmation button, the text field should turn red and text should appear beneath the  question that says “Incorrect”.
  - If the user types the correct answer and presses the confirmation button,  the input field should turn green and text should appear beneath the  question that says “Correct!”.

Optionally, you may also:

- Edit `styles.css` to change the CSS of your webpage!
- Add additional trivia questions to your trivia quiz if you would like!

### [Walkthrough](https://cs50.harvard.edu/college/2022/spring/labs/8/#walkthrough)

This video was recorded when the course was still using CS50 IDE for writing code. Though the interface may look different from your codespace, the  behavior of the two environments should be largely similar!

[walkthrough](https://video.cs50.io/WGd0Jx7rxUo)

### [Hints](https://cs50.harvard.edu/college/2022/spring/labs/8/#hints)

- Use [`document.querySelector`](https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelector) to query for a single HTML element.
- Use [`document.querySelectorAll`](https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll) to query for multiple HTML elements that match a query. The function returns an array of all matching elements.

<details><summary>Not sure how to solve?</summary>

[watch](https://video.cs50.io/FLlI7rSSV_M)

</details>



### [Testing](https://cs50.harvard.edu/college/2022/spring/labs/8/#testing)

No `check50`  for this lab, as implementations will vary based on your questions! But  be sure to test both incorrect and correct responses for each of your  questions to ensure that your webpage responds appropriately.

Run `http-server` in your terminal while in your `lab8` directory to start a web server that serves your webpage.

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/labs/8/#how-to-submit)

1. Download your `index.html` and `styles.css` files by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Lab 8: Trivia”.
4. Drag and drop your `index.html` and `styles.css` files to the area that says “Drag & Drop”. Be sure they have those **exact** filenames! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filenames is your responsibility!
5. Click “Upload”.

You should see a message that says “Lab 8: Trivia submitted successfully!”