# `find`

`def find(pred: A => Boolean): Option[A]`

Imagine that instead of filtering for all black cats, we are happy with obtaining just one, no matter which. 
We could use `filter` for that and then take the first cat from the resulting collection. However, `filter` will iterate through the entire original collection of cats, regardless of its size.
There are better solutions. 
For example, we can use the `find` method, which works precisely like `filter` but stops at the first matching element.

```scala
// We find the first black cat in the bag, if it exists
val blackCat: Option[Cat] = bagOfCats.find { cat => cat.color == Black }

val felixTheCat: Option[Cat] = bagOfCats.find { cat => cat.name == "Felix" }
```

Note that the `find` method returns an `Option`. 
For now, you can consider an `Option` as a special type of collection that holds either zero or one element. 
If the predicate we used returned `false` for every element in the collection, the `find` method would return an empty `Option` (also known as `None`). 
We will talk more about `Option` in one of the following chapters.

Also, while we are on the topic, check out the Scala documentation for methods such as `exists`, `forall`, and `contains`. 
These can be handy if you just want to check whether an element in the collection fulfills specific requirements 
(or, in the case of `forall`, whether all of them meet the requirements), but you are not interested in retrieving the matching element.

## Exercise

Now, imagine that we are willing to adopt any cat from the animal shelter that is white and fluffy, or, say, any Persian cat with a calico coat.
Implement the corresponding functions. 

<div class="hint">
  Use the `contains` method to check if a cat is fluffy.
</div>
