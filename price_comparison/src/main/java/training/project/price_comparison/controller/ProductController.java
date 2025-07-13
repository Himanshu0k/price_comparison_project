package training.project.price_comparison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.project.price_comparison.model.Product;
import training.project.price_comparison.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // enable frontend access
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/compare")
    public ResponseEntity<List<Product>> compare(@RequestParam String keyword) {
        System.out.println("Product Controller");
        List<Product> products = productService.getAllPrices(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
