package training.project.price_comparison.scrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import training.project.price_comparison.model.Product;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MeeshoScraper {
    public static List<Product> scrape(String keyword) {
        List<Product> products = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-blink-features=AutomationControlled", "--no-sandbox", "--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0");

        WebDriver driver = new ChromeDriver(options);
        try {
            String searchUrl = "https://www.meesho.com/search?q=" + keyword.replace(" ", "%20");
            driver.get(searchUrl);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Wait for visible image blocks
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt]")));

            List<WebElement> images = driver.findElements(By.cssSelector("img[alt]"));

            for (WebElement img : images) {
                try {
                    WebElement anchor = img.findElement(By.xpath("ancestor::a"));
                    String title = img.getAttribute("alt");
                    String imageUrl = img.getAttribute("src");
                    String url = anchor.getAttribute("href");

                    // Try to find price nearby
                    String priceStr = "";
                    try {
                        WebElement priceElement = anchor.findElement(By.xpath(".//h5"));
                        priceStr = priceElement.getText().replace("₹", "").replace(",", "").trim();
                    } catch (Exception ignored) {}

                    if (!title.isEmpty() && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        products.add(new Product(title, price, "Meesho", url, imageUrl));
                    }
                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            System.out.println("Meesho error: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return products;
    }
}
