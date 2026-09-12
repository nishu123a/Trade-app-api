package in.nishu.tradex.price_stream_service.services;

import in.nishu.tradex.price_stream_service.dtos.PriceResponse;
import in.nishu.tradex.price_stream_service.dtos.PriceTick;
import in.nishu.tradex.price_stream_service.entity.PriceHistory;
import in.nishu.tradex.price_stream_service.repositories.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PriceEventHandler {
    private final PriceCacheService priceCachService;
    private final PriceHistoryRepository priceHistoryRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void handle(PriceTick tick){
        priceCachService.put(tick);
        priceHistoryRepository.save(toHistory(tick));
        PriceResponse response=toResponse(tick);
        messagingTemplate.convertAndSend("/topic/market",response);
        messagingTemplate.convertAndSend("/topic/"+tick.symbol());
    }

    private PriceHistory toHistory(PriceTick tick){
        return PriceHistory
                .builder()
                .symbol(tick.symbol())
                .price(tick.price())
                .previousPrice(tick.previousPrice())
                .changeAmount(tick.changeAmount())
                .changePercent(tick.changePercent())
                .synthetic(tick.synthetic())
                .timestamp(tick.timestamp())
                .build();
    }

    private PriceResponse toResponse(PriceTick tick){
        return new PriceResponse(
                tick.symbol(),
                tick.price(),
                tick.previousPrice(),
                tick.changeAmount(),
                tick.changePercent(),
                tick.synthetic(),
                tick.timestamp());
    }

}
