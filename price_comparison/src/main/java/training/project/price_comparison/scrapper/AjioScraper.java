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

public class AjioScraper {
    public static List<Product> scrape(String keyword) {
        List<Product> products = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-blink-features=AutomationControlled", "--no-sandbox", "--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0");

        WebDriver driver = new ChromeDriver(options);

        try {
            String searchUrl = "https://www.ajio.com/search/?text=" + keyword;
            driver.get(searchUrl);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.item")));

            List<WebElement> items = driver.findElements(By.cssSelector("div.item"));

            for (WebElement item : items) {
                try {
                    String title = item.findElement(By.cssSelector("div.nameCls")).getText();
                    String priceStr = "";

                    // First check if discounted price is available
                    try {
                        priceStr = item.findElement(By.cssSelector("span.price")).getText().replace("₹", "").replace(",", "");
                    } catch (Exception e1) {
                        // If not, fall back to MRP
                        priceStr = item.findElement(By.cssSelector("span.mrp")).getText().replace("₹", "").replace(",", "");
                    }

                    String url = item.findElement(By.tagName("a")).getAttribute("href");
                    if (!url.startsWith("https")) {
                        url = "https://www.ajio.com" + url;
                    }

                    String imageUrl = item.findElement(By.cssSelector("img")).getAttribute("src");

                    if (!title.isEmpty() && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        products.add(new Product(title, price, "Ajio", url, imageUrl));
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("AJIO error: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return products;
    }
}
