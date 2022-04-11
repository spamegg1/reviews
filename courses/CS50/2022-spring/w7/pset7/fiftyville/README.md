# [Fiftyville](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#fiftyville)

Write SQL queries to solve a mystery.

## [A Mystery in Fiftyville](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#a-mystery-in-fiftyville)

The CS50 Duck has been stolen! The town of Fiftyville has called upon you to solve the mystery of the stolen duck. Authorities believe that  the thief stole the duck and then, shortly afterwards, took a flight out of town with the help of an accomplice. Your goal is to identify:

- Who the thief is,
- What city the thief escaped to, and
- Who the thief’s accomplice is who helped them escape

All you know is that the theft **took place on July 28, 2021** and that it **took place on Humphrey Street**.

How will you go about solving this mystery? The Fiftyville  authorities have taken some of the town’s records from around the time  of the theft and prepared a SQLite database for you, `fiftyville.db`, which contains tables of data from around the town. You can query that table using SQL `SELECT` queries to access the data of interest to you. Using just the information in the database, your task is to solve the mystery.

## [Getting Started](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#getting-started)

Log into [code.cs50.io](https://code.cs50.io/), click on your terminal window, and execute `cd` by itself. You should find that your terminal window’s prompt resembles the below:

```bash
$
```

Next execute

```bash
wget https://cdn.cs50.net/2021/fall/psets/7/fiftyville.zip
```

in order to download a ZIP called `fiftyville.zip` into your codespace.

Then execute

```bash
unzip fiftyville.zip
```

to create a folder called `fiftyville`. You no longer need the ZIP file, so you can execute

```bash
rm fiftyville.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd fiftyville
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
fiftyville/ $
```

Execute `ls` by itself, and you should see a few files:

```bash
answers.txt  fiftyville.db  log.sql
```

If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Specification](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#specification)

For this problem, equally as important as solving the mystery itself is the process that you use to solve the mystery. In `log.sql`, keep a log of all SQL queries that you run on the database. Above each  query, label each with a comment (in SQL, comments are any lines that  begin with `--`) describing why you’re running the query and/or what information you’re  hoping to get out of that particular query. You can use comments in the  log file to add additional notes about your thought process as you solve the mystery: ultimately, this file should serve as evidence of the  process you used to identify the thief!

As you write your queries, you may notice that some of them can  become quite complex. To help keep your queries readable, see principles of good style at [sqlstyle.guide](https://www.sqlstyle.guide). The [indentation](https://www.sqlstyle.guide/#indentation) section may be particularly helpful!

Once you solve the mystery, complete each of the lines in `answers.txt` by filling in the name of the thief, the city that the thief escaped  to, and the name of the thief’s accomplice who helped them escape town.  (Be sure not to change any of the existing text in the file or to add  any other lines to the file!)

Ultimately, you should submit both your `log.sql` and `answers.txt` files.

## [Walkthrough](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#walkthrough)

Note: This walkthrough is about a different infamous crime in Fiftyville,  which took place on Chamberlin Street. Authorities are certain the most  recent theft took place on Humphrey Street. The database they’ve given  you has been updated to include the `bakery_security_logs` table.

[walkthrough](https://www.youtube.com/embed/x7Q8tJMi7cQ?modestbranding=0&amp;rel=0&amp;showinfo=0)

## [Hints](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#hints)

- Execute 

  ```bash
  sqlite3 fiftyville.db
  ```
  
   to begin running queries on the database.    

  - While running `sqlite3`, executing `.tables` will list all of the tables in the database.
  - While running `sqlite3`, executing `.schema TABLE_NAME`, where `TABLE_NAME` is the name of a table in the database, will show you the `CREATE TABLE` command used to create the table. This can be helpful for knowing which columns to query!
  
- You may find it helpful to start with the `crime_scene_reports` table. Start by looking for a crime scene report that matches the date and the location of the crime.

- See [this SQL keywords reference](https://www.w3schools.com/sql/sql_ref_keywords.asp) for some SQL syntax that may be helpful!

## [Testing](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#testing)

Execute the below to evaluate the correctness of your code using `check50`.

```bash
check50 cs50/problems/2022/spring/fiftyville
```

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#how-to-submit)

1. Download your `log.sql` and `answers.txt` files by control-clicking or right-clicking on the files in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Problem Set 7: Fiftyville”.
4. Drag and drop your `log.sql` and `answers.txt` files to the area that says “Drag & Drop”. Be sure they have those **exact** filenames! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filenames is your responsibility!
5. Click “Upload”.

You should see a message that says “Problem Set 7: Fiftyville  submitted successfully!” You may not see a score just yet, but if you  see the message then we’ve received your submission!

## [Acknowledgements](https://cs50.harvard.edu/college/2022/spring/psets/7/fiftyville/#acknowledgements)

Inspired by another case over at [SQL City](http://mystery.knightlab.com/).