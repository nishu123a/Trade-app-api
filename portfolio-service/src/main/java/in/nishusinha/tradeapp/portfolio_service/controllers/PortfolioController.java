package in.nishusinha.tradeapp.portfolio_service.controllers;

import in.nishusinha.tradeapp.common_lib.security.JwtPrincipal;
import in.nishusinha.tradeapp.portfolio_service.dtos.PortfolioDtos;
import in.nishusinha.tradeapp.portfolio_service.services.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping
    PortfolioDtos.PortfolioResponse portfolio(@AuthenticationPrincipal JwtPrincipal principal) {
        return portfolioService.portfolio(principal);
    }

    @GetMapping("/summary")
    PortfolioDtos.PortfolioSummaryResponse summary(@AuthenticationPrincipal JwtPrincipal principal) {
        return portfolioService.summary(principal);
    }

    @GetMapping("/holdings")
    List<PortfolioDtos.HoldingResponse> holdings(@AuthenticationPrincipal JwtPrincipal principal) {
        return portfolioService.holdings(principal);
    }
}
