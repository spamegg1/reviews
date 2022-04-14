# [Code Reviews](https://cs50.harvard.edu/college/2022/spring/test/reviews/#code-reviews)

In industry, it’s best practice to do “code reviews,” whereby one or  more colleagues must review your code for problems before you can  release it to users, much like a TF reviews your problem sets. Suppose  that you’ve been asked to review some code that a (future) colleague has written.

For each of the snippets of code below, **in no more than three sentences**, explain why the code is incorrect, poorly designed, susceptible to  crash, and/or vulnerable to attack, inferring from each snippet what  it’s meant to do, and advise how to fix it. Assume that any functions,  types, and variables used therein have been defined (elsewhere) and that any requisite libraries have been included or imported.

1. (2 points.)

   ```c
   char *get_string(char *prompt)
   {
       printf("%s", prompt);
       char *s;
       scanf("%s", s);
       return s;
   }
   ```

   **SOLUTION:** The code is meant to get user input as a string, and return it. 

   The man pages say this about `scanf`:

   ```
   It is not safe to use this function to get a string from a user using %s, as the user’s input might exceed the capacity of the argument that would be assigned that value.
   ```

   Indeed, if we compile and run this code, after providing keyboard input, we always get `Segmentation fault`.

   One poor temporary solution is to increase the size of the `char *s` like this:

   ```c
   char s[100];
   ```

   This way we won’t get segmentation fault for user input up to 100 characters. We will still get a compiler warning about returning a stack address. Also `scanf` will stop at the first whitespace inputted by the user.

   Better is to use `fgets`  and a `static char` like this:

   ```c
   #define MAXSIZE = 100
   
   char *get_string(char *prompt)
   {
       printf("%s", prompt);
       static char s[MAXSIZE];
       fgets(s, MAXSIZE, stdin);
       return s;
   }
   ```

   This fixes both issues (whitespace and stack memory warning). The issue of getting arbitrary size input from the user still remains. That would require a lot more code, allocating memory, and then resizing/reallocating it over and over.

2. (2 points.)

   ```c
    node *n = malloc(sizeof(node));
    n->number = 13;
    n->next = NULL;
   ```

   **SOLUTION:** The code is meant to create a new node. Not too sure about this one. 

   I think one possible issue is that we might forget to free the allocated memory later. WE SHOULD REMEMBER THAT! 

   Another issue is that we need to check if `malloc` succeeded or not. We should check if `n` is `NULL`:

   ```c
   node *n = malloc(sizeof(node));
   if (n == NULL)
   {
       // do something appropriate, such as returning error
       return -1;
   }
   n->number = 13;
   n->next = NULL;
   ```

   Another issue might be (not sure) assigning a `NULL` pointer to the node’s `next` field. This might cause null pointer related problems. (Not sure about this!)

3. (2 points.)

   ```html
    <form action="/login" method="get">
        <input name="username" type="text">
        <input name="password" type="password">
        <input type="submit">
    </form>
   ```

   **SOLUTION** This is a login form, it is supposed to get user’s name and password. The method is wrong! It should be `POST`. 

   ```html
   <form action="/login" method="post">
        <input name="username" type="text">
        <input name="password" type="password">
        <input type="submit">
    </form>
   ```

   

4. (2 points.)

   ```python
   @app.route("/search", methods=["GET"])
   def search():
       q = request.args.get("q")
       results = db.execute(f"SELECT * FROM books WHERE title LIKE '{q}'")
       return render_template("results.html", results=results)
   ```

   **SOLUTION** The code is supposed to search the database for a book title, and return the results. The issues are:

   - f-strings should not be used (due to possible SQL injection attacks),
   - parameters like `title` and `q` should be sanitized (due to possible SQL injection attacks).

   ```python
   @app.route("/search", methods=["GET"])
   def search():
       q = request.args.get("q")
       results = db.execute("SELECT * FROM books WHERE :title LIKE :query",
                           title=title, query=q)
       return render_template("results.html", results=results)
   ```

5. (2 points.)

   ```python
   @app.route("/register", methods=["POST"])
   def register():
       username = request.form.get("username")
       password = request.form.get("password")
       db.execute("INSERT INTO users (username, password) VALUES(?, ?)", username, password)
       return redirect("/")
   ```

   **SOLUTION** The code is to get a new user name and password, to register a new user. 

   - The execute command syntax is wrong. Also, 
   - it should make sure both the user name and the password were indeed submitted and were not empty, 
   - it should ask for a confirmation of the password, 
   - it should check to see if the user name already exists, 
   - and it should insert the hash of the password, not the plain text of the password itself.

   ```python
   @app.route("/register", methods=["POST"])
   def register():
       # Ensure username was submitted
       if not request.form.get("username"):
           return apology("must provide username", 400)
   
       # Ensure password was submitted
       elif not request.form.get("password"):
           return apology("must provide password", 400)
   
       # Ensure passwords match
       elif not request.form.get("password") == request.form.get("confirmation"):
           return apology("passwords do not match", 400)
   
       # Query database for username
       rows = db.execute("SELECT * FROM users WHERE username = :username",
                         username=request.form.get("username"))
   
       # Ensure username does not exist:
       if len(rows) != 0:
           return apology("username already exists", 400)
   
       # Add username and password to database
       db.execute("INSERT INTO users (username, hash) VALUES (:username, :password)",
                  username=request.form.get("username"),
                  password=generate_password_hash(request.form.get("password")))
   
       return redirect("/")
   ```

   
