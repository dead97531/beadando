package com.dead97531.beadando;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=local")
public class BeadandoApplicationTest {

    @Test
    void contextLoads() {
    }
    @Test
    void mainRunsWithoutExceptions() {
        BeadandoApplication.main(new String[] {});
    }
}
