package pe70


object Derivator extends App with Expression with Parse with Eval {

  //noinspection ScalaUnnecessaryParentheses
  def derive(t: Tree, env: String): Tree = t match {
    case Add(l, r) => Add(derive(l, env), derive(r, env))
    case Sub(t1, t2) => Sub(derive(t1, env), t2)
    case Mul(t1, t2) => Add(Mul(derive(t1, env), t2), Mul(derive(t2, env), t1))
    case Div(t1, t2) => Div(Sub(Mul(derive(t1, env), t2), Mul(derive(t2, env), t1)), Mul(t2, t2))
    case Var(n) if (env == n) => Const(1)
    case _ => Const(0)
  }

  println(derive(parseAll(expr, "100-x").get, "x"))
}
