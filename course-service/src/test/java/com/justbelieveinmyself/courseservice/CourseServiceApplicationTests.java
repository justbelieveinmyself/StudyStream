package com.justbelieveinmyself.courseservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(ports = {9092})
class CourseServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
