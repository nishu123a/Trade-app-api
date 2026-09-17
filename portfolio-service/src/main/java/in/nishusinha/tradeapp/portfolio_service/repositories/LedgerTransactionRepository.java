package in.nishusinha.tradeapp.portfolio_service.repositories;

import in.nishusinha.tradeapp.portfolio_service.entity.LedgerTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerTransactionRepository extends JpaRepository<LedgerTransaction, Long> {
    List<LedgerTransaction> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<LedgerTransaction> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}

