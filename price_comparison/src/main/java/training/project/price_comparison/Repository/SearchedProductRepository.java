package training.project.price_comparison.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.project.price_comparison.model.SearchedProduct;

public interface SearchedProductRepository extends JpaRepository<SearchedProduct, Long> {}