package in.nishusinha.tradeapp.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false",
        "tradeapp.jwt.issuer=tradeapp-test",
        "tradeapp.jwt.secret=tradeapp-test-secret-that-is-long-enough-for-hmac-signing",
        "tradeapp.jwt.access-token-minutes=30",
        "tradeapp.jwt.refresh-token-days=7"
})
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

}
