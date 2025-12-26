package org.km.controller;

import org.junit.jupiter.api.Test;

import org.km.db.entity.Cooper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.km.db.view.CooperView;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.km.controller.ControllerConstants.COOPER_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties =
        {
            "spring.profiles.active=integration",
            "scheduling.enabled: false"
        }
)
@TestPropertySource(properties = {"spring.flyway.enabled=true"})

@ActiveProfiles("integration")

public class CooperControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Проверка получения списка.
     */
    @Test
    public void testGetCoopers() {
        var response = testRestTemplate.getForEntity(COOPER_URL, CooperView[].class);
        CooperView coopers[] = response.getBody();
        var cooperList = Arrays.stream(coopers).sorted((c1, c2)->c1.getId().toString().compareTo(c2.getId().toString())).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(2, cooperList.size()),
                () -> assertEquals("Сысоев", cooperList.get(0).getName()),
                () -> assertEquals(5, cooperList.get(1).getBarrelCount())
        );
    }

    /**
     * Проверка получения записи.
     */
    @Test
    public void testGetCooper() {
        var response = testRestTemplate.getForEntity(COOPER_URL + "/2", CooperView.class);
        CooperView cooperView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(2, cooperView.getId()),
                () -> assertEquals("Из Дуба", cooperView.getName()),
                () -> assertEquals(5, cooperView.getBarrelCount())
        );
    }

    /**
     * Попытка получения записи по несуществующему id.
     */
    @Test
    public void testGetCooper_negative_not_found() {
        var response = testRestTemplate.getForEntity(COOPER_URL + "/23", CooperView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    /**
     * Проверка добавления записи.
     */
    @Test
    @Sql(
            statements = {"SELECT setval('cooper_id_seq', 100)"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM cooper WHERE name = 'Большой бочонок'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testAdd() {
        String name = "Большой бочонок";
        var cooper = new Cooper(null, name, "http://foo.bar");
        var response = testRestTemplate.postForEntity(COOPER_URL, cooper, Cooper.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertEquals(101, result.getId()),
                () -> assertEquals(name, result.getName())
        );
    }

    /**
     * Успешное изменение записи.
     */
    @Test
    @Sql(
            statements = {
                    "UPDATE cooper SET name = 'Сысоев', url='https://rusbochki.ru/' WHERE name = 'Конюшня'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testUpdate() {
        var cooper = new Cooper(1, "Конюшня", "http://foo.bar");
        var response = testRestTemplate.exchange(COOPER_URL + "/1", HttpMethod.PUT, new HttpEntity<>(cooper), Cooper.class);
        var result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals("Конюшня", result.getName());
    }

    /**
     * Проверка защиты от изменения id.
     */
    @Test
    public void testUpdate_negative_id_change() {
        var cooper = new Cooper(111, "Конюшня", "http://foo.bar");
        var response = testRestTemplate.exchange(COOPER_URL + "/1", HttpMethod.PUT, new HttpEntity<>(cooper), Cooper.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Попытка изменения несуществующей записи.
     */
    @Test
    public void testUpdate_negative() {
        var cooper = new Cooper(111, "Конюшня 111", "http://foo.bar");
        var response = testRestTemplate.exchange(COOPER_URL + "/111", HttpMethod.PUT, new HttpEntity<>(cooper), Cooper.class);
        var result = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(result);
        assertNull(result.getName());
    }

    /**
     * Успешное удаление.
     */
    @Test
    @Sql(
            statements = {"INSERT INTO cooper (id, name, URL) VALUES (11, 'КРокодилов', 'https://bourbon.ru/')"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void testDelete() {
        var response = testRestTemplate.exchange(COOPER_URL + "/11", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Попытка удаления записи по несуществуюшему id.
     */
    @Test
    public void testDelete_negative_not_found() {
        var response = testRestTemplate.exchange(COOPER_URL + "/111", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Попытка удалить родительскую запись.
     */
    @Test
    public void testDelete_negative_conflict() {
        var response = testRestTemplate.exchange(COOPER_URL + "/1", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
