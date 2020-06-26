import os

from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session
from flask_session import Session
from tempfile import mkdtemp
from werkzeug.exceptions import default_exceptions
from werkzeug.security import check_password_hash, generate_password_hash

from helpers import apology, login_required, lookup, usd

# Ensure environment variable is set
if not os.environ.get("API_KEY"):
    raise RuntimeError("API_KEY not set")

# Configure application
app = Flask(__name__)

# Ensure templates are auto-reloaded
app.config["TEMPLATES_AUTO_RELOAD"] = True


# Ensure responses aren't cached
@app.after_request
def after_request(response):
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    response.headers["Expires"] = 0
    response.headers["Pragma"] = "no-cache"
    return response


# Custom filter
app.jinja_env.filters["usd"] = usd

# Configure session to use filesystem (instead of signed cookies)
app.config["SESSION_FILE_DIR"] = mkdtemp()
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Configure CS50 Library to use SQLite database
db = SQL("sqlite:///finance.db")


###### INDEX ######
@app.route("/")
@login_required
def index():
    """Show portfolio of stocks"""

    # Query database for user's cash in users table
    cash = db.execute("SELECT cash FROM users WHERE id = :userid",
                      userid=session["user_id"])
    cash = cash[0]['cash']

    # Query database for stocks owned by user in stocks table
    stocks = db.execute("SELECT * FROM stocks WHERE id = :userid",
                        userid=session["user_id"])

    # Extract symbols from stocks
    symbols = [row['symbol'] for row in stocks]

    # Lookup CURRENT prices of stocks
    prices = [lookup(symbol)['price'] for symbol in symbols]

    # Lookup number of shares of each stock
    shares = [row['shares'] for row in stocks]

    # Calculate user's total (cash + all stocks)
    total = cash
    for i in range(len(stocks)):
        total += shares[i] * prices[i]

    return render_template("index.html", key="NVIBF3Y09TWBEWRJ", cash=cash, stocks=stocks, total=total, prices=prices, shares=shares, symbols=symbols, usd=usd)


###### BUY ######
@app.route("/buy", methods=["GET", "POST"])
@login_required
def buy():
    """Buy shares of stock"""

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure symbol was submitted
        if not request.form.get("symbol"):
            return apology("missing symbol", 400)

        # Store symbol in uppercase
        symbol = request.form.get("symbol").upper()

        # Lookup stock quote using lookup function from helpers.py
        quote = lookup(symbol)

        # Ensure symbol is valid
        if quote == None:
            return apology("invalid symbol", 400)

        # Ensure number of shares being bought was submitted
        if not request.form.get("shares"):
            return apology("missing shares", 400)

        # Ensure number of shares is numeric
        if not request.form.get("shares").isnumeric():
            return apology("invalid shares", 400)

        # Convert shares to int
        shares = int(request.form.get("shares"))

        # Ensure number of shares being bought is positive
        if shares <= 0:
            return apology("invalid shares", 400)

        # Query users table in database for user's available cash
        cash = db.execute("SELECT cash FROM users WHERE id = :userid",
                          userid=session["user_id"])
        cash = cash[0]['cash']

        # Ensure funds are sufficient to buy shares
        spending = shares * quote['price']
        if cash < spending:
            return apology("insufficient funds", 400)

        # Carry out the transaction, add it to transactions table in database
        db.execute("INSERT INTO transactions (id, symbol, shares, price) VALUES (:userid, :symbol, :shares, :price)",
                   userid=session["user_id"],
                   symbol=symbol,
                   shares=shares,
                   price=quote['price'])

        # Now update cash on users table in database
        db.execute("UPDATE users SET cash = :newcash WHERE id = :userid",
                   newcash=cash - spending, userid=session["user_id"])

        # Query stocks table in database for already existing shares of symbol for user
        existing_shares = db.execute("SELECT shares FROM stocks WHERE id = :userid and symbol = :symbol",
                                     userid=session["user_id"], symbol=symbol)

        # If user has no existing shares of symbol, insert shares on stocks table in database
        if not existing_shares:
            db.execute("INSERT INTO stocks (id, symbol, shares) VALUES (:userid, :symbol, :shares)",
                       userid=session["user_id"],
                       symbol=symbol,
                       shares=shares)

        # Otherwise add bought shares to existing shares
        else:
            existing_shares = existing_shares[0]['shares']
            db.execute("UPDATE stocks SET shares = :newshares WHERE id = :userid and symbol = :symbol",
                       newshares=shares + existing_shares,
                       userid=session["user_id"],
                       symbol=symbol)

        # Redirect to portfolio page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("buy.html")


###### HISTORY ######
@app.route("/history")
@login_required
def history():
    """Show history of transactions"""

    # Query database for user's transactions from the transactions table in database
    transactions = db.execute("SELECT * from transactions WHERE id = :userid",
                              userid=session["user_id"])

    return render_template("history.html", transactions=transactions)


###### LOGIN ######
@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in"""

    # Forget any user_id
    session.clear()

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username", 403)

        # Ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password", 403)

        # Query database for username
        rows = db.execute("SELECT * FROM users WHERE username = :username",
                          username=request.form.get("username"))

        # Ensure username exists and password is correct
        if len(rows) != 1 or not check_password_hash(rows[0]["hash"], request.form.get("password")):
            return apology("invalid username and/or password", 403)

        # Remember which user has logged in
        session["user_id"] = rows[0]["id"]

        # Redirect user to home page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")


###### LOGOUT ######
@app.route("/logout")
def logout():
    """Log user out"""

    # Forget any user_id
    session.clear()

    # Redirect user to login form
    return redirect("/")


###### QUOTE ######
@app.route("/quote", methods=["GET", "POST"])
@login_required
def quote():
    """Get stock quote."""

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure symbol was submitted
        if not request.form.get("symbol"):
            return apology("missing symbol", 400)

        # Lookup stock quote using lookup function from helpers.py
        quote = lookup(request.form.get("symbol"))

        # Ensure symbol is valid
        if quote == None:
            return apology("invalid symbol", 400)

        # Render quoted page
        symbol = quote['symbol']
        price = usd(quote['price'])
        return render_template("quoted.html", symbol=symbol, price=price)

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("quote.html")


###### REGISTER ######
@app.route("/register", methods=["GET", "POST"])
def register():
    """Register user"""

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

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

        # Redirect user to login page
        return redirect("/login")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("register.html")


###### SELL ######
@app.route("/sell", methods=["GET", "POST"])
@login_required
def sell():
    """Sell shares of stock"""

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure symbol was submitted
        if not request.form.get("symbol"):
            return apology("missing symbol", 400)

        # Store symbol
        symbol = request.form.get("symbol").upper()

        # Lookup stock quote using lookup function from helpers.py
        quote = lookup(symbol)

        # Ensure number of shares being sold was submitted
        if not request.form.get("shares"):
            return apology("missing shares", 400)

        # Convert shares to int
        shares = int(request.form.get("shares"))

        # Query stocks table in database for already existing shares of symbol for user
        existing_shares = db.execute("SELECT shares FROM stocks WHERE id = :userid and symbol = :symbol",
                                     userid=session["user_id"], symbol=symbol)
        existing_shares = existing_shares[0]['shares']

        # Ensure number of shares being sold is positive, and does not exceed existing shares
        if shares <= 0 or shares > existing_shares:
            return apology("invalid shares", 400)

        # Query users table in database for user's available cash
        cash = db.execute("SELECT cash FROM users WHERE id = :userid",
                          userid=session["user_id"])
        cash = cash[0]['cash']

        # Carry out the transaction, add it to transactions table in database
        db.execute("INSERT INTO transactions (id, symbol, shares, price) VALUES (:userid, :symbol, :shares, :price)",
                   userid=session["user_id"],
                   symbol=symbol,
                   shares=-shares,
                   price=quote['price'])
        # Calculate money made by the sale
        sale = shares * quote['price']

        # Now update cash on users table in database
        db.execute("UPDATE users SET cash = :newcash WHERE id = :userid",
                   newcash=cash + sale, userid=session["user_id"])

        # Update shares on stocks table in database
        # Subtract sold shares from existing shares
        db.execute("UPDATE stocks SET shares = :newshares WHERE id = :userid and symbol = :symbol",
                   newshares=existing_shares - shares,
                   userid=session["user_id"],
                   symbol=symbol)

        # Redirect to portfolio page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        # Gather symbols that belong to user from stocks table in database
        stocks = db.execute("SELECT symbol FROM stocks WHERE id = :userid",
                            userid=session["user_id"])

        return render_template("sell.html", stocks=stocks)


def errorhandler(e):
    """Handle error"""
    return apology("bla")


# listen for errors
for code in default_exceptions:
    app.errorhandler(code)(errorhandler)
