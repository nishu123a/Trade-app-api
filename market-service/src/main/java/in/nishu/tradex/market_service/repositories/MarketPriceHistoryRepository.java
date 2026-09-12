package in.nishu.tradex.market_service.repositories;

import in.nishu.tradex.market_service.entity.MarketPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MarketPriceHistoryRepository  extends JpaRepository<MarketPriceHistory,Long> {
    List<MarketPriceHistory> findBySymbolAndIntervalAndCandletimeBetweenOrderByCandletimeAsc(String symbol,String interval,
                                                              LocalDateTime startTime,
                                                              LocalDateTime endTime);
    List<MarketPriceHistory> findBySymbolAndIntervalOrderByCandletimeDesc(String symbol,String interval);
    Optional<MarketPriceHistory> findFirstBySymbolAndIntervalOrderByCandletimeDesc(String symbol, String interval);

    long countBySymbolAndIntervalAndCandletimeBetween(String symbol,
                                                      String interval,
                                                      LocalDateTime startTime,
                                                      LocalDateTime endTime);

    Optional<MarketPriceHistory> findFirstBySymbolAndIntervalOrderByCandletimeAsc(String symbol,String interval);

    @Query("select h.candletime from MarketPriceHistory h where h.symbol = :symbol and h.interval = :interval and h.candletime between :startTime and :endTime")
    Set<LocalDateTime> findExistingTimes(@Param("symbol") String symbol,
                                         @Param("interval") String interval,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);

    @Query("""
                    select h from MarketPriceHistory h
                    where h.symbol in :symbols
                    and h.interval= :interval
                    and h.candletime=(
                     select max(innerHistory.candletime)
                     from MarketPriceHistory innerHistory
                     where innerHistory.symbol=h.symbol
                     and innerHistory.interval=  :interval
                    )
            """)
    List<MarketPriceHistory> findLatestForSymbols(@Param("symbols") Collection<String> symbols,
                                                  @Param("interval") String interval);


}
