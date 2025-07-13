package training.project.price_comparison.model;

import jakarta.persistence.*;

@Entity
public class SearchedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String source;
    private String productUrl;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "search_history_id")
    private SearchHistory searchHistory;
}
