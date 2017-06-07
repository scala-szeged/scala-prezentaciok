package hu.tigra.fszk.scala


object A3_Performance {

  case class Person(name: String, age: Int) {
    def getName: String = name

    def getAge: Int = age
  }

  def createPeople: List[Person] =
    List(
      Person("Tomika", 12),
      Person("Petike", 14),
      Person("Jancsi", 21),
      Person("Julcsi", 18),
      Person("Paula", 20),
      Person("Paulina", 31),
      Person("McAffee", 30),
      Person("McDonalds", 69),
      Person("McMester", 16)
    )

  def main(args: Array[String]): Unit = {
    val people = createPeople

    val sfunc = System.currentTimeMillis

    people
      .filter(person => person.age > 17)
      .map(_.name)
      .map(name => name.toUpperCase)
      .headOption.foreach(println)

    val efunc = System.currentTimeMillis
    System.out.println(efunc - sfunc)
    System.out.println()
  }
}
