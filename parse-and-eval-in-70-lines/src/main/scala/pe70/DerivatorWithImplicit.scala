package pe70


object DerivatorWithImplicit extends App with Expression with Parse with Eval {

  whereItIsUSed()


  type DeriveBy = String

  object WhereItIsDeclared {

    //noinspection ScalaUnnecessaryParentheses
    def derive(t: Tree)(implicit deriveBy: DeriveBy): Tree = t match {
      case Add(l, r) => Add(derive(l), derive(r))
      case Sub(t1, t2) => Sub(derive(t1), derive(t2))
      case Mul(t1, t2) => Add(Mul(derive(t1), t2), Mul(derive(t2), t1))
      case Div(t1, t2) => Div(Sub(Mul(derive(t1), t2), Mul(derive(t2), t1)), Mul(t2, t2))
      case Var(n) if (deriveBy == n) => Const(1)
      case _ => Const(0)
    }
  }


  def whereItIsUSed(): Unit = {

    import WhereItIsDeclared.derive

    val tree = parseAll(expr, "100-x").get

    implicit val deriveBy: DeriveBy = "x"
    println(derive(tree))
  }
}
