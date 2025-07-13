package training.project.price_comparison.service;

import org.springframework.stereotype.Service;
import training.project.price_comparison.model.Product;
//import training.project.price_comparison.scrapper.AjioScraper;
import training.project.price_comparison.scrapper.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getAllPrices(String keyword) {
        List<Product> allProducts = new ArrayList<>();

        try {
            System.out.println("Working ebay scrapper");
            allProducts.addAll(EbayScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("Ebay error: " + e.getMessage());
        }

        try {
            System.out.println("Working Flipkart scrapper");
            allProducts.addAll(FlipkartScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("Flipkart error: " + e.getMessage());
        }

        try {
            System.out.println("Working amazon scrapper");
            allProducts.addAll(AmazonScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("Amazon error: " + e.getMessage());
        }

        try {
            System.out.println("Working Myntra scrapper");
            allProducts.addAll(MyntraScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("Myntra error: " + e.getMessage());
        }

        try {
            System.out.println("Working Ajio scrapper");
            allProducts.addAll(AjioScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("Ajio error: " + e.getMessage());
        }

        try {
            System.out.println("Working Meesho scrapper");
            allProducts.addAll(MeeshoScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("Meesho error: " + e.getMessage());
        }

        try {
            System.out.println("Working ShopClues scrapper");
            allProducts.addAll(ShopcluesScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("ShopClues error: " + e.getMessage());
        }

        try {
            System.out.println("Working SnapDeal scrapper");
            allProducts.addAll(SnapdealScraper.scrape(keyword));
        } catch (Exception e) {
            System.out.println("SnapDeal error: " + e.getMessage());
        }

        return allProducts;
    }

}
