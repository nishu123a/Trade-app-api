package in.nishu.tradex.market_service.repositories;
import org.springframework.data.domain.Page;
import in.nishu.tradex.market_service.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,String> {
    Page<Stock> findBySymbolContainingIgnoreCaseOrNameContainingIgnoreCase(String symbol, String name, Pageable pageable);
    List<Stock> findTop10BySymbolContainingIgnoreCaseOrNameContainingIgnoreCaseOrderedBySymbolsAsc(String symbol, String name);
}
