package hu.tigra.fszk.scala;

/**
 * Created with IntelliJ IDEA.
 * Date: 2017.03.30.
 * Time: 8:28
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class C_MrStocksJava {
    public static String getPriceFor(String ticker) {
        return ticker + " : " + YahooFinance.getPrice(ticker);
    }

    public static void main(String[] args) {
        try {
            List<String> tickers =
                    Arrays.asList("GOOG", "AMZN", "AAPL",
                            "MSFT", "INTC", "ORCL");

            ExecutorService executorService =
                    Executors.newFixedThreadPool(100);

            tickers.parallelStream()
                    .map(r -> executorService.submit(() -> getPriceFor(r)))
                    .map(C_MrStocksJava::getFuture)
                    .forEach(System.out::println);

            executorService.shutdown();
            executorService.awaitTermination(100, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String getFuture(Future<String> stringFuture) {
        try {
            return stringFuture.get();
        } catch (Exception e) {
            // ignored
            return "";
        }
    }

    private static class YahooFinance {
        private static double getPrice(final String ticker) {
            try {
                final URL url =
                        new URL("http://ichart.finance.yahoo.com/table.csv?s="
                                + ticker);
                final BufferedReader reader =
                        new BufferedReader(new InputStreamReader(url.openStream()));
                final String[] dataItems =
                        reader.lines().collect(toList()).get(1).split(",");
                double price =
                        Double.parseDouble(dataItems[dataItems.length - 1]);
                return price;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}