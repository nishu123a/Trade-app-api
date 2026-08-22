package in.nishu.tradex.portfolio_service.repositories;

import in.nishu.tradex.portfolio_service.entity.LedgerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerTransactionRepository extends JpaRepository <LedgerTransaction,Long>{
    List<LedgerTransaction> findByUserIdOrderByCreatedAtDesc(Long userId);
}
