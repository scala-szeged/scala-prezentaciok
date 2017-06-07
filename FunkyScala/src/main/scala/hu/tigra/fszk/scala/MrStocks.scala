package hu.tigra.fszk.scala


object MrStocks extends App {

  import scala.collection.GenSeq


  def quandlGetYearEndClosingPrice(symbol: String) = {
    val url =
      s"http://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY" +
        s"&symbol=$symbol&interval=1min&apikey=BXtYhdeyDHgj1_Kn5bz6"

    val data = scala.io.Source.fromURL(url).mkString.lines.toList
    val price = data(12).split("\"")(3).toDouble
    price
  }

  def findMax(symbols: GenSeq[String]) = {
    val startTime = System.currentTimeMillis()

    val (topStock, topPrice) =
      symbols
        .map { symbol => (symbol, quandlGetYearEndClosingPrice(symbol)) }
        .maxBy { symbolAndPrice => symbolAndPrice._2 }

    println(s"Top stock is $topStock closing at price $$$topPrice")
    val endTime = System.currentTimeMillis()
    println(s"${endTime - startTime} milliseconds")
  }

  val symbols = List("GOOG", "INTC", "AMD", "AAPL", "AMZN", "IBM", "ORCL", "MSFT")

  findMax(symbols)
}