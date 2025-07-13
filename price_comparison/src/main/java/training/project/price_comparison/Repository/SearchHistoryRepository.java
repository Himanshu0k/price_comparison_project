package training.project.price_comparison.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.project.price_comparison.model.SearchHistory;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserId(Long userId);
}