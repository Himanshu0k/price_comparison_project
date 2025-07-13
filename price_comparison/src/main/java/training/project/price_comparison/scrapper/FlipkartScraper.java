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

public class FlipkartScraper {
    public static List<Product> scrape(String keyword) {
        List<Product> products = new ArrayList<>();
        WebDriver driver = null;

        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu", "--no-sandbox", "--window-size=1920,1080");
            options.addArguments("user-agent=Mozilla/5.0");

            driver = new ChromeDriver(options);
            String url = "https://www.flipkart.com/search?q=" + keyword.replace(" ", "+");
            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Close login popup
            try {
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button._2KpZ6l._2doB4z")));
                closeButton.click();
            } catch (Exception ignored) {}

            // Wait for products to load (grid or list layout)
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._2kHMtA")),   // list
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._1xHGtK"))    // grid
            ));

            List<WebElement> items = driver.findElements(By.cssSelector("div._2kHMtA")); // list
            if (items.isEmpty()) {
                items = driver.findElements(By.cssSelector("div._1xHGtK")); // grid
            }

            for (WebElement item : items) {
                try {
                    String title = "";
                    try {
                        title = item.findElement(By.cssSelector("div._4rR01T")).getText(); // list
                    } catch (NoSuchElementException e) {
                        title = item.findElement(By.cssSelector("a.IRpwTa")).getText(); // grid
                    }

                    String priceStr = item.findElement(By.cssSelector("div._30jeq3")).getText().replace("₹", "").replace(",", "").trim();

                    String productUrl = item.findElement(By.tagName("a")).getAttribute("href");
                    if (!productUrl.startsWith("http")) {
                        productUrl = "https://www.flipkart.com" + productUrl;
                    }

                    String imageUrl = "";
                    try {
                        imageUrl = item.findElement(By.cssSelector("img.DByuf4")).getAttribute("src"); // grid view image
                    } catch (Exception e) {
                        imageUrl = item.findElement(By.cssSelector("img")).getAttribute("src"); // fallback
                    }

                    if (!title.isEmpty() && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        products.add(new Product(title, price, "Flipkart", productUrl, imageUrl));
                    }
                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            System.out.println("Flipkart error: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

        return products;
    }
}
