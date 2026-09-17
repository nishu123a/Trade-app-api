package in.nishusinha.tradeapp.price_stream_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "tradeapp.prices")
@Data
public class PriceStreamProperties {
    private String topic = "tradeapp.market.prices";
    private long generationIntervalMs = 2000;
    private int historyLimit = 500;
    private List<String> symbols = List.of(
            "RELIANCE",
            "TCS",
            "INFY",
            "HDFCBANK",
            "ICICIBANK",
            "SBIN",
            "ITC",
            "LT",
            "AXISBANK",
            "BHARTIARTL",
            "MARUTI",
            "TITAN",
            "ASIANPAINT",
            "NIFTYBEES",
            "BANKBEES");
}
