package in.nishu.tradex.market_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceTrick(
        String symbol,
        BigDecimal price,
        BigDecimal previousPrice,
        BigDecimal changeAmount,
        BigDecimal changePercent,
        boolean synthetic,
        LocalDateTime timestamp){

}

