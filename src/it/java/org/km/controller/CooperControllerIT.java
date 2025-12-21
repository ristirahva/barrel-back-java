package org.km.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.km.db.entity.Cooper;
import org.km.db.view.CooperView;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.km.controller.ControllerConstants.COOPER_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties =
        {"spring.profiles.active=test",
        "scheduling.enabled: false"}
)

@ActiveProfiles("test")
@Transactional
@Rollback(true)
@Sql(
        scripts = {"/db.migration/V2.1__insert_cooper.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
public class CooperControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetCoopers() {
        var response = restTemplate.getForEntity(COOPER_URL, CooperView[].class);
        CooperView coopers[] = response.getBody();
        var cooperList = Arrays.stream(coopers).sorted((c1, c2)->c1.getId().toString().compareTo(c2.getId().toString())).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(2, cooperList.size()),
                () -> assertEquals("Сысоев", cooperList.get(0).getName()),
                () -> assertEquals(5, cooperList.get(1).getBarrelCount())
        );
    }

    @Test
    public void testGetCooper() {
        var response = restTemplate.getForEntity(COOPER_URL + "/2", CooperView.class);
        CooperView cooperView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(2, cooperView.getId()),
                () -> assertEquals("Из Дуба", cooperView.getName()),
                () -> assertEquals(5, cooperView.getBarrelCount())
        );
    }

    @Test
    public void testGetCooper_negative() {
        var response = restTemplate.getForEntity(COOPER_URL + "/23", CooperView.class);
        CooperView cooperView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @Sql(
            statements = {"DELETE FROM cooper WHERE name = 'Большой бочонок'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testAdd() {
        String name = "Большой бочонок";
        var cooper = new Cooper(3, name, "http://foo.bar");
        var response = restTemplate.postForEntity(COOPER_URL, cooper, Cooper.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertEquals(1, result.getId()),
                () -> assertEquals(name, result.getName())
        );
    }

    @Test
    public void testUpdate() {
        var cooper = new Cooper(1, "Конюшня", "http://foo.bar");
        var response = restTemplate.exchange(COOPER_URL + "/1", HttpMethod.PUT, new HttpEntity<>(cooper), Cooper.class);
        var result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals("Конюшня", result.getName());
    }

    @Test
    public void testUpdate_negative() {
        var cooper = new Cooper(111, "Конюшня 111", "http://foo.bar");
        var response = restTemplate.exchange(COOPER_URL + "/111", HttpMethod.PUT, new HttpEntity<>(cooper), Cooper.class);
        var result = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(result);
        assertNull(result.getName());
    }

    @Test
    @Sql(
            statements = {"INSERT INTO cooper (id, name, URL) VALUES (1, 'Сысоев', 'https://rusbochki.ru/')"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testDelete() {
        var response = restTemplate.exchange(COOPER_URL + "/1", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    public void testDelete_negative() {
        var response = restTemplate.exchange(COOPER_URL + "/111", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
