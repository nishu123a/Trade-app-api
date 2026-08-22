package in.nishu.tradex.portfolio_service.dtos;

import in.nishu.tradex.portfolio_service.enums.OrderSide;

import java.math.BigDecimal;

public record TransactionResponse(
        Long id,
        Long orderId,
        OrderSide type,
        BigDecimal amount,
        String description,
        java.time.LocalDateTime at) {
}
