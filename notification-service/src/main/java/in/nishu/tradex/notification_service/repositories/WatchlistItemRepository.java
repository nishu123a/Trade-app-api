package in.nishu.tradex.notification_service.repositories;

import in.nishu.tradex.notification_service.entity.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistItemRepository  extends JpaRepository<WatchlistItem, Long> {
    List<WatchlistItem> findByUserIdOrderBySymbolAsc(Long id);
    Optional<WatchlistItem> findByUserIdAndSymbol(Long userId, String symbol);

    void deleteByUserIdAndSymbol(Long userId, String symbol);
    boolean existsByUserIdAndSymbol(Long userId, String symbol);
}
