package training.project.price_comparison.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import training.project.price_comparison.model.Product;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AmazonScraper {
    public static List<Product> scrape(String keyword) throws IOException {
        List<Product> products = new ArrayList<>();
        String searchUrl = "https://www.amazon.in/s?k=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        Document doc = Jsoup.connect(searchUrl)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        Elements items = doc.select(".s-result-item");

        for (Element item : items) {
            String title = item.select("h2 span").text();
            String priceStr = item.select(".a-price-whole").text().replace(",", "");
            String url = "https://www.amazon.in" + item.select("a").attr("href");
            String imageUrl = item.select("img.s-image").attr("src");

            if (!title.isEmpty() && !priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    products.add(new Product(title, price, "Amazon", url, imageUrl));
                } catch (NumberFormatException ignored) {}
            }
        }
        return products;
    }
}
