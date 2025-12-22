package org.km.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.test.annotation.Rollback;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=test",
        "scheduling.enabled: false"
}
)
@Rollback
public class DrinkControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetDrinks() {

    }
}
