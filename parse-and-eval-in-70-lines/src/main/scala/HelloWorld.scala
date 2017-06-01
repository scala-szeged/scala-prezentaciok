import scala.util.parsing.combinator.JavaTokenParsers

/**
  * https://gist.github.com/sschaef/5529436
  */
object HelloWorld extends App with Expression with Parse with Eval {

  val env: Environment = {
    case "x" => 5
    case "y" => 7
  }

  println(eval(parseAll(expr, "100-x*y").get, env))
}

trait Expression {

  sealed abstract class Tree

  case class Add(t1: Tree, t2: Tree) extends Tree

  case class Sub(t1: Tree, t2: Tree) extends Tree

  case class Mul(t1: Tree, t2: Tree) extends Tree

  case class Div(t1: Tree, t2: Tree) extends Tree

  case class Const(v: Double) extends Tree

  case class Var(n: String) extends Tree

}

trait Parse extends JavaTokenParsers {
  this: Expression =>

  def expr: Parser[Tree] = term ~ rep("[+-]".r ~ term) ^^ {
    case t ~ ts => ts.foldLeft(t) {
      case (t1, "+" ~ t2) => Add(t1, t2)
      case (t1, "-" ~ t2) => Sub(t1, t2)
    }
  }

  def term: Parser[Tree] = factor ~ rep("[*/]".r ~ factor) ^^ {
    case t ~ ts => ts.foldLeft(t) {
      case (t1, "*" ~ t2) => Mul(t1, t2)
      case (t1, "/" ~ t2) => Div(t1, t2)
    }
  }

  def factor: Parser[Tree] = "(" ~> expr <~ ")" | const | varr

  def const: Parser[Const] = floatingPointNumber ^^ { t => Const(t.toDouble) }

  def varr: Parser[Var] = "[a-zA-Z][a-zA-Z0-9_]*".r ^^ { n => Var(n) }
}

trait Eval {
  this: Expression =>

  type Environment = String => Double

  def eval(t: Tree, env: Environment): Double = t match {
    case Add(t1, t2) => eval(t1, env) + eval(t2, env)
    case Sub(t1, t2) => eval(t1, env) - eval(t2, env)
    case Mul(t1, t2) => eval(t1, env) * eval(t2, env)
    case Div(t1, t2) => eval(t1, env) / eval(t2, env)
    case Const(v) => v
    case Var(n) => env(n)
  }
}