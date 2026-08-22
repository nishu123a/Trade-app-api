package in.nishu.tradex.portfolio_service.dtos;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioDtos() {

    public record PortfolioResponse(PortfolioSummaryResponse summary, List<HoldingResponse> holdings){

    }

    public record  PortfolioSummaryResponse(
            BigDecimal cashBalance,
            BigDecimal holdingsValue,
            BigDecimal totalValue,
            BigDecimal investedValue,
            BigDecimal unrealizedPnl,
            BigDecimal unrealizedPnPercent){

    }
    public record HoldingResponse(
            String symbol,
            String stockName,
            BigDecimal quantity,
            BigDecimal averagePrice,
            BigDecimal lastPrice,
            BigDecimal investedValue,
            BigDecimal marketValue,
            BigDecimal unrealizedPnl,
            BigDecimal unrealizedPnlPercent){

    }

}
