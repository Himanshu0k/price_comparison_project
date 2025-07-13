package training.project.price_comparison.scrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import training.project.price_comparison.model.Product;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MyntraScraper {
    public static List<Product> scrape(String keyword) {
        List<Product> products = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-blink-features=AutomationControlled", "--no-sandbox", "--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0");

        WebDriver driver = new ChromeDriver(options);
        try {
            String searchUrl = "https://www.myntra.com/search?q=" + keyword;
            driver.get(searchUrl);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.product-base")));

            List<WebElement> items = driver.findElements(By.cssSelector("li.product-base"));

            for (WebElement item : items) {
                try {
                    String title = item.findElement(By.cssSelector("h4.product-product")).getText();
                    String priceStr = item.findElement(By.cssSelector("span.product-discountedPrice")).getText().replace("Rs. ", "").replace(",", "");
                    if (priceStr.isEmpty()) {
                        priceStr = item.findElement(By.cssSelector("span.product-strike")).getText().replace("Rs. ", "").replace(",", "");
                    }

                    String url = item.findElement(By.cssSelector("a")).getAttribute("href");
                    if (!url.startsWith("https")) {
                        url = "https://www.myntra.com" + url;
                    }

                    String imageUrl = item.findElement(By.cssSelector("img.img-responsive")).getAttribute("src");

                    if (!title.isEmpty() && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        products.add(new Product(title, price, "Myntra", url, imageUrl));
                    }
                } catch (Exception ignored) {}
            }

        } finally {
            driver.quit();
        }

        return products;
    }
}
