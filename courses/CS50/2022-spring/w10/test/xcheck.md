# [XCheck](https://cs50.harvard.edu/college/2022/spring/test/xcheck/#xcheck)

[Facebook Whistleblower Frances Haugen - The 60 Minutes Interview.mp4](https://player.vimeo.com/video/647468555?h=38c6ec1f13&amp;badge=0&amp;autopause=0&amp;player_id=0&amp;app_id=58479)

The Wall Street Journal recently published the [Facebook Files](https://en.wikipedia.org/wiki/Facebook_Files), a series of articles based on internal documents leaked from Facebook (otherwise known now as Meta). Per the [investigation](https://www.wsj.com/articles/the-facebook-files-11631713039):

> In private, the company has built a system that has exempted  high-profile users from some or all of its rules. The program, known as  “cross check” or “XCheck,” was intended as a quality-control measure for high-profile accounts. Today, it shields millions of VIPs from the  company’s normal enforcement, the documents show.

And according to one [article](https://www.wsj.com/articles/facebook-files-xcheck-zuckerberg-elite-rules-11631541353) in particular:

> Facebook designed the system to minimize what its employees have  described in the documents as “PR fires”—negative media attention that  comes from botched enforcement actions taken against VIPs.

Let’s consider how Facebook should (or should not) implement a feature like XCheck.

1. (2 points.) Suppose that Facebook has implemented XCheck with HTML like the below, wherein the `form` via which users post content includes an `input`, the type of which is [`hidden`](https://www.w3schools.com/tags/att_input_type_hidden.asp), the `value` of which is `1` for VIPs and `0` for everyone else:

   ```html
    <!DOCTYPE html>
   
    <html lang="en">
        <head>
            <title>Facebook</title>
        </head>
        <body>
            <form method="post">
                <input name="vip" type="hidden" value="0">
                <input name="content" placeholder="What's on your mind?" type="text">
                <input type="submit">
            </form>
        </body>
    </html>
   ```

   Suppose further that Facebook relies server-side on the value of that `vip` parameter, once submitted via POST, to decide whether to post some content or moderate it.

   In no more than three sentences, explain in technical terms why this implementation is not designed well.

------

Suppose that Facebook has instead implemented XCheck with JavaScript like the below, wherein `vip` is `true` for VIPs and `false` for everyone else:

```html
<!DOCTYPE html>

<html lang="en">
    <head>
        <script>

            let vip = false;

            document.addEventListener('DOMContentLoaded', function() {
                document.querySelector('form').addEventListener('submit', function(e) {

                    // TODO

                });
            });

        </script>
        <title>Facebook</title>
    </head>
    <body>
        <form method="post">
            <input id="content" name="content" placeholder="What's on your mind?" type="text">
            <input type="submit">
        </form>
    </body>
</html>
```

1. (3 points.) Suppose that Facebook wants to limit the spread of  COVID-19 misinformation. Complete the implementation of the page’s event listener in such a way that, if `content` contains any (case-insensitive) mention of `vaccine`, the `form` can only be submitted to the server by VIPs.

1. (3 points.) Suppose that Facebook wants to detect words in addition to `vaccine`. Recall that a [regular expression](https://www.w3schools.com/js/js_regexp.asp) (regex) is a pattern that can be used to match strings. Consider the regex below:

   ```regex
    /^(covid(-19)?|vaccin(e|at(ed?|ions?)))$/i
   ```

   What are **all** of the (all-lowercase) strings that this regular expression would match?

1. (3 points.) In no more than three sentences, explain in technical  terms why this implementation is not designed well, even with the regex.

1. (2 points.) In no more than three sentences, explain in  non-technical terms why this implementation might limit more than the  spread of misinformation.

------

Suppose that Facebook has implemented XCheck with SQL like the below in `facebook.db`, wherein `vip` is `1` for VIPs and `0` for everyone else:

```sqlite
CREATE TABLE users (
    id INTEGER,
    username TEXT NOT NULL,
    hash TEXT NOT NULL,
    vip INTEGER DEFAULT 0,
    PRIMARY KEY(id)
);
CREATE UNIQUE INDEX username ON users (username);
```

1. (2 points.) Suppose that you’ve opened `facebook.db` in Python per the below:

   ```python
    import cs50
   
    db = cs50.SQL("sqlite:///facebook.db")
    def is_vip(username):
        # TODO
   ```

   Complete the implementation of `is_vip` in such a way that the function returns `True` if `username` is a VIP and `False` if not.

1. (2 points.) Via what SQL query could Facebook disable XCheck by taking away everyone’s VIP status? No need for Python.

------

1. (1 point.) Suffice it to say, [with great power comes great responsibility](https://en.wikipedia.org/wiki/With_great_power_comes_great_responsibility). Suppose that you’re on Facebook’s engineering team, asked by a product  manager to implement XCheck. And suppose that you have non-technical  concerns with the feature’s potential for abuse. How might you phrase  one such concern to the manager to open their eyes to the same?

1. (1 point.) Suppose that you’re that manager instead. How do you hope you’d respond?