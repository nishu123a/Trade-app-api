package in.nishu.tradex.market_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor


@Table(name="market_price_candles",
      uniqueConstraints = @UniqueConstraint(name = "uk_market_price_candles_symbol_interval_time",columnNames = {"symbol","candle_interval","candle_time"}),
        indexes = {
            @Index(name="idx_market_price_candles_symbol_interval_time",columnList = "symbol,candle_interval,candle_time"),
            @Index(name="idx_market_price_candles_time",columnList = "candle_time")
        })


public class MarketPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String symbol;

    @Column(name = "candle_interval", nullable = false, length = 16)
    private String interval;

    @Column(name = "candle_time", nullable = false)
    private LocalDateTime candletime;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal openPrice;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal highPrice;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal lowPrice;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal closePrice;

    @Column(nullable = false)
    private Long volume;


    public MarketPriceHistory( String symbol, LocalDateTime candletime, String interval, BigDecimal openPrice, BigDecimal highPrice, BigDecimal lowPrice, BigDecimal closePrice, Long volume) {
        this.symbol = symbol;
        this.candletime = candletime;
        this.interval = interval;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }
   protected MarketPriceHistory(){

   }

    public Long getId() {
        return id;
    }

    public String getInterval() {
        return interval;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDateTime getCandletime() {
        return candletime;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public Long getVolume() {
        return volume;
    }
}