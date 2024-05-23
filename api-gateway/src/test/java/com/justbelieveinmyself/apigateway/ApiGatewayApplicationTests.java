package com.justbelieveinmyself.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"jwt.secret=emptysecret"
}) //To Load context without config-server
class ApiGatewayApplicationTests {

	@Test
	void contextLoads() {
	}

}
