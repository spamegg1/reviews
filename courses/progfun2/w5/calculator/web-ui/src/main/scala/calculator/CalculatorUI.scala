package calculator

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.html
import dom.document
import Expr.*

object CalculatorUI:
  def main(args: Array[String]): Unit =
    try
      setupTweetMeasurer()
      setup2ndOrderPolynomial()
      setupCalculator()
    catch
      case th: Throwable =>
        th.printStackTrace()

  // Helpers

  def elementById[A <: js.Any](id: String): A =
    document.getElementById(id).asInstanceOf[A]

  def elementValueSignal(element: html.Element,
      getValue: () => String): Signal[String] =
    var prevVal = getValue()
    val value = Signal.Var(prevVal)
    val onChange = { (event: dom.Event) =>
      // Reconstruct manually the optimization at the root of the graph
      val newVal = getValue()
      if newVal != prevVal then
        prevVal = newVal
        value() = newVal
    }
    element.addEventListener("change", onChange)
    element.addEventListener("keypress", onChange)
    element.addEventListener("keyup", onChange)
    value

  def inputValueSignal(input: html.Input): Signal[String] =
    elementValueSignal(input, () => input.value)

  def textAreaValueSignal(textAreaID: String): Signal[String] =
    val textArea = elementById[html.TextArea](textAreaID)
    elementValueSignal(textArea, () => textArea.value)

  private lazy val ClearCssClassRegExp =
    js.RegExp(raw"""(?:^|\s)has-error(?!\S)""", "g")

  def doubleValueOfInput(input: html.Input): Signal[Double] =
    val text = inputValueSignal(input)
    val parent = input.parentElement
    Signal {
      import js.JSStringOps.*
      parent.className = parent.className.jsReplace(ClearCssClassRegExp, "")
      try
        text().toDouble
      catch
        case e: NumberFormatException =>
          parent.className += " has-error"
          Double.NaN
    }

  // TWEET LENGTH

  def setupTweetMeasurer(): Unit =
    val tweetText = textAreaValueSignal("tweettext")
    val remainingCharsArea =
      document.getElementById("tweetremainingchars").asInstanceOf[html.Span]

    val remainingCount = TweetLength.tweetRemainingCharsCount(tweetText)
    Signal {
      remainingCharsArea.textContent = remainingCount().toString
    }

    val color = TweetLength.colorForRemainingCharsCount(remainingCount)
    Signal {
      remainingCharsArea.style.color = color()
    }

  // 2ND ORDER POLYNOMIAL

  def setup2ndOrderPolynomial(): Unit =
    val ids = List("polyroota", "polyrootb", "polyrootc")
    val inputs = ids.map(id => elementById[html.Input](id))
    val doubleValues = inputs.map(doubleValueOfInput)
    val List(a, b, c) = doubleValues

    val delta = Polynomial.computeDelta(a, b, c)
    val deltaArea = elementById[html.Span]("polyrootdelta")
    Signal {
      deltaArea.textContent = delta().toString
    }

    val solutions = Polynomial.computeSolutions(a, b, c, delta)
    val solutionsArea = elementById[html.Span]("polyrootsolutions")
    Signal {
      solutionsArea.textContent = solutions().toString
    }

  // CALCULATOR

  def setupCalculator(): Unit =
    val names = (0 until 10).map(i => ('a' + i).toChar.toString)

    val inputs = names.map(name => elementById[html.Input]("calculatorexpr" + name))
    val exprs = inputs.map(exprOfInput)

    val namedExpressions = names.zip(exprs).toMap

    val namedValues = Calculator.computeValues(namedExpressions)

    assert(namedValues.keySet == namedExpressions.keySet)

    for (name, valueSignal) <- namedValues do
      val span = elementById[html.Span]("calculatorval" + name)
      var dehighlightTimeout: Option[js.timers.SetTimeoutHandle] = None
      Signal {
        span.textContent = valueSignal().toString

        span.style.backgroundColor = "#ffff99"

        dehighlightTimeout.foreach(js.timers.clearTimeout)
        dehighlightTimeout = Some(js.timers.setTimeout(1500) {
          dehighlightTimeout = None
          span.style.backgroundColor = "white"
        })
      }

  def exprOfInput(input: html.Input): Signal[Expr] =
    val text = inputValueSignal(input)
    val parent = input.parentElement
    Signal {
      import js.JSStringOps.*
      parent.className = parent.className.jsReplace(ClearCssClassRegExp, "")
      try
        parseExpr(text())
      catch
        case e: IllegalArgumentException =>
          parent.className += " has-error"
          Literal(Double.NaN)
    }

  def parseExpr(text: String): Expr =
    def parseSimple(text: String): Expr =
      if text.forall(l => l >= 'a' && l <= 'z') then
        Ref(text)
      else
        try
          Literal(text.toDouble)
        catch
          case e: NumberFormatException =>
            throw IllegalArgumentException(s"$text is neither a variable name nor a number")

    text.split(" ").map(_.trim).filter(_ != "") match
      case Array(x) => parseSimple(x)
      case Array(aText, op, bText) =>
        val a = parseSimple(aText)
        val b = parseSimple(bText)
        op match
          case "+" => Plus(a, b)
          case "-" => Minus(a, b)
          case "*" => Times(a, b)
          case "/" => Divide(a, b)
          case _ =>
            throw IllegalArgumentException(s"$op is not a valid operator")
      case _ =>
        throw IllegalArgumentException(s"$text is not a valid simple expression")
