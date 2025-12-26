package org.km.controller;

import org.junit.jupiter.api.Test;

import org.km.db.view.BarrelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.km.controller.ControllerConstants.BARREL_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties =
        {
                "spring.profiles.active=integration",
                "scheduling.enabled: false"
        }
)
@TestPropertySource(properties = {"spring.flyway.enabled=true"})

@ActiveProfiles("integration")

public class BarrelControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Проверка получения списка записей.
     */
    @Test
    public void testGetBarrels() {
        var response = testRestTemplate.getForEntity(BARREL_URL, BarrelView[].class);
        BarrelView barrels[] = response.getBody();
        var barrelList = Arrays.stream(barrels).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(13, barrelList.size()),
                () -> assertEquals(3, barrelList.get(0).getVolume()),
                () -> assertEquals(5, barrelList.get(5).getVolume())
        );
    }

    /**
     * Проверка получения списка бочек по производителю.
     */
    @Test
    public void testGetBarrelsByCooper() {
        var response = testRestTemplate.getForEntity(BARREL_URL + "/by-cooper/2", BarrelView[].class);
        BarrelView barrels[] = response.getBody();
        var barrelList = Arrays.stream(barrels).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(5, barrelList.size()),
                () -> assertEquals(10, barrelList.get(2).getVolume()),
                () -> assertEquals(20, barrelList.get(3).getVolume())
                );
    }

    @Test
    public void testGetBarrelsByWood() {
        var response = testRestTemplate.getForEntity(BARREL_URL + "/by-wood/1", BarrelView[].class);
        BarrelView barrels[] = response.getBody();
        var barrelList = Arrays.stream(barrels).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(6, barrelList.size()),
                () -> assertEquals(10, barrelList.get(2).getVolume()),
                () -> assertEquals(20, barrelList.get(3).getVolume())
        );
    }

    @Test
    public void testGetBarrel() {
        var response = testRestTemplate.getForEntity(BARREL_URL + "/2", BarrelView.class);
        BarrelView barrelView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(2, barrelView.getId()),
                () -> assertEquals("Из Дуба", barrelView.getCooperName()),
                () -> assertEquals(5, barrelView.getVolume())
        );
    }

    /**
     * Проверка получения записи с несуществующим id.
     */
    @Test
    public void testGetBarrel_negative_not_found() {
        var response = testRestTemplate.getForEntity(BARREL_URL + "/234", BarrelView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    /**
     * Проверка успешного добавления записи.
     */
    @Test
    @Sql(
            statements = {"SELECT setval('barrel_id_seq', 300)"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM barrel WHERE description = 'Большая бочка'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testAdd() {

        var barrelView = new BarrelView(100, "Большая бочка", 2, 2);
        var response = testRestTemplate.postForEntity(BARREL_URL, barrelView, BarrelView.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertEquals(301, result.getId()),
                () -> assertEquals("Большая бочка", result.getDescription())
        );
    }

    /**
     * Попытка добавления записи с внешними ключами на несуществующие родительские записи.
     */
    @Test
    public void testAdd_negative_not_found() {

        var barrelView = new BarrelView(100, "Большая бочка", 200, 2000);
        var response = testRestTemplate.postForEntity(BARREL_URL, barrelView, BarrelView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    /**
     * Проверка изменение записи.
     */
    @Test
    @Sql(
            statements = {
                    "UPDATE barrel SET volume = 3, description = 'Сысоев, бочка с краником' WHERE id = 1"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testUpdate() {
        var barrelView = new BarrelView(1, 35, "Средняя бочка", 1, 1);
        var response = testRestTemplate.exchange(BARREL_URL + "/1", HttpMethod.PUT, new HttpEntity<>(barrelView), BarrelView.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(result),
                () -> assertEquals("Средняя бочка", result.getDescription())
        );
    }

    /**
     * Проверка защиты от попытки изменить id.
     */
    @Test
    public void testUpdate_negative_id_change() {
        var barrelView = new BarrelView(111, 35, "Средняя бочка", 1, 1);
        var response = testRestTemplate.exchange(BARREL_URL + "/1", HttpMethod.PUT, new HttpEntity<>(barrelView), BarrelView.class);
        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode())
        );
    }

    /**
     * Проверка обновления с внешними ключами на несуществущие родительские записи.
     */
    @Test
    public void testUpdate_negative_not_found() {
        var barrelView = new BarrelView(1111, 35, "Средняя бочка", 1, 1);
        var response = testRestTemplate.exchange(BARREL_URL + "/111", HttpMethod.PUT, new HttpEntity<>(barrelView), BarrelView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    /**
     * Проверка удаления записи.
     */
    @Test
    @Sql(
            statements = {"INSERT INTO barrel (id, cooper_id, wood_id, volume, description) VALUES (111, 1, 1, 150, 'тестовая запись для удаления')"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void testDelete() {
        var response = testRestTemplate.exchange(BARREL_URL + "/111", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Проверка удаления несуществующей записи.
     */
    @Test
    public void testDelete_negative_not_found() {
        var response = testRestTemplate.exchange(BARREL_URL + "/111", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Попытка удаления записи, на которую есть ссылка.
     */
    @Test
    public void testDelete_negative_conflict() {
        var response = testRestTemplate.exchange(BARREL_URL + "/1", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
