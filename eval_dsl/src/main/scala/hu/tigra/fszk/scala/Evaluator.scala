package hu.tigra.fszk.scala


/**
  * https://gist.github.com/sschaef/5529436
  */
object Evaluator extends App with Expression with Eval_Dsl {

  val env: Environment = {
    case "x" => 5
    case "y" => 7
  }

  println(eval(
    Add(Sub(Const(100.0), Mul(Var("x"), Var("y"))), Const(5.0))
    , env))
}

trait Expression {

  sealed abstract class Tree

  sealed abstract class TreeTree(val t1: Tree, val t2: Tree) extends Tree

  object TreeTree {
    def unapply(arg: TreeTree): Option[(Tree, Tree)] = Some(arg.t1, arg.t2)
  }

  case class Add(override val t1: Tree, override val t2: Tree) extends TreeTree(t1, t2)

  case class Sub(override val t1: Tree, override val t2: Tree) extends TreeTree(t1, t2)

  case class Mul(override val t1: Tree, override val t2: Tree) extends TreeTree(t1, t2)

  case class Div(override val t1: Tree, override val t2: Tree) extends TreeTree(t1, t2)

  case class Const(v: Double) extends Tree

  case class Var(n: String) extends Tree

}


trait Eval_Dsl {
  this: Expression =>

  type Environment = String => Double


  //noinspection LanguageFeature
  def eval(t: Tree, env: Environment): Double = {


    def on[T <: TreeTree](op: (Double, Double) => Double)(implicit m: Manifest[T]): Option[Double] = t match {
      case _: T => Some(op(eval(t.asInstanceOf[T].t1, env), eval(t.asInstanceOf[T].t2, env)))
      case _ => None
    }

    def const_orElse_var: Option[Double] = t match {
      case Const(v) => Some(v)
      case Var(n) => Some(env(n))
      case _ => None
    }


    on[Add](_ + _) orElse on[Sub](_ - _) orElse on[Mul](_ * _) orElse on[Div](_ / _) orElse const_orElse_var get
  }
}


trait Eval_With {
  this: Expression =>

  type Environment = String => Double


  def eval(t: Tree, env: Environment): Double = {

    def evalWith(op: (Double, Double) => Double, t1: Tree, t2: Tree): Double = op(eval(t1, env), eval(t2, env))


    t match {

      case Add(l, r) => evalWith(_ + _, l, r)
      case Sub(l, r) => evalWith(_ - _, l, r)
      case Mul(l, r) => evalWith(_ * _, l, r)
      case Div(l, r) => evalWith(_ / _, l, r)

      case Const(v) => v
      case Var(n) => env(n)
    }
  }
}
