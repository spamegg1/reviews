[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 11: FRP

CIS 194: Homework 11
Due Tuesday, November 15

The is just small, 10 points, finger exercise to reinforce the lecture material. Your main task is to work on the project.

Because exercise 2 builds on top of exercise 1, you only have to submit the final code if you do both exercises.

## Exercise 1

Start from [code from the lecture](https://www.seas.upenn.edu/~cis194/fall16/extras/11-frp.hs). Install `reactive-bananas-wx` and all its dependencies, to be able to run it.

The banana price should now vary.

- Model the price as a `Behavior Integer` (this is basically done already).
- Add a new text output that displays the current price.
- Made sure that the price starts at 7 and increases by 1 every time a banana is bought, up to a maximum of 20.

## Exercise 2

Now the it is pay-what-you-want time. To that end, add a *slider* widget to the window. It should range from 1 to 20, and display the  current price of the banana. Furthermore, the user should be able to use the slider to set the price of the banana.

This is also an exercise in API research, i.e. you will have to  search the WX documentation on information about how to create a slider, and the `reactive-banana-wx` documentation to find out how to react to the value of a slider. For the latter, this bit of code might be useful:

```haskell
eventSlider :: Slider a -> MomentIO (Event Int)
eventSlider w = do
    addHandler <- liftIO $ event1ToAddHandler w (event0ToEvent1 command)
    fromAddHandler $ mapIO (const $ get w selection) addHandler
```

The result might look like this:

![img](https://www.seas.upenn.edu/~cis194/fall16/images/banana.png)

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          

  