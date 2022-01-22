package followers.model

/**
  * Message sent by a user joining the network
  *
  * @param userId Identity of the user
  */
final case class Identity(userId: Int)

object Identity {
  def parse(message: String): Identity = Identity(message.toInt)
}