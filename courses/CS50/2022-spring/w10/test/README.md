# [Test](https://cs50.harvard.edu/college/2022/spring/test/#test)

Looking for information [about the test](https://cs50.harvard.edu/college/2022/spring/test/about/) itself, including past problems, the [review sessions](https://cs50.harvard.edu/college/2022/spring/test/about/#review-sessions), and advice?

This test is open-book: you may use any and all non-human resources during the test, but the **only** humans to whom you may turn for help or from whom you may receive help are the course’s heads, which means that

**you may**

- browse and search the internet,
- review books,
- review questions and answers already posted on Ed,
- review the course’s own materials,
- use VS Code, and/or
- email the course’s heads at [heads@cs50.harvard.edu](mailto:heads@cs50.harvard.edu) with questions, but

**you may not**

- provide help to anyone, and/or
- receive or solicit help from anyone other than the course’s heads.

Take care to review the course’s [policy on academic honesty](https://cs50.harvard.edu/college/2022/spring/syllabus/#academic-honesty) in its entirety. Note particularly, but not only, that

- looking at another individual’s work during the test is *not reasonable* and
- turning to humans (besides the course’s heads) for help or receiving help from  humans (besides the course’s heads) during the test is *not reasonable*.

Unless otherwise noted, you may call any functions we’ve encountered  this term in code that you write. You needn’t comment code that you  write, but comments may help in cases of partial credit. If having  difficulty with short-answer snippets of code (such as those in Checking Speller or XCheck), you may resort to pseudocode for potential partial  credit.

Among the test’s aims is to assess your newfound comfort with the  course’s material and your ability to apply the course’s lessons to  familiar and unfamiliar problems. **And most problems aspire to teach something new.** Be sure to click on (and learn from) any links or videos included in problems.

You may resubmit as many times as you would like before the test’s  deadline. Late submissions will incur a 0.2% penalty per minute, per [the course’s syllabus](https://cs50.harvard.edu/college/2022/spring/syllabus/#lateness). We strongly encourage you not to wait until the last minute,  particularly as the response document will require you to assign pages  to questions in Gradescope’s interface, which will take a few minutes  before your submission is complete.

## [When To Do It](https://cs50.harvard.edu/college/2022/spring/test/#when-to-do-it)

By Mon, Apr 18, 6:59 AM.

## [What To Do](https://cs50.harvard.edu/college/2022/spring/test/#what-to-do)

1. Log into [code.cs50.io](https://code.cs50.io) using your GitHub account

2. Run `update50` in your codespace’s terminal window to ensure your codespace is up-to-date and, when prompted, click **Rebuild now**

3. In VS Code, execute

   ```bash
    wget https://cdn.cs50.net/2022/spring/test/test.zip
   ```

   to download the distribution code for the test. Execute `unzip test.zip` to create a folder named `test` that contains three folders (`emojicode`, `squad`, and `wheels`). Each of these folders contains files or distribution code relevant to those questions.

4. Open the test’s [response document](https://vault.cs50.io/f5925249-44c2-4ede-b948-30c8b00ecebf) in Google Docs.

5. Make a copy of the response document in your own Google account by choosing **File > Make a Copy**.

6. Solve all of the [problems](https://cs50.harvard.edu/college/2022/spring/test/#problems) below, in any order you’d like, by writing your answers in the response document, replacing each **TODO** with an answer. Some questions will instead ask you to complete a **TODO** in one of the files in your `test` directory within VS Code.

### [Problems](https://cs50.harvard.edu/college/2022/spring/test/#problems)

- [Checking Speller](https://cs50.harvard.edu/college/2022/spring/test/speller/)
- [Code Reviews](https://cs50.harvard.edu/college/2022/spring/test/reviews/)
- [Duo Mobile](https://cs50.harvard.edu/college/2022/spring/test/duo/)
- [Emojicode](https://cs50.harvard.edu/college/2022/spring/test/emojicode/)
- [Harvard Pep Squad](https://cs50.harvard.edu/college/2022/spring/test/squad/)
- [Imagineering](https://cs50.harvard.edu/college/2022/spring/test/imagineering/)
- [International Obfuscated C Code Contest](https://cs50.harvard.edu/college/2022/spring/test/obfuscated/)
- [Reinventing Some Wheels](https://cs50.harvard.edu/college/2022/spring/test/wheels/)
- [View Source](https://cs50.harvard.edu/college/2022/spring/test/source/)
- [XCheck](https://cs50.harvard.edu/college/2022/spring/test/xcheck/)

## [CHANGELOG](https://cs50.harvard.edu/college/2022/spring/test/#changelog)

Reload this page (and each problem’s page) throughout the test window to see any clarifications to the test.

- Wed, Apr 13, 10:25 PM

  Clarified `test.zip` should be unzipped via `unzip test.zip` once downloaded.

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/test/#how-to-submit)

1. Before submitting, download a ZIP file of your `test` folder (which should contain folders `emojicode`, `squad`, and `wheels`). First, ensure you are in your test directory by running

   ```bash
   cd
   ```

   followed by

   ```bash
   cd test
   ```

   Make sure your terminal prompt looks like the below:

   ```bash
   test/ $
   ```

   Then, run the following command:

   ```bash
   zip -r test.zip * -x "*x86_64*"
   ```

   The `-x "*x86_64*"` argument is intended to exclude any system files you may have downloaded to install `emojicodec` for Emojicode.

2. Control-click or right-click on your `test.zip` file in VS Code’s file browser and choose **Download**.

3. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).

4. Click **Test: Code Files**.

5. Drag and drop your `test.zip` file to the area that says “Drag & Drop”.

6. Click “Upload”.

7. You should see a message that says “Test: Code Files submitted  successfully!” Don’t worry if you see 0/31 points—the autograder will  not be configured until after the test’s deadline!

8. Download your completed test response document from Google Docs as a PDF by choosing **File → Download → PDF Document**, and save it to your computer.

9. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).

10. Click **Test: Response Document**.

11. Click **Submit PDF**.

12. Click **Select PDF** and choose your test file.

13. Click **Upload PDF**.

14. For each question in the **Question Outline** at  left, click on the question and then click on the page(s) on which your  response to that question appears. Since some questions (Reinventing  Some Wheels, Harvard Pep Squad, and parts of Emojicode) do not involve  written answers, it is okay to leave pages for those questions  unassigned. Be sure that you have assigned all other questions to a  page, however!

15. Click **Submit**.

16. You should see a message that says “Test: Response Document  submitted successfully!” You may need to first confirm that you have not assigned all pages to all questions.

Be certain that **both parts** of your submission have  been accepted! You are welcome to resubmit your code files and response  document up until the deadline. Only your last submission for each will  be taken into consideration.

If you run into any trouble with the above steps, email [heads@cs50.harvard.edu](mailto:heads@cs50.harvard.edu)!