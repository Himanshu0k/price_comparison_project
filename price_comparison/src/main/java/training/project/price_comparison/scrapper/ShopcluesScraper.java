package training.project.price_comparison.scrapper;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import training.project.price_comparison.model.Product;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ShopcluesScraper {
    public static List<Product> scrape(String keyword) {
        List<Product> products = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-blink-features=AutomationControlled", "--no-sandbox", "--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0");

        WebDriver driver = new ChromeDriver(options);
        try {
            String searchUrl = "https://www.shopclues.com/search?q=" + keyword;
            driver.get(searchUrl);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.column")));

            List<WebElement> items = driver.findElements(By.cssSelector("div.column"));

            for (WebElement item : items) {
                try {
                    String title = item.findElement(By.cssSelector("h2")).getText();
                    String priceStr = item.findElement(By.cssSelector("span.p_price")).getText().replace("₹", "").replace(",", "").trim();
                    String url = item.findElement(By.cssSelector("a")).getAttribute("href");
                    String imageUrl = item.findElement(By.cssSelector("img")).getAttribute("src");

                    if (!title.isEmpty() && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        products.add(new Product(title, price, "ShopClues", url, imageUrl));
                    }
                } catch (Exception ignored) {}
            }
        } finally {
            driver.quit();
        }

        return products;
    }
}
