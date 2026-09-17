package in.nishusinha.tradeapp.market_service;

import in.nishusinha.tradeapp.market_service.config.MarketHistoryProperties;
import in.nishusinha.tradeapp.market_service.entity.MarketPriceHistory;
import in.nishusinha.tradeapp.market_service.repositories.MarketPriceHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:market_service_hourly_test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "tradeapp.market.history.interval=HOURLY",
        "tradeapp.market.history.hours=24",
        "tradeapp.jwt.issuer=tradeapp-test",
        "tradeapp.jwt.secret=tradeapp-test-secret-that-is-long-enough-for-hmac-signing",
        "tradeapp.jwt.access-token-minutes=30",
        "tradeapp.jwt.refresh-token-days=7"
})
class MarketHourlyTest {

    @Autowired
    private MarketHistoryProperties properties;

    @Autowired
    private MarketPriceHistoryRepository repository;

    @Test
    void testHourlyIntervalRunsWithoutCrashing() {
        assertEquals(MarketHistoryProperties.Interval.HOURLY, properties.getInterval());

        List<MarketPriceHistory> candles = repository.findBySymbolAndIntervalOrderByCandleTimeDesc(
                "INFY", "HOURLY", PageRequest.of(0, 50));

        assertFalse(candles.isEmpty(), "Hourly candles should be seeded successfully without crashing");

        for (int i = 0; i < candles.size() - 1; i++) {
            MarketPriceHistory newer = candles.get(i);
            MarketPriceHistory older = candles.get(i + 1);

            assertEquals("HOURLY", newer.getInterval());
            long diffHours = ChronoUnit.HOURS.between(older.getCandleTime(), newer.getCandleTime());
            assertEquals(1, diffHours);
            assertTrue(newer.getClosePrice().doubleValue() > 0);
            assertTrue(newer.getClosePrice().doubleValue() < 1_000_000, "Price must not overflow");
        }
    }
}

