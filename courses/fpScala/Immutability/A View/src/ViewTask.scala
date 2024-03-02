object ViewTask:
  def findLogMessage(
    messages: List[String],
    severity: String,
    errorCode: Int
  ): Option[String] =
    /* TODO */
    messages
      .view
      .find(s => s.startsWith(s"$severity, $errorCode"))
      .map(_.split(",").last)


  @main
  def main() =
    val logMessages: List[String] = List(
      "Error, 1, this is an error log entry",
      "Error, 0, this is another error log entry",
      "Info, 0, this is an info log entry",
      "Fatal, 0, this is a fatal log entry")
    println(findLogMessage(logMessages, "Error", 0)) // Some( this is another error log entry)
    println(findLogMessage(logMessages, "Error", 1)) // Some( this is an error log entry)
    println(findLogMessage(logMessages, "Error", 2)) // None
    println(findLogMessage(logMessages, "Fatal", 0)) // Some( this is a fatal log entry)
