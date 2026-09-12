package in.nishu.tradex.market_service.config;

import in.nishu.tradex.market_service.catalog.SupportedStockCatalog;
import in.nishu.tradex.market_service.entity.Stock;
import in.nishu.tradex.market_service.repositories.StockRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class StockDataSeeder {
    @Bean
    SmartInitializingSingleton seedStocks(StockRepository stockRepository, SupportedStockCatalog supportedStockCatalog){
        return ()->{
            List<Stock> supportedStocks=supportedStockCatalog.stocks().stream()
                    .map(SupportedStockCatalog.StockSeed::toEntity).toList();
            stockRepository.saveAll(supportedStocks);
        };
    }

//    private Stock stock(String symbol, String name, String exchange, String sector, String price, boolean synthetic){
//        return Stock.builder()
//                .symbol(symbol)
//                .name(name)
//                .exchange(exchange)
//                .sector(sector)
//                .referencePrice(new BigDecimal(price))
//                .synthetic(synthetic)
//                .build();
//        }
}
