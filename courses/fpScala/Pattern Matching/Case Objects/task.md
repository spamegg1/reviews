
# Case Objects

You might have noticed in the example of a binary tree implemented with sealed trait hierarchies, 
we used a *case object* to introduce the `Stump` type. 
In Scala, a case object is a special type of object that combines characteristics and benefits of both a case class and an object.

Similar to a case class, a case object comes equipped with a number of auto-generated methods like `toString`, `hashCode`,
and `equals`, and they can be directly used in pattern matching. 
On the other hand, just like any regular object, a case object is a singleton, i.e., there's exactly one instance of it in the entire JVM. 
Case objects are used in place of case classes when there's no need for parametrization — when you don't need  to carry data, 
but you still want to benefit from pattern matching capabilities of case classes. 
In Scala 2, case objects implementing a common trait were the default way of achieving enum functionality. 
This is no longer necessary in Scala 3, which introduced enums, but case objects are still useful in more complex situations.

For example, you may have noticed that to use case objects as enums, we make them extend a shared sealed trait.

```scala 3
sealed trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...
```

Here, `AuthorizationStatus` is a sealed trait and `Authorized` and `Unauthorized` are the only two case objects extending it. 
This means that the result of calling the authorize method can only ever be either `Authorized` or `Unauthorized`. 
There is no other response possible.

However, imagine that you're working on code which uses a library or a module you no longer want to modify. 
In that case, the initial author of that library or module might have used case objects extending a non-sealed trait 
to make it easier for you to add your own functionality:

```scala 3
// original code
trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...

// our extension
case object LoggedOut extends AuthorizationStatus

override def authorize(userId: UserId): AuthorizationStatus =
  if isLoggedOut(userId) then
    LoggedOut
  else
    super.authorize(userId)
```

Here, we extend the functionality of the original code by adding a possibility that the user, despite being authorized to perform a given operation, 
encountered an issue and was logged out. 
Now they need to log in again before they are able to continue. 
This is not the same as simply being `Unauthorized`, so we add a third case object to the set of those extending `AuthorizationStatus`: 
we call it `LoggedOut`. 
If the original author had used a sealed trait to define `AuthorizationStatus`, or if they had used an enum, we wouldn't have been able to do that.

### Exercise 

We're modeling bots that move on a 2D plane (see the `Coordinates` case class). 
There are various kinds of bots (see the `Bot` trait), which move a distinct number of cells at a time. 
Each bot moves in one of four directions (see the `Direction` trait). 
Determine whether the traits should be sealed or not and modify them accordingly.
Implement the `move` function. 
