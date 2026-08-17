package in.nishu.tradex.portfolio_service;

import in.nishu.tradex.common_lib.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(JwtProperties.class)
public class PortfolioServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(PortfolioServiceApplication.class, args);
	}

}
