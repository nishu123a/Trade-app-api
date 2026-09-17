package in.nishusinha.tradeapp.notification_service.services;

import in.nishusinha.tradeapp.notification_service.dtos.PriceTick;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceAlertConsumer {
    private final AlertService alertService;

    @KafkaListener(topics = "${tradeapp.notifications.price-topic:tradeapp.market.prices}", groupId = "notification-service")
    public void consume(PriceTick priceTick) {
        alertService.evaluate(priceTick);
    }
}
