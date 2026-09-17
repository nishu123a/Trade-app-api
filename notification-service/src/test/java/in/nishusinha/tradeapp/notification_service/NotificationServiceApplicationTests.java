package in.nishusinha.tradeapp.notification_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.cloud.config.enabled=false",
		"spring.cloud.discovery.enabled=false",
		"eureka.client.enabled=false",
		"spring.kafka.listener.auto-startup=false",
		"spring.datasource.url=jdbc:h2:mem:notification_service_test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"tradeapp.jwt.issuer=tradeapp-test",
		"tradeapp.jwt.secret=tradeapp-test-secret-that-is-long-enough-for-hmac-signing",
		"tradeapp.jwt.access-token-minutes=30",
		"tradeapp.jwt.refresh-token-days=7"
})
class NotificationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
