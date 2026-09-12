package in.nishu.tradex.price_stream_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "tradex.prices")//eureka-server
@Data
public class PriceStreamProperties {
    private String topic="tradex.market.prices";
    private long geneerationIntervals=2000;
    private int historyLimit=500;
    private List<String> symbols=List.of(
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
            "TITAN",
            "ASIANPAINT",
            "NIFTYBEES",
            "BANKBEES");
}
