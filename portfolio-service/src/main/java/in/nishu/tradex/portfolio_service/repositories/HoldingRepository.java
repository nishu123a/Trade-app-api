package in.nishu.tradex.portfolio_service.repositories;

import in.nishu.tradex.portfolio_service.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding,Long> {
    List<Holding> findByUserIdOrderBySymbolAsc(Long userId);

    Optional<Holding>findByUserIdAndSymbol(Long userId,String symbol);
}
