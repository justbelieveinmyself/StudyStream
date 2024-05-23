package com.justbelieveinmyself.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(ports = {9092})
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
