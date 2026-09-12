package in.nishu.tradex.portfolio_service.controllers;

import in.nishu.tradex.common_lib.security.JwtPrincipal;
import in.nishu.tradex.portfolio_service.dtos.OrderDtos;
import in.nishu.tradex.portfolio_service.services.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/orders","/api/orders"})
@RequiredArgsConstructor
public class OrderController {
    private final PortfolioService portfolioService;

    @PostMapping("/buy")
    OrderDtos.OrderResponse buy(@AuthenticationPrincipal JwtPrincipal principal, @Valid @RequestBody OrderDtos.OrderRequest request){
        return portfolioService.buy(principal,request);
    }

    @PostMapping("/sell")
    OrderDtos.OrderResponse sell(@AuthenticationPrincipal JwtPrincipal principal,@Valid @RequestBody OrderDtos.OrderRequest request){
        return portfolioService.sell(principal,request);
    }
    @GetMapping("/history")
    List<OrderDtos.OrderResponse> history(@AuthenticationPrincipal JwtPrincipal principal){
        return portfolioService.orderHistory(principal);
    }
}