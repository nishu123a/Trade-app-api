package in.nishu.tradex.portfolio_service.controllers;

import in.nishu.tradex.common_lib.security.JwtPrincipal;
import in.nishu.tradex.portfolio_service.dtos.TransactionResponse;
import in.nishu.tradex.portfolio_service.services.PortfolioService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final PortfolioService portfolioService;

    @GetMapping
    List<TransactionResponse> transactions(@AuthenticationPrincipal JwtPrincipal principal){
        return portfolioService.transactions(principal);
    }
}
