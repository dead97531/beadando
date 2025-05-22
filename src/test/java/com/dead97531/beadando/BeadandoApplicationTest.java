package com.dead97531.beadando;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.*;

@ActiveProfiles("test")
public class BeadandoApplicationTest {

    @Test
    void contextLoads() {
    }
    @Test
    void mainRunsWithoutExceptions() {
        BeadandoApplication.main(new String[] {});
    }
}
