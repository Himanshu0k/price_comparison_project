package training.project.price_comparison.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private double price;
    private String source;
    private String productUrl;
    private String imageUrl;
}
