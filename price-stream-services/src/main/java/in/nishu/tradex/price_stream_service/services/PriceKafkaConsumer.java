

package in.nishu.tradex.price_stream_service.services;

import in.nishu.tradex.price_stream_service.dtos.PriceTick;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceKafkaConsumer {
    private final PriceEventHandler priceEventHandler;

    @KafkaListener(topics = "${tradex.prices.topic:tradex.market.prices}",groupId = "prices-stream-service")
    public void consume(PriceTick tick){
        priceEventHandler.handle(tick);
    }
}

