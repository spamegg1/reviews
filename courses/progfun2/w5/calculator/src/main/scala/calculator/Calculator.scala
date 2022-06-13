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

  def computeValues(namedExpressions: Map[String, Signal[Expr]])
                                    : Map[String, Signal[Double]] =      // TODO
    namedExpressions map ((string, signal) =>
      (string, Signal(eval(signal(), namedExpressions))))

  def eval(expr: Expr, references: Map[String, Signal[Expr]])
          (using Signal.Caller)  : Double =                              // TODO
    expr match
      case Literal(v) => v
      case Ref(name) => references.get(name) match
        case Some(signal) =>
          eval(signal(), references filter ((k, _) => k != name))
        case None => Double.NaN
      case Plus(a, b)   => eval(a, references) + eval(b, references)
      case Minus(a, b)  => eval(a, references) - eval(b, references)
      case Times(a, b)  => eval(a, references) * eval(b, references)
      case Divide(a, b) => eval(b, references) match
        case 0     => Double.NaN
        case denom => eval(a, references) / denom

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
    references: Map[String, Signal[Expr]])(using Signal.Caller): Expr =
    references
      .get(name)
      .fold[Expr](Literal(Double.NaN))(exprSignal => exprSignal())
