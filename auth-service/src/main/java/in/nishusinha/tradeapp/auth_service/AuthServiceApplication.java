package in.nishusinha.tradeapp.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import in.nishusinha.tradeapp.common_lib.security.JwtProperties;

@SpringBootApplication(scanBasePackages = {"in.nishusinha.tradeapp.auth_service", "in.nishusinha.tradeapp.common_lib"})
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
