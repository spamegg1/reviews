# Uses request.args.get

from flask import Flask, render_template, request

app = Flask(__name__)


@app.route("/")
def index():
    name = request.args.get("name", "world")
    return render_template("index.html", name=name)
