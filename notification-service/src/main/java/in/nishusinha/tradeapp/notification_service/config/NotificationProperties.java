package in.nishusinha.tradeapp.notification_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tradeapp.notifications")
@Data
public class NotificationProperties {
    private String priceTopic = "tradeapp.market.prices";
    private long watchlistCacheTtlMinutes = 10;
    private int notificationLimit = 100;
}
