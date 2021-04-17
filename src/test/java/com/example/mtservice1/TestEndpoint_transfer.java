package com.example.mtservice1;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEndpoint_transfer {
    @Autowired
    TestRestTemplate template1;
    public static GenericContainer<?> app = new GenericContainer("m-t-service1_my-course-app").withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @Test
    void testDevApp_getMethod405() {
        ResponseEntity<String> actual = template1.getForEntity("http://localhost:" + app.getMappedPort(5500) + "/transfer", String.class);
        Assert.assertEquals(405, actual.getStatusCodeValue());
    }

    @Test
    void testDevApp_postMethod400() {
        ResponseEntity<String> actual = template1.postForEntity("/transfer", "", String.class);
        Assertions.assertEquals(415, actual.getStatusCodeValue());
    }

    @Test
    void isRunning() {
        Assert.assertTrue(app.isRunning());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "1111", "{\"cardFromNumber\":\"1232345643645645\",\"cardToNumber\":\"3124646476474747\",\"cardFromCVV\":\"124\",\"cardFromValidTill\":\"11/22\",\"amount\":{\"currency\":\"RUR\",\"value\":14253500}}"})
    void postIllegalStrings(String arguments) {
        ResponseEntity<String> response = template1.postForEntity("/transfer", arguments, String.class);
        Assert.assertEquals(415, response.getStatusCodeValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "_", "/", "/*-+", "1234", "feragferg", "qwe", "cardFromNumber", "1111111111111111", "11111111111111111111111111111111"})
    void postJSON_WrongCardFrom(String args) {
        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", "RUR");
        amountMasp.put("value", 12345);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", args);
        map.put("cardToNumber", "5105105105105100");
        map.put("cardFromCVV", "123");
        map.put("cardFromValidTill", "11/22");
        map.put("amount", amountMasp);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "_", "/", "/*-+", "1234", "feragferg", "qwe", "cardFromNumber", "1111111111111111", "11111111111111111111111111111111"})
    void postJSON_WrongCardTo(String args) {
        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", "RUR");
        amountMasp.put("value", 12345);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", "5105105105105100");
        map.put("cardToNumber", args);
        map.put("cardFromCVV", "123");
        map.put("cardFromValidTill", "11/22");
        map.put("amount", amountMasp);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "_", "/", "/*-+", "1234", "feragferg", "qwe", "cardFromNumber", "1111111111111111", "11111111111111111111111111111111"})
    void postJSON_WrongCardFromCVV(String args) {
        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", "RUR");
        amountMasp.put("value", 12345);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", "5105105105105100");
        map.put("cardToNumber", "1111111111111111");
        map.put("cardFromCVV", args);
        map.put("cardFromValidTill", "11/22");
        map.put("amount", amountMasp);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "_", "/", "/*-+", "1234", "feragferg", "qwe", "cardFromNumber", "1111111111111111", "11111111111111111111111111111111"})
    void postJSON_WrongCardFromValidTill(String args) {
        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", "RUR");
        amountMasp.put("value", 12345);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", "5105105105105100");
        map.put("cardToNumber", "5105105105105100");
        map.put("cardFromCVV", "123");
        map.put("cardFromValidTill", args);
        map.put("amount", amountMasp);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "_", "/", "/*-+", "feragferg", "qwe", "cardFromNumber", "1111111111111111", "11111111111111111111111111111111"})
    void postJSON_WrongAmountValue(String args) {
        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", "RUR");
        amountMasp.put("value", args);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", "5105105105105100");
        map.put("cardToNumber", "5105105105105100");
        map.put("cardFromCVV", "123");
        map.put("cardFromValidTill", "12/25");
        map.put("amount", amountMasp);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void postIncompleteJSON_1() {
        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", "RUR");
        amountMasp.put("value", 12345);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", "5105105105105100");
        map.put("cardToNumber", "1111111111111111");

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void postIncompleteJSON_2() {
        HashMap<String, Object> amountMap = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", "5105105105105100");
        map.put("cardToNumber", "1111111111111111");
        map.put("cardFromCVV", "123");
        map.put("cardFromValidTill", "11/22");
        map.put("amount", amountMap);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void postNullMap(){

        HashMap<String, Object> amountMasp = new HashMap<>();
        amountMasp.put("currency", null);
        amountMasp.put("value", null);

        HashMap<String, Object> map = new HashMap<>();//temp. map using for response only
        map.put("cardFromNumber", null);
        map.put("cardToNumber", null);
        map.put("cardFromCVV", null);
        map.put("cardFromValidTill", null);
        map.put("amount", null);

        ResponseEntity<String> response = template1.postForEntity("/transfer", map, String.class);
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }


}
