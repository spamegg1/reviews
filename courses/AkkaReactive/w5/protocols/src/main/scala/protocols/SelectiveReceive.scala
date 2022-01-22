package protocols

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.*

import scala.reflect.ClassTag

object SelectiveReceive:
  /**
    * @return A behavior that stashes incoming messages unless they are handled
    *         by the underlying `initialBehavior`
    * @param bufferCapacity Maximum number of messages to stash before throwing a `StashOverflowException`
    *                       Note that 0 is a valid size and means no buffering at all (ie all messages should
    *                       always be handled by the underlying behavior)
    * @param initialBehavior Behavior to decorate
    * @tparam T Type of messages
    *
    * Hints: Use [[Behaviors.withStash]] to create a `StashBuffer` and call the `intercept`
    *        method defined below to intercept messages sent to the `initialBehavior`.
    *        Use [[Behavior.validateAsInitial]] to make sure that `initialBehavior` is a
    *        valid initial behavior.
    */
  def apply[T: ClassTag](bufferCapacity: Int, initialBehavior: Behavior[T]): Behavior[T] =
    ???

  /**
   * @return A behavior that interprets the incoming messages with the supplied `started`
   *         behavior to compute the next behavior. If the message has been unhandled, it
   *         is stashed in the `buffer`. If the message has been handled, the previously
   *         stashed messages are also sent to the next behavior.
   *
   * @param bufferSize Capacity of the StashBuffer
   * @param buffer     Buffer to stash unhandled messages to
   * @param started    Behavior to decorate. Must be a valid “initial” behavior.
   * @tparam T         Type of messages
   *
   * Hints: Use [[Behavior.interpretMessage]] to compute the next behavior.
   *        Use [[Behavior.isUnhandled]] to know if the message was unhandled by the behavior.
   *        Use [[Behavior.canonicalize]] to make the next behavior applicable to the
   *        `unstashAll` operation of the `StashBuffer` (otherwise, an
   *        `IllegalArgumentException` will be thrown by the actor system).
   */
  private def intercept[T: ClassTag](bufferSize: Int, buffer: StashBuffer[T], started: Behavior[T]): Behavior[T] =
    Behaviors.receive { case (ctx, message) =>
      // If the next behavior does not handle the incoming `message`, stash the `message` and
      // return an unchanged behavior. Otherwise, return a behavior resulting from
      // “unstash-ing” all the stashed messages to the next behavior wrapped in an `SelectiveReceive`
      // interceptor.
      ???
    }

