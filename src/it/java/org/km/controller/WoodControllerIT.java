package org.km.controller;

import org.junit.jupiter.api.Test;
import org.km.db.view.CooperView;
import org.km.db.view.WoodView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.km.controller.ControllerConstants.WOOD_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=test",
        "scheduling.enabled: false"
}
)
@Rollback
public class WoodControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetWoods() {
        var response = restTemplate.getForEntity(WOOD_URL, WoodView[].class);
        WoodView woods[] = response.getBody();
        var woodList = Arrays.stream(woods).sorted((c1, c2)->c1.getId().toString().compareTo(c2.getId().toString())).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(7, woodList.size()),
                () -> assertEquals("Ясень", woodList.get(2).getName()),
                () -> assertEquals(1, woodList.get(1).getBarrelCount())
        );
    }
}
