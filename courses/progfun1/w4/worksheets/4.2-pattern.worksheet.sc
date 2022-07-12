trait Expr

case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Var(name: String) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr

def eval(e: Expr): Int = e match
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
  case Var(name) => ???
  case Prod(e1, e2) => eval(e1) * eval(e2)

val e = Prod(Number(3), Sum(Number(1), Number(2)))
eval(e)

def show(e: Expr): String = e match
  case Number(n) => s"$n"
  case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
  case Var(name) => name
  case Prod(e1, e2) => s"${showP(e1)} * ${showP(e2)}"

def showP(e: Expr): String = e match
  case Sum(_, _) => s"(${show(e)})"
  case _ => show(e)

val expr = Sum(Number(1), Number(1))
val expr1 = Prod(expr, Var("x"))
show(expr1)
