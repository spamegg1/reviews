package calculator

enum Expr:
  case Literal(v: Double)
  case Ref(name: String)
  case Plus(a: Expr, b: Expr)
  case Minus(a: Expr, b: Expr)
  case Times(a: Expr, b: Expr)
  case Divide(a: Expr, b: Expr)

object Calculator extends CalculatorInterface:
 import Expr.*

  def computeValues( // TODO
      namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] =
    namedExpressions map((string, signal) =>
        (string, Signal(eval(signal(), namedExpressions)))
    )

  def eval(expr: Expr, references: Map[String, Signal[Expr]])(using Signal.Caller): Double =
    expr match // TODO
      case Literal(v) => v
      case Ref(name) =>
        val lookup: Option[Signal[Expr]] = references.get(name)
        lookup match  // careful! avoid infinite loops by removing name from references
          case Some(v) => eval(v(), references.filter{case (key, _) => key != name})
          case None => Double.NaN
      case Plus(a, b) => eval(a, references) + eval(b, references)
      case Minus(a, b) => eval(a, references) - eval(b, references)
      case Times(a, b) => eval(a, references) * eval(b, references)
      case Divide(a, b) =>
        val denom: Double = eval(b, references)
        if (denom == 0) Double.NaN
        else eval(a, references) / denom


  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
      references: Map[String, Signal[Expr]])(using Signal.Caller): Expr =
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
