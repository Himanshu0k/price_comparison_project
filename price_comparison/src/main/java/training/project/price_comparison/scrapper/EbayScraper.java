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

public class EbayScraper {
    public static List<Product> scrape(String keyword) throws IOException {
        List<Product> products = new ArrayList<>();
        String searchUrl = "https://www.ebay.com/sch/i.html?_nkw=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        Document doc = Jsoup.connect(searchUrl)
                            .userAgent("Mozilla/5.0")
                            .timeout(10000)
                            .get();

        Elements items = doc.select(".s-item");

        for (Element item : items) {
            String title = item.select(".s-item__title").text();
            String priceStr = item.select(".s-item__price").text().replaceAll("[^0-9.]", "");
            String url = item.select("a.s-item__link").attr("href");
            String imageUrl = item.select(".s-item__image-img").attr("src");

            if (!title.isEmpty() && !priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    products.add(new Product(title, price, "eBay", url, imageUrl));
                } catch (NumberFormatException ignored) {}
            }
        }
        return products;
    }
}
