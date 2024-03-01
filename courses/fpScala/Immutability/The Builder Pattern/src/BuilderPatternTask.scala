object BuilderPatternTask:
  case class Message( sender: Option[String],
                      receiver: Option[String],
                      content: Option[String]
                    )

  class MessageBuilder():
    /* put private fields here */

    def setSender(s: String): MessageBuilder =
      /* TODO */

    def setReceiver(r: String): MessageBuilder =
      /* TODO */

    def setContent(c: String): MessageBuilder =
      /* TODO */

    def build(): Message =
      /* TODO */
