package training.project.price_comparison.scrapper;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import training.project.price_comparison.model.Product;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SnapdealScraper {
    public static List<Product> scrape(String keyword) {
        List<Product> products = new ArrayList<>();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-blink-features=AutomationControlled", "--no-sandbox", "--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("https://www.snapdeal.com/search?keyword=" + keyword);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-tuple-listing")));

            List<WebElement> items = driver.findElements(By.cssSelector("div.product-tuple-listing"));

            for (WebElement item : items) {
                try {
                    String title = item.findElement(By.cssSelector("p.product-title")).getText();
                    String priceStr = item.findElement(By.cssSelector("span.lfloat.product-price")).getText().replace("Rs. ", "").replace(",", "").trim();
                    String url = item.findElement(By.cssSelector("a.dp-widget-link")).getAttribute("href");
                    String imageUrl = item.findElement(By.cssSelector("img.product-image")).getAttribute("src");

                    if (!title.isEmpty() && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        products.add(new Product(title, price, "Snapdeal", url, imageUrl));
                    }
                } catch (Exception ignored) {}
            }
        } finally {
            driver.quit();
        }
        return products;
    }
}
