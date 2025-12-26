package org.km.controller;

import org.junit.jupiter.api.Test;
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

    /**
     * Проверка получения списка древесины.
     */
    @Test
    public void testGetWoods() {
        var response = testRestTemplate.getForEntity(WOOD_URL, org.km.db.view.WoodView[].class);
        org.km.db.view.WoodView woods[] = response.getBody();
        var woodList = Arrays.stream(woods).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(7, woodList.size()),
                () -> assertEquals("Ясень", woodList.get(6).getName()),
                () -> assertEquals("Castanea", woodList.get(4).getNameLat()),
                () -> assertEquals(6, woodList.get(1).getBarrelCount())
        );
    }

    /**
     * Проверка получения записи.
     */
    @Test
    public void testGetWood() {
        var response = testRestTemplate.getForEntity(WOOD_URL + "/3", org.km.db.view.WoodView.class);
        org.km.db.view.WoodView woodView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(3, woodView.getId()),
                () -> assertEquals("Ясень", woodView.getName()),
                () -> assertEquals(2, woodView.getBarrelCount())
        );
    }

    @Test
    public void testGetWood_negative_not_found() {
        var response = testRestTemplate.getForEntity(WOOD_URL + "/555", org.km.db.view.WoodView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    /**
     * Проверка успешного добавления записи.
     */
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
        var wood = new WoodView("Баобаб", "Adansonia");
        var response = testRestTemplate.postForEntity(WOOD_URL, wood, WoodView.class);
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

    /**
     * Успешное изменение записи.
     */
    @Test
    @Sql(
            statements = {
                    "UPDATE wood SET name = 'Ясень', name_lat = 'Fraxinus' WHERE name = 'Эвкалипт'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testUpdate() {
        var wood = new WoodView(3, "Эвкалипт", "Eucalyptus", null);
        var response = testRestTemplate.exchange(WOOD_URL + "/3", HttpMethod.PUT, new HttpEntity<>(wood), WoodView.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(result),
                () ->assertEquals("Эвкалипт", result.getName())
        );
    }

    /**
     * Проверка защиты от изменения id.
     */
    @Test
    public void testUpdate_negative_id_change() {
        var wood = new WoodView(4, "Эвкалипт", "Eucalyptus", null);
        var response = testRestTemplate.exchange(WOOD_URL + "/3", HttpMethod.PUT, new HttpEntity<>(wood), WoodView.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Попытка изменения записи с несуществующим id.
     */
    @Test
    public void testUpdate_negative() {
        var wood = new WoodView(1111, "Эвкалипт", "Eucalyptus", null);
        var response = testRestTemplate.exchange(WOOD_URL + "/111", HttpMethod.PUT, new HttpEntity<>(wood), WoodView.class);
        var result = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(result);
        assertNull(result.getName());
    }

    /**
     * Успешное удаление записи.
     */
    @Test
    @Sql(
            statements = {"INSERT INTO wood (id, name, name_lat) VALUES (4444, 'Псевдотсуга', 'Pseudotsuga')"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void testDelete() {
        var response = testRestTemplate.exchange(WOOD_URL + "/4444", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Попытка удаления записи с несуществующим id.
     */
    @Test
    public void testDelete_negative_not_found() {
        var response = testRestTemplate.exchange(WOOD_URL + "/2222", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Попытка удаления родительской записи.
     */
    @Test
    public void testDelete_negative_conflict() {
        var response = testRestTemplate.exchange(WOOD_URL + "/1", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
