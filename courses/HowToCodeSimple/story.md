**How to use templates/recipes in other languages?**
Applying the principles here to other languages/paradigms/programs require metacognitive skills. You need to recognize the overall thinking patterns and not the technical specific details.
Recently I gave an interesting example of this in the Prog Lang Part A course. Starts around [here:](https://discord.com/channels/744385009028431943/744398347368005733/1008445345216331836)

You can come up with your own "templates" and "recipes" in other languages. Here's an example:
```
Let me tell you an interesting story.

A few days ago I was writing some Python code for a problem in the OCW version of the MIT course to help out someone struggling with it. It's a complicated problem with a lot of variables (the one about paying off down payment for a house, using bisection search) and mutation of state. The problem is long and wordy, and doesn't mention writing any functions or anything.

I vaguely came up with an idea for a function. I started writing tests for it, without entirely knowing what the function was supposed to do. I started writing the signature (input/output types) of the function, and this kept changing a little bit as I've read more of the problem. I started to realize how the variables should be interpreted (int, float, boolean etc.) and wrote comments describing them.

After solidifying the tests and the signature, I started thinking about the function. There is no "template" of a function for this problem, but a "template" slowly started appearing in my mind, I could see the overall structure of the function. I started writing some skeleton code just marking the outline of the function, leaving a lot of things blank. So this was effectively a "template" that I created for this problem/function. Eventually I zeroed in on those blank parts and it was very clear what to write in them. And it worked and passed the tests.

Now, the really interesting part is: all the stuff I explained above, I was actually doing all of it subconsciously! Only after I was done, I realized "OMG, I've been totally using the How to Code discipline here!" I must have successfully internalized it even though on the surface of it it looks not applicable to a language like Python.
```

Additionally you need to think about how data structure and "data flow" through the program almost "dictates the code." This happens pretty much in any language/paradigm (with the exception of OOP requiring a very different way of thinking).

Also take a look at [my review](https://github.com/spamegg1/reviews/#how-to-code)
