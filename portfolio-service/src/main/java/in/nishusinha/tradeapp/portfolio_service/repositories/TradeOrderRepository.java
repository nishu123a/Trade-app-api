package in.nishusinha.tradeapp.portfolio_service.repositories;

import in.nishusinha.tradeapp.portfolio_service.entity.TradeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
    List<TradeOrder> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<TradeOrder> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}

