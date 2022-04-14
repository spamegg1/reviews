# [Imagineering](https://cs50.harvard.edu/college/2022/spring/test/imagineering/#imagineering)

![Dory and Nemo](https://cs50.harvard.edu/college/2022/spring/test/imagineering/dory-quote071_595c2401.png)

Source: [news.disney.com/9-dory-quotes-deeper-than-the-drop-off](https://news.disney.com/9-dory-quotes-deeper-than-the-drop-off)

In the real world, it’s quite common to have to read and understand  someone else’s code and even learn some new library along the way. Let’s do just that.

Suppose that you’ve been hired as a Software ~~Engineer~~ Imagineer at Pixar Animation Studios to work on a sequel to *Finding Nemo* and *Finding Dory*, which were animated films starring fish. Your team is interested in  developing a model for fish movement, especially for scenes in which  schools of fish will be swimming in the background.

Suppose that a previous imagineer created [this program](https://p5js.org/examples/hello-p5-flocking.html) in JavaScript to simulate birds flying and “flocking” together, which  seems like a reasonable starting point for modeling fish swimming  together. (Click the program’s **run** button to run the  code.) Your job is to figure out how their program works and,  ultimately, propose how to adapt it for fish! No need to understand  every line of code, though. We’ll guide you through some of it!

Note that the code uses [p5.js](https://p5js.org/), a  JavaScript library for animation “that empowers artists, designers,  students, and anyone to learn to code and express themselves creatively  on the web.” You might find its [reference](https://p5js.org/reference/) helpful!

Take a look at the imagineer’s first function, `setup`:

```javascript
let boids = [];

function setup() {
  createCanvas(720, 400);

  // Add an initial set of boids into the system
  for (let i = 0; i < 100; i++) {
    boids[i] = new Boid(random(width), random(height));
  }
}
```

Notice that the previous imagineer commented, “Add an initial set of  boids into the system”. A “boid” is a bird-like object. (Try saying it  aloud.)

1. (1 point.) In no more than one sentence, explain how you could change how many boids are in the system.

**ANSWER** Change the upper bound of the loop variable `i` in this loop in the `setup()` function:

```js
for (let i = 0; i < 10; i++) {
```

2. (2 points.) In no more than three sentences, explain what the line of code below does. You might find it helpful to read up on [JavaScript Object Constructors](https://www.w3schools.com/js/js_object_constructors.asp) and then take a look at the imagineer’s `constructor` function (aka method).

   ```javascript
    boids[i] = new Boid(random(width), random(height));
   ```
   
   **ANSWER** It creates a new `Boid` instance/object (by calling the constructor) with random x, y coordinates for its position, and assigns it to index `i` in the `boids` array.

------

Next, take a look at the imagineer’s `draw` function:

```javascript
function draw() {
  background(51);
  
  // Run all the boids
  for (let i = 0; i < boids.length; i++) {
    boids[i].run(boids);
  }
}
```

Notice, in particular, that the previous imagineer commented, “Run all the boids”.

3. (3 points.) In no more than three sentences, what does it mean to “run” a boid? Be sure to look at the code for `run` and any functions it calls.

**ANSWER** Running a boid updates its acceleration, velocity, direction vector based on the boids around it, based on simulating three flock mechanisms (separation, alignment, cohesion) between birds. If the “next” position of a boid goes out of bounds, it wraps around the screen. Finally the boid is rendered at the updated location

4. (3 points.) Suppose that, on slower computers, it might be  painfully slow to run too many boids at once. In no more than three  sentences, how could you change `draw` to only run some of the boids?

**ANSWER** I would create a global constant `MAX_BOIDS_FOR_SLOW_PCS` and use that instead of `boids.length`.

5. (2 points.) Notice how `draw` calls `background`, a function that comes with p5.js, per its [reference](https://p5js.org/reference/), with an argument of `51`, which makes the flock’s background gray. With what line of code could  you instead change the flock’s background to the color that browsers  know as [Aqua](https://www.w3schools.com/colors/colors_names.asp), as though it’s swimming in water?

**ANSWER** I can use:

```javascript
// R, G & B integer values
background(0, 255, 255);
describe('canvas with aqua background');
```



------

Finally, take a look at the imagineer’s `run` function (aka method),

```javascript
run(boids) {
  this.flock(boids);
  this.update();
  this.borders();
  this.render();
}
```

as well as the imagineer’s `flock` function (aka method),

```javascript
flock(boids) {
  let sep = this.separate(boids); // Separation
  let ali = this.align(boids);    // Alignment
  let coh = this.cohesion(boids); // Cohesion
  // Arbitrarily weight these forces
  sep.mult(2.5);
  ali.mult(1.0);
  coh.mult(1.0);
  // Add the force vectors to acceleration
  this.applyForce(sep);
  this.applyForce(ali);
  this.applyForce(coh);
}
```

both of which are defined in the `Boid` class.

6. (3 points.) Notice how `run` calls `this.flock(boids)`, wherein `this` refers to a `boid` object. In no more than three sentences, why does `flock` need access not only to `this` `boid` but also the whole array of `boids`?

**ANSWER** Since it is simulating flock mechanics, the acceleration/velocity/direction/position of `this` boid is influenced by all the other boids that are nearby and around it.

7. (2 points.) Notice how flocking behavior is a function of three  other behaviors: separation, alignment, and cohesion. See the comments  atop `separate`, `align`, and `cohesion`, respectively, for definitions of each. Suppose that your team wants to  create a single school of fish swimming tightly together. In no more  than three sentences, explain how you could change `flock` to achieve that behavior. Again, no need to understand every line of  code. See what you can glean from some trial and error! Note that if you check **Auto-refresh** atop [the program](https://p5js.org/examples/hello-p5-flocking.html), you can make changes to the code and see the effects in real time.

**ANSWER** I can increase the weight of alignment and cohesion, and lower the weight of separation:

```javascript
sep.mult(1.0);
ali.mult(2.5);
coh.mult(2.5);
```

