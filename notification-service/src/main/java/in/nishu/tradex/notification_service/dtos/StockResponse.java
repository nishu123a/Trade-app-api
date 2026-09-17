package in.nishu.tradex.notification_service.dtos;

import java.math.BigDecimal;

public record StockResponse (
    String symbol,
    String name,
    String exchange,
    String sector,
    BigDecimal referencePrice,
    boolean synthetic
){

}
