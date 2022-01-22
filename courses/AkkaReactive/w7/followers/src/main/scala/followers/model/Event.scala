package followers.model

import akka.util.ByteString

/**
 * This object provides a representation for the various incoming events.
 *
 * {{{
 * | Payload       | Sequence #| Type         | From User Id | To User Id |
 * |---------------|-----------|--------------|--------------|------------|
 * |666|F|60|50    | 666       | Follow       | 60           | 50         |
 * |1|U|12|9       | 1         | Unfollow     | 12           | 9          |
 * |542532|B       | 542532    | Broadcast    | -            | -          |
 * |43|P|32|56     | 43        | Private Msg  | 32           | 56         |
 * |634|S|32       | 634       | Status Update| 32           | -          |
 * }}}
 *
 * Delivery semantics of messages:
 *
 * Follow:          Only the To User Id should be notified
 * Unfollow:        No clients should be notified
 * Broadcast:       All connected user clients should be notified
 * Private Message: Only the To User Id should be notified
 * Status Update:   All current followers of the From User ID should be notified
 */
sealed trait Event:
  /** Sequence number of the message. Starts at 1. */
  def sequenceNr: Int
  /** Payload of the message */
  def render: ByteString

object Event:

  def parse(message: String): Event =
    val fields = message.split("\\|")
    val seqNr = fields.head.toInt

    fields(1) match
      case "F" => Event.Follow(seqNr, fields(2).toInt, fields(3).toInt)
      case "U" => Event.Unfollow(seqNr, fields(2).toInt, fields(3).toInt)
      case "B" => Event.Broadcast(seqNr)
      case "P" => Event.PrivateMsg(seqNr, fields(2).toInt, fields(3).toInt)
      case "S" => Event.StatusUpdate(seqNr, fields(2).toInt)

  /**
   * Follow: Only the To User Id should be notified
   */
  final case class Follow(
    sequenceNr: Int,
    fromUserId: Int,
    toUserId: Int
  ) extends Event:
    def render: ByteString = ByteString(s"$sequenceNr|F|$fromUserId|$toUserId\n")

  /**
   * Unfollow: No clients should be notified
   */
  final case class Unfollow(
    sequenceNr: Int,
    fromUserId: Int,
    toUserId: Int
  ) extends Event:
    def render: ByteString = ByteString(s"$sequenceNr|U|$fromUserId|$toUserId\n")

  /**
   * Broadcast: All connected user clients should be notified
   */
  final case class Broadcast(
    sequenceNr: Int
  ) extends Event:
    def render: ByteString = ByteString(s"$sequenceNr|B\n")

  /**
   * Private Message: Only the To User Id should be notified
   */
  final case class PrivateMsg(
    sequenceNr: Int,
    fromUserId: Int,
    toUserId: Int
  ) extends Event:
    def render: ByteString = ByteString(s"$sequenceNr|P|$fromUserId|$toUserId\n")

  /**
   * Status Update: All current followers of the From User ID should be notified
   */
  final case class StatusUpdate(
    sequenceNr: Int,
    fromUserId: Int
  ) extends Event:
    def render: ByteString = ByteString(s"$sequenceNr|S|$fromUserId\n")

