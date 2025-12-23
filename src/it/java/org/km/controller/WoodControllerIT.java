package org.km.controller;

import org.junit.jupiter.api.Test;
import org.km.db.entity.Wood;
import org.km.db.view.WoodView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.km.controller.ControllerConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=integration",
        "scheduling.enabled: false"
}
)

@ActiveProfiles("integration")

public class WoodControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetWoods() {
        var response = testRestTemplate.getForEntity(WOOD_URL, WoodView[].class);
        WoodView woods[] = response.getBody();
        var woodList = Arrays.stream(woods).sorted((c1, c2)->c1.getId().toString().compareTo(c2.getId().toString())).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(7, woodList.size()),
                () -> assertEquals("Ясень", woodList.get(2).getName()),
                () -> assertEquals(1, woodList.get(1).getBarrelCount())
        );
    }

    @Test
    public void testGetWood() {
        var response = testRestTemplate.getForEntity(WOOD_URL + "/3", WoodView.class);
        WoodView woodView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(3, woodView.getId()),
                () -> assertEquals("Ясень", woodView.getName()),
                () -> assertEquals(2, woodView.getBarrelCount())
        );
    }

    @Test
    public void testGetWood_negative_not_found() {
        var response = testRestTemplate.getForEntity(WOOD_URL + "/555", WoodView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @Sql(
            statements = {"SELECT setval('wood_id_seq', 333)"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM wood WHERE name = 'Баобаб'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testAdd() {
        var wood = new Wood(null, "Баобаб", "Adansonia");
        var response = testRestTemplate.postForEntity(WOOD_URL, wood, Wood.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertEquals(334, result.getId()),
                () -> assertEquals("Баобаб", result.getName()),
                () -> assertEquals("Adansonia", result.getNameLat())
        );
    }
    @Test
    @Sql(
            statements = {
                    "UPDATE wood SET name = 'Ясень', name_lat = 'Fraxinus' WHERE name = 'Эвкалипт'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testUpdate() {
        var wood = new Wood(3, "Эвкалипт", "Eucalyptus");
        var response = testRestTemplate.exchange(WOOD_URL + "/3", HttpMethod.PUT, new HttpEntity<>(wood), Wood.class);
        var result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals("Эвкалипт", result.getName());
    }

    @Test
    public void testUpdate_negative() {
        var wood = new Wood(1111, "Эвкалипт", "Eucalyptus");
        var response = testRestTemplate.exchange(WOOD_URL + "/111", HttpMethod.PUT, new HttpEntity<>(wood), Wood.class);
        var result = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(result);
        assertNull(result.getName());
    }

    @Test
    @Sql(
            statements = {"INSERT INTO wood (id, name, name_lat) VALUES (4444, 'Псевдотсуга', 'Pseudotsuga')"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void testDelete() {
        var response = testRestTemplate.exchange(WOOD_URL + "/4444", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDelete_negative_not_found() {
        var response = testRestTemplate.exchange(WOOD_URL + "/2222", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDelete_negative_conflict() {
        var response = testRestTemplate.exchange(WOOD_URL + "/1", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
