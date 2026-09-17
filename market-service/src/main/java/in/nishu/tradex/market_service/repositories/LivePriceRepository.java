package in.nishu.tradex.market_service.repositories;

import in.nishu.tradex.market_service.entity.LivePriceTick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivePriceRepository extends JpaRepository<LivePriceTick, Long> {
    Optional<LivePriceTick> findFirstBySymbolOrderByIdDesc(String symbol);
}

