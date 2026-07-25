package in.nishu.tradex.api_gateway;

import in.nishu.tradex.common_lib.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class ApiGateewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateewayApplication.class, args);
	}

}
