package org.km.controller;

import org.junit.jupiter.api.Test;
import org.km.db.entity.Cooper;
import org.km.db.entity.Drink;
import org.km.db.view.CooperView;
import org.km.db.view.DrinkView;
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
import static org.km.controller.ControllerConstants.DRINK_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=integration",
        "scheduling.enabled: false"
}
)

@ActiveProfiles("integration")

public class DrinkControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetDrinks() {
        var response = testRestTemplate.getForEntity(DRINK_URL, DrinkView[].class);
        DrinkView drinks[] = response.getBody();
        var drinkList = Arrays.stream(drinks).toList();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(35, drinkList.size()),
                () -> assertEquals("коньяк", drinkList.get(34).getName()),
                () -> assertEquals("красный виноград", drinkList.get(34).getSource()),
                () -> assertTrue(drinkList.get(1).isFilled())
        );
    }

    @Test
    public void testGetDrink() {
        var response = testRestTemplate.getForEntity(DRINK_URL + "/22", DrinkView.class);
        DrinkView drinkView = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(22, drinkView.getId()),
                () -> assertEquals("ром", drinkView.getName()),
                () -> assertEquals("меласса", drinkView.getSource())
        );
    }

    @Test
    public void testGetDrink_negative_not_found() {
        var response = testRestTemplate.getForEntity(DRINK_URL + "/555", DrinkView.class);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @Sql(
            statements = {"SELECT setval('drink_id_seq', 1000)"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM drink WHERE name = 'Чаранда'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testAdd() {
        var drink = new Drink(null, "сахарный тростник", "Чаранда", 40, "мексиканский ром");
        var response = testRestTemplate.postForEntity(DRINK_URL, drink, Drink.class);
        var result = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertEquals(1001, result.getId()),
                () -> assertEquals("Чаранда", result.getName()),
                () -> assertEquals("мексиканский ром", result.getDescription())
        );
    }

    @Test
    @Sql(
            statements = {
                    "UPDATE drink SET source = 'красный виноград', name = 'коньяк', alcohol = 40 WHERE name = 'сотол'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testUpdate() {
        var drink = new Drink(35, "сотол", "сотол", 40, null);
        var response = testRestTemplate.exchange(DRINK_URL + "/1", HttpMethod.PUT, new HttpEntity<>(drink), Drink.class);
        var result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals("сотол", result.getName());
    }

    @Test
    public void testUpdate_negative() {
        var drink = new Drink(1111, "сотол", "сотол", 40, null);
        var response = testRestTemplate.exchange(DRINK_URL + "/111", HttpMethod.PUT, new HttpEntity<>(drink), Drink.class);
        var result = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(result);
        assertNull(result.getName());
    }

    @Test
    @Sql(
            statements = {"INSERT INTO drink (id, source, name, alcohol) VALUES (4444, 'лак для ногтей', 'слеза комсомолки', 66)"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void testDelete() {
        var response = testRestTemplate.exchange(DRINK_URL + "/4444", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDelete_negative_not_found() {
        var response = testRestTemplate.exchange(DRINK_URL + "/1111", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDelete_negative_conflict() {
        var response = testRestTemplate.exchange(DRINK_URL + "/1", HttpMethod.DELETE, null, Void.class, 111);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
