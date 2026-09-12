package in.nishu.tradex.price_stream_service;

import in.nishu.tradex.common_lib.security.JwtProperties;
import in.nishu.tradex.price_stream_service.config.PriceStreamProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		PriceStreamProperties.class,
		JwtProperties.class})
public class PriceStreamServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceStreamServicesApplication.class, args);
	}

}
