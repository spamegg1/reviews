object BuilderPatternTask:
  case class Message( sender: Option[String],
                      receiver: Option[String],
                      content: Option[String]
                    )

  class MessageBuilder():
    /* put private fields here */
    private var sender: Option[String] = None
    private var receiver: Option[String] = None
    private var content: Option[String] = None

    def setSender(s: String): MessageBuilder =
      /* TODO */
      sender = Some(s)
      this

    def setReceiver(r: String): MessageBuilder =
      /* TODO */
      receiver = Some(r)
      this

    def setContent(c: String): MessageBuilder =
      /* TODO */
      content = Some(c)
      this

    def build(): Message =
      /* TODO */
      Message(sender, receiver, content)
