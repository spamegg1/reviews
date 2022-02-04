/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import akka.actor.*
import scala.collection.immutable.Queue

object BinaryTreeSet:

  trait Operation:
    def requester: ActorRef
    def id: Int
    def elem: Int

  trait OperationReply:
    def id: Int

  /** Request with identifier `id` to insert an element `elem` into the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to check whether an element `elem` is present
    * in the tree. The actor at reference `requester` should be notified when
    * this operation is completed.
    */
  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to remove the element `elem` from the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request to perform garbage collection */
  case object GC

  /** Holds the answer to the Contains request with identifier `id`.
    * `result` is true if and only if the element is present in the tree.
    */
  case class ContainsResult(id: Int, result: Boolean) extends OperationReply

  /** Message to signal successful completion of an insert or remove operation. */
  case class OperationFinished(id: Int) extends OperationReply

  // type Receive = PartialFunction[Any, Unit]


class BinaryTreeSet extends Actor:
  import BinaryTreeSet.*
  import BinaryTreeNode.*

  def createRoot: ActorRef =
    context actorOf BinaryTreeNode.props(0, initiallyRemoved = true)

  var root = createRoot

  // optional (used to stash incoming operations during garbage collection)
  var pendingQueue = Queue.empty[Operation]

  // optional
  def receive = normal

  // optional
  /** Accepts `Operation` and `GC` messages. */
  val normal: Receive =                                                  // TODO
    case op: Operation => root ! op                    // ops must begin at root
    case GC =>
      val newRoot: ActorRef = createRoot                // copy tree to new tree
      root ! CopyTo(newRoot)                           // assume no new ops here
      context become garbageCollecting(newRoot)          // start GCing new copy

  // optional
  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive =                    // TODO
    case op: Operation =>
      pendingQueue = pendingQueue enqueue op              // queue IRC during GC
    case CopyFinished =>
      root ! PoisonPill
      root = newRoot
      dequeue                         // re-send queued ops to root, clear queue
    case GC => ()

  def dequeue: Unit = pendingQueue.dequeueOption match
    case Some((op, q)) =>                                // pending is not empty
      pendingQueue = q
      op match                                        // forward IRC ops to root
        case Insert(_, id, elem) => root ! Insert(self, id, elem)
        case Remove(_, id, elem) => root ! Remove(self, id, elem)
        case Contains(_, id, elem) => root ! Contains(self, id, elem)
      context become pending(op)                         // handle rest of queue
    case None => context become normal                      // nothing on queue!

  def pending(op: Operation): Receive =
    case opr: Operation =>
      pendingQueue = pendingQueue enqueue opr
    case reply: OperationReply =>
      op.requester ! reply
      dequeue

object BinaryTreeNode:
  trait Position

  case object Left extends Position
  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)
  /**
   * Acknowledges that a copy has been completed. This message should be sent
   * from a node to its parent, when this node and all its children nodes have
   * finished being copied.
   */
  case object CopyFinished

  def props(elem: Int, initiallyRemoved: Boolean) =
    Props(classOf[BinaryTreeNode],  elem, initiallyRemoved)

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor:
  import BinaryTreeNode.*
  import BinaryTreeSet.*

  var subtrees = Map[Position, ActorRef]()
  var removed = initiallyRemoved

  // optional
  def receive = normal

  // optional
  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive =                                                  // TODO
    case Insert(req, id, e) => insert(req, id, e)
    case Remove(req, id, e) => remove(req, id, e)
    case Contains(req, id, e) => contains(req, id, e)
    case CopyTo(newRoot) => copyTo(newRoot)

  def insert(req: ActorRef, id: Int, e: Int): Unit =                     // TODO
    if e == elem then
      if removed then
        removed = false
      req ! OperationFinished(id)

    else if e < elem then
      subtrees get Left match
        case Some(aRef) => aRef ! Insert(req, id, e)
        case None =>
          val insertedNode: ActorRef =
            context actorOf props(e, initiallyRemoved = false)
          subtrees += Left -> insertedNode
          req ! OperationFinished(id)

    else
      subtrees get Right match
        case Some(aRef) => aRef ! Insert(req, id, e)
        case None =>
          val insertedNode: ActorRef =
            context actorOf props(e, initiallyRemoved = false)
          subtrees += Right -> insertedNode
          req ! OperationFinished(id)


  def remove(req: ActorRef, id: Int, e: Int): Unit =                     // TODO
    if e == elem then
      removed = true
      req ! OperationFinished(id)

    else if e < elem then
      subtrees get Left match
        case Some(aRef) => aRef ! Remove(req, id, e)
        case None => req ! OperationFinished(id)

    else
      subtrees get Right match
        case Some(aRef) => aRef ! Remove(req, id, e)
        case None => req ! OperationFinished(id)

  def contains(req: ActorRef, id: Int, e: Int): Unit =                   // TODO
    if e == elem then
      req ! ContainsResult(id, !removed)

    else if e < elem then
      subtrees get Left match
        case Some(aRef) => aRef ! Contains(req, id, e)
        case None => req ! ContainsResult(id, result = false)

    else
      subtrees get Right match
        case Some(aRef) => aRef ! Contains(req, id, e)
        case None => req ! ContainsResult(id, result = false)

  def copyTo(req: ActorRef): Unit =                                      // TODO
    if removed && subtrees.isEmpty then
      context.parent ! CopyFinished
      //context stop self
      self ! PoisonPill
      return

    if !removed then
      req ! Insert(self, elem, elem)

    subtrees foreach (node => node._2 ! CopyTo(req))
    context become copying(subtrees.values.toSet, insertConfirmed = removed)

  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node
    * to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef],
              insertConfirmed: Boolean): Receive =                       // TODO
    case OperationFinished(_) =>
      if expected.isEmpty then
        context.parent ! CopyFinished
        //context stop self
        self ! PoisonPill
      else
        context become copying(expected, insertConfirmed = true)

    case CopyFinished =>
      val remaining: Set[ActorRef] = expected - sender()
      if remaining.isEmpty && insertConfirmed then
        context.parent ! CopyFinished
        //context stop self
        self ! PoisonPill
      else
        context become copying(expected, insertConfirmed = true)
