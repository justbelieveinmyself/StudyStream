package com.justbelieveinmyself.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "eureka.client.enabled=false"
})
class ConfigServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
