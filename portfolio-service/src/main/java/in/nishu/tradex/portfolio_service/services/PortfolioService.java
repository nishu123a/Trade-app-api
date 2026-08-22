package in.nishu.tradex.portfolio_service.services;

import in.nishu.tradex.common_lib.security.JwtPrincipal;
import in.nishu.tradex.portfolio_service.clients.MarketClient;
import in.nishu.tradex.portfolio_service.dtos.OrderDtos;
import in.nishu.tradex.portfolio_service.dtos.StockResponse;
import in.nishu.tradex.portfolio_service.dtos.TransactionResponse;
import in.nishu.tradex.portfolio_service.entity.Holding;
import in.nishu.tradex.portfolio_service.entity.LedgerTransaction;
import in.nishu.tradex.portfolio_service.entity.PortfolioAccount;
import in.nishu.tradex.portfolio_service.entity.TradeOrder;
import in.nishu.tradex.portfolio_service.enums.OrderSide;
import in.nishu.tradex.portfolio_service.repositories.HoldingRepository;
import in.nishu.tradex.portfolio_service.repositories.LedgerTransactionRepository;
import in.nishu.tradex.portfolio_service.repositories.PortfolioAccountRepository;
import in.nishu.tradex.portfolio_service.repositories.TradeOrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import in.nishu.tradex.portfolio_service.dtos.PortfolioDtos;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class PortfolioService {
    private static final BigDecimal ONE_HUNDRED =new BigDecimal(100);
    private final HoldingRepository holdingRepository;
    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final PortfolioAccountRepository portfolioAccountRepository;
    private final TradeOrderRepository tradeOrderRepository;
    private final MarketClient marketClient;
    private final BigDecimal startingCash;

    public PortfolioService(HoldingRepository holdingRepository,
                            LedgerTransactionRepository ledgerTransactionRepository,
                            PortfolioAccountRepository portfolioAccountRepository, TradeOrderRepository tradeOrderRepository,
                            MarketClient marketClient, @Value("${tradex.trading.starting-cash:10_00_000.00") BigDecimal startingCash) {
        this.holdingRepository = holdingRepository;
        this.ledgerTransactionRepository = ledgerTransactionRepository;
        this.portfolioAccountRepository = portfolioAccountRepository;
        this.tradeOrderRepository = tradeOrderRepository;
        this.marketClient = marketClient;
        this.startingCash = startingCash;
    }

    @Transactional
    public  PortfolioDtos.PortfolioResponse portfolio(JwtPrincipal principal){
        PortfolioAccount account=getOrCreateAccount(principal.userId());
        List<PortfolioDtos.HoldingResponse> holdings=holdings(principal);

        return new PortfolioDtos.PortfolioResponse(summaryResponse(account,holdings),holdings);
    }

    @Transactional
    public  PortfolioDtos.PortfolioSummaryResponse summary(JwtPrincipal principal){

        PortfolioAccount account=getOrCreateAccount(principal.userId());
        return summaryResponse(account,holdings(principal));
    }

    @Transactional(readOnly = true)
    public List<PortfolioDtos.HoldingResponse> holdings(JwtPrincipal principal){
        return holdingRepository.findByUserIdOrderBySymbolAsc(principal.userId())
                .stream().map(this::toHoldingResponse)
                .toList();
    }

    @Transactional
    public OrderDtos.OrderResponse buy(JwtPrincipal principal,OrderDtos.OrderRequest request){
        String symbol=normalizeSymbol(request.symbol());
        BigDecimal quantity=quantity(request.quantity());

        StockResponse stock=marketClient.getStock(symbol);
        BigDecimal price=price(stock.referencePrice());
        BigDecimal totalAmount=money(price.multiply(quantity));

        PortfolioAccount account=getOrCreateAccount(principal.userId());

        if(account.getCashBalancce().compareTo(totalAmount)<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient Virtual cash");
        }

        account.debit(totalAmount);
        Holding holding=holdingRepository.findByUserIdAndSymbol(principal.userId(),stock.symbol())
        .orElseGet(() -> Holding.builder()
                        .userId(principal.userId())
                        .symbol(stock.symbol())
                        .stockName(stock.name())
                        .quantity(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP))
                        .averagePrice(price)
                        .build());
        holding.buy(quantity,price,stock.name());
        holdingRepository.save(holding);

        TradeOrder order=tradeOrderRepository.save(TradeOrder.builder()
                .userId(principal.userId())
                .symbol(stock.symbol())
                .stockName(stock.name())
                .side(OrderSide.BUY)
                .quantity(quantity)
                .price(price)
                .totalAmount(totalAmount)
                .build());

        ledgerTransactionRepository.save(LedgerTransaction.builder()
                .userId(principal.userId())
                .orderId(order.getId())
                .type(OrderSide.BUY)
                .amount(totalAmount)
                .description("Bought "+quantity+" stocks of "+stock.symbol())
                .build());

        return toOrderResponse(order);
    }

    @Transactional
    public OrderDtos.OrderResponse sell(JwtPrincipal principal,OrderDtos.OrderRequest request){
        String symbol=normalizeSymbol(request.symbol());
        BigDecimal quantity =quantity(request.quantity());
        StockResponse stock=marketClient.getStock(symbol);
        BigDecimal price=price(stock.referencePrice());
        BigDecimal totalAmount=money(price.multiply(quantity));

        PortfolioAccount account=getOrCreateAccount(principal.userId());
        Holding holding=holdingRepository.findByUserIdAndSymbol(principal.userId(),stock.symbol())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient quantity to sell"));
        if(holding.getQuantity().compareTo(quantity)<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient quantity to sell");
        }
        holding.sell(quantity);

        if(holding.isEmpty()){
            holdingRepository.delete(holding);
        }
        account.credit(totalAmount);

        TradeOrder order=tradeOrderRepository.save(TradeOrder.builder()
        .userId(principal.userId())
        .symbol(stock.symbol())
        .stockName(stock.name())
        .side(OrderSide.SELL)
        .quantity(quantity)
        .price(price)
        .totalAmount(totalAmount)
        .build());

        ledgerTransactionRepository.save(LedgerTransaction.builder()
                .userId(principal.userId())
                .orderId(order.getId())
                .type(OrderSide.SELL)
                .amount(totalAmount)
                .description("Sold " + quantity + " stocks of " + stock.symbol())
                .build());

        return toOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDtos.OrderResponse> orderHistory(JwtPrincipal principal){
        return tradeOrderRepository.findByUserIdOrderByCreatedAtDesc(principal.userId())
                .stream().map(this::toOrderResponse).toList();
    }
    @Transactional(readOnly = true)

    public List<TransactionResponse> transactions(JwtPrincipal principal){
        return ledgerTransactionRepository.findByUserIdOrderByCreatedAtDesc(principal.userId())
                .stream().map(this::toTransactionResponse).toList();
    }

    //DTO mappers
    private PortfolioDtos.PortfolioSummaryResponse summaryResponse(PortfolioAccount account,List<PortfolioDtos.HoldingResponse> holdings){
        BigDecimal holdingsValue=money(holdings.stream()
                .map(PortfolioDtos.HoldingResponse::marketValue)
                .reduce(BigDecimal.ZERO,BigDecimal::add));

        BigDecimal investedValue=money(holdings.stream()
                .map(PortfolioDtos.HoldingResponse::investedValue).
                reduce(BigDecimal.ZERO,BigDecimal::add));

        BigDecimal unrealizedPnl=money(holdingsValue.subtract(investedValue));
        return new PortfolioDtos.PortfolioSummaryResponse(
                account.getCashBalancce(),
                holdingsValue,
                money(account.getCashBalancce().add(holdingsValue)),
                investedValue,
                unrealizedPnl,
                percent(unrealizedPnl,investedValue)
        );
    }
    private PortfolioDtos.HoldingResponse toHoldingResponse(Holding holding){
        StockResponse stock=marketClient.getStock(holding.getSymbol());
        BigDecimal lastPrice=price(stock.referencePrice());
        BigDecimal investedValue=money(holding.getAveragePrice().multiply(holding.getQuantity()));
        BigDecimal marketValue=money(lastPrice.multiply(holding.getQuantity()));
        BigDecimal pnl=money(marketValue.subtract(investedValue));


        return new PortfolioDtos.HoldingResponse(
                holding.getSymbol(),
                stock.name(),
                holding.getQuantity(),
                holding.getAveragePrice(),
                lastPrice,
                investedValue,
                marketValue,
                pnl,
                percent(pnl,investedValue)
        );
    }
    private OrderDtos.OrderResponse toOrderResponse(TradeOrder order){
        return new OrderDtos.OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getSymbol(),
                order.getStockName(),
                order.getSide(),
                order.getQuantity(),
                order.getPrice(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
    public TransactionResponse toTransactionResponse(LedgerTransaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getOrderId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getCreatedAt()
        );
    }

    private String normalizeSymbol(String symbol){
        return symbol.trim().toUpperCase();
    }
    private BigDecimal quantity(BigDecimal value){
        return value.setScale(4,RoundingMode.HALF_UP);
    }
    private  BigDecimal price(BigDecimal value){
        return value.setScale(4,RoundingMode.HALF_UP);
    }

    private BigDecimal money(BigDecimal value){
        return value.setScale(2,RoundingMode.HALF_UP);
    }
    private BigDecimal percent(BigDecimal amount,BigDecimal base){
        if(base.compareTo(BigDecimal.ZERO)==0)
            return BigDecimal.ZERO.setScale(2,RoundingMode.HALF_UP);

        return amount.multiply(ONE_HUNDRED).divide(base,2,RoundingMode.HALF_UP);
    }

    private PortfolioAccount getOrCreateAccount(Long userId){
        return portfolioAccountRepository.findByUserId(userId)
                .orElseGet(() -> portfolioAccountRepository.save(PortfolioAccount.builder()
                        .userId(userId)
                        .cashBalancce(startingCash)
                        .build()));
    }

}
