package in.nishusinha.tradeapp.notification_service.dtos;

import java.util.List;

public class DashboardDtos {
    public record DashboardResponse(
            List<WatchlistDtos.WatchlistResponse> watchlist,
            List<AlertDtos.AlertResponse> alerts,
            List<NotificationDtos.NotificationResponse> notifications,
            List<MarketDtos.MarketMoverResponse> topGainers,
            List<MarketDtos.MarketMoverResponse> topLosers,
            List<MarketDtos.MarketTrendResponse> trendingStocks
    ) {
    }
}
