# [Lab 9: Birthdays](https://cs50.harvard.edu/college/2022/spring/labs/9/#lab-9-birthdays)

Labs are practice problems which, time permitting, may be started or  completed in your section, and are assessed on correctness only. You are encouraged to collaborate with classmates on this lab, though each  member in a group collaborating is expected to contribute equally to the lab.

Create a web application to keep track of friends’ birthdays.

![screenshot of birthdays website](https://cs50.harvard.edu/college/2022/spring/labs/9/birthdays.png)

## [Getting Started](https://cs50.harvard.edu/college/2022/spring/labs/9/#getting-started)

Open [VS Code](https://code.cs50.io/).

Start by clicking inside your terminal window, then execute `cd` by itself. You should find that its “prompt” resembles the below.

```bash
$
```

Click inside of that terminal window and then execute

```bash
wget https://cdn.cs50.net/2021/fall/labs/9/birthdays.zip
```

followed by Enter in order to download a ZIP called `birthdays.zip` in your codespace. Take care not to overlook the space between `wget` and the following URL, or any other character for that matter!

Now execute

```bash
unzip birthdays.zip
```

to create a folder called `birthdays`. You no longer need the ZIP file, so you can execute

```bash
rm birthdays.zip
```

and respond with “y” followed by Enter at the prompt to remove the ZIP file you downloaded.

Now type

```bash
cd birthdays
```

followed by Enter to move yourself into (i.e., open) that directory. Your prompt should now resemble the below.

```bash
birthdays/ $
```

If all was successful, you should execute

```bash
ls
```

and you should see the following files and folders:

```bash
app.py  birthdays.db  static/  templates/
```

If you run into any trouble, follow these same steps again and see if you can determine where you went wrong!

## [Understanding](https://cs50.harvard.edu/college/2022/spring/labs/9/#understanding)

In `app.py`, you’ll find the start of a Flask web application. The application has one route (`/`) that accepts both `POST` requests (after the `if`) and `GET` requests (after the `else`). Currently, when the `/` route is requested via `GET`, the `index.html` template is rendered. When the `/` route is requested via `POST`, the user is redirected back to `/` via `GET`.

`birthdays.db` is a SQLite database with one table, `birthdays`, that has four columns: `id`, `name`, `month`, and `day`. There are a few rows already in this table, though ultimately your web  application will support the ability to insert rows into this table!

In the `static` directory is a `styles.css` file containing the CSS code for this web application. No need to edit this file, though you’re welcome to if you’d like!

In the `templates` directory is an `index.html` file that will be rendered when the user views your web application.

## [Implementation Details](https://cs50.harvard.edu/college/2022/spring/labs/9/#implementation-details)

Complete the implementation of a web application to let users store and keep track of birthdays.

- When the 

  ```html
  /
  ```

   route is requested via 

  ```html
  GET
  ```

  , your web application should display, in a table, all of the people in your database along with their birthdays.    

  - First, in `app.py`, add logic in your `GET` request handling to query the `birthdays.db` database for all birthdays. Pass all of that data to your `index.html` template.
  - Then, in `index.html`, add logic to render each birthday as a row in the table. Each row  should have two columns: one column for the person’s name and another  column for the person’s birthday.

- When the 

  ```html
  /
  ```

   route is requested via 

  ```html
  POST
  ```

  , your web application should add a new birthday to your database and then re-render the index page.    

  - First, in `index.html`, add an HTML form. The form should let users type in a name, a birthday month, and a birthday day. Be sure the form submits to `/` (its “action”) with a method of `post`.
  - Then, in `app.py`, add logic in your `POST` request handling to `INSERT` a new row into the `birthdays` table based on the data supplied by the user.

Optionally, you may also:

- Add the ability to delete and/or edit birthday entries.
- Add any additional features of your choosing!

### [Walkthrough](https://cs50.harvard.edu/college/2022/spring/labs/9/#walkthrough)

This video was recorded when the course was still using CS50 IDE for writing code. Though the interface may look different from your codespace, the  behavior of the two environments should be largely similar!

[walkthrough](https://video.cs50.io/HXwvj8x1Fcs)

### [Hints](https://cs50.harvard.edu/college/2022/spring/labs/9/#hints)

- Recall that you can call 

  ```python
  db.execute
  ```

   to execute SQL queries within 

  ```bash
  app.py
  ```

  .    

  - If you call `db.execute` to run a `SELECT` query, recall that the function will return to you a list of  dictionaries, where each dictionary represents one row returned by your  query.

- You’ll likely find it helpful to pass in additional data to `render_template()` in your `index` function so that access birthday data inside of your `index.html` template.

- Recall that the `tr` tag can be used to create a table row and the `td` tag can be used to create a table data cell.

- Recall that, with Jinja, you can create a [`for` loop](https://jinja.palletsprojects.com/en/2.11.x/templates/#for) inside your `index.html` file.

- In 

  ```bash
  app.py
  ```

  , you can obtain the data 

  ```javascript
  POST
  ```

  ed by the user’s form submission via 

  ```python
  request.form.get(field)
  ```

   where 

  ```python
  field
  ```

   is a string representing the 

  ```python
  name
  ```

   attribute of an 

  ```html
  input
  ```

   from your form.    

  - For example, if in `index.html`, you had an `<input name="foo" type="text">`, you could use `request.form.get("foo")` in `app.py` to extract the user’s input.

<details><summary>Not sure how to solve?</summary>

[watch](https://video.cs50.io/lVwv4o8vmvI)

</details>



### [Testing](https://cs50.harvard.edu/college/2022/spring/labs/9/#testing)

No `check50`  for this lab! But be sure to test your web application by adding some  birthdays and ensuring that the data appears in your table as expected.

Run `flask run` in your terminal while in your `birthdays` directory to start a web server that serves your Flask application.

## [How to Submit](https://cs50.harvard.edu/college/2022/spring/labs/9/#how-to-submit)

1. While in your birthdays directory, create a ZIP file of your Flask application by executing:

```bash
zip -r birthdays.zip *
```

1. Download your `birthdays.zip` file by control-clicking or right-clicking on the file in your codespace’s file browser and choosing **Download**.
2. Go to CS50’s [Gradescope page](https://www.gradescope.com/courses/336119).
3. Click “Lab 9: Birthdays”.
4. Drag and drop your `birthdays.zip` file to the area that says “Drag & Drop”. Be sure it has that **exact** filename! If you upload a file with a different name, the autograder  likely will fail when trying to run it, and ensuring you have uploaded  files with the correct filename is your responsibility!
5. Click “Upload”.

You should see a message that says “Lab 9: Birthdays” submitted successfully!”