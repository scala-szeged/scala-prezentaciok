package pe70

object Derivator extends App with Expression with Parse with Eval {

  //noinspection ScalaUnnecessaryParentheses
  def derive(t: Tree, deriveBy: String): Tree = t match {
    case Add(l, r) => Add(derive(l, deriveBy), derive(r, deriveBy))
    case Sub(t1, t2) => Sub(derive(t1, deriveBy), derive(t2, deriveBy))
    case Mul(t1, t2) => Add(Mul(derive(t1, deriveBy), t2), Mul(derive(t2, deriveBy), t1))
    case Div(t1, t2) => Div(Sub(Mul(derive(t1, deriveBy), t2), Mul(derive(t2, deriveBy), t1)), Mul(t2, t2))
    case Var(n) if (deriveBy == n) => Const(1)
    case _ => Const(0)
  }

  println(derive(parseAll(expr, "100-x").get, "x"))
}
