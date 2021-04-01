package com.example.mtservice1;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestContainers {
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
        ResponseEntity<String> actual = template1.postForEntity("/transfer","", String.class);
        Assert.assertEquals(400, actual.getStatusCodeValue());
    }

    @Test
    void isRunning(){
        Assert.assertTrue(app.isRunning());
    }

    @Test
    void postLegalJSON(){
        String legalJSON = "{\"cardFromNumber\":\"1232345643645645\",\"cardToNumber\":\"3124646476474747\",\"cardFromCVV\":\"124\",\"cardFromValidTill\":\"11/22\",\"amount\":{\"currency\":\"RUR\",\"value\":14253500}}";
        ResponseEntity<String> response = template1.postForEntity("/transfer", legalJSON, String.class);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void postLegalJSON1(){
        String legalJSON = "{\"cardFromNumber\":\"1232345643645645\",\"cardToNumber\":\"3124646476474747\",\"cardFromCVV\":\"124\",\"cardFromValidTill\":\"11/22\",\"amount\":{\"currency\":\"RUR\",\"value\":14253500}}";
        ResponseEntity<String> response = template1.postForEntity("/transfer", legalJSON, String.class);
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("operationId"));
    }
    @Test
    void postWrongJSON_expect400(){
        String legalJSON = "{\"cardFromNumber\":\"sdfgbrgh\",\"cardToNumber\":\"3124646476474747\",\"cardFromCVV\":\"124\",\"cardFromValidTill\":\"11/22\",\"amount\":{\"currency\":\"RUR\",\"value\":14253500}}";
        ResponseEntity<String> response = template1.postForEntity("/transfer", legalJSON, String.class);
        Assert.assertEquals(400, response.getStatusCodeValue());
    }
    @Test
    void postJSON_WrongSenderData(){
        String legalJSON = "{\"cardFromNumber\":\"sdfgbrgh\",\"cardToNumber\":\"3124646476474747\",\"cardFromCVV\":\"124\",\"cardFromValidTill\":\"11/22\",\"amount\":{\"currency\":\"RUR\",\"value\":14253500}}";
        ResponseEntity<String> response = template1.postForEntity("/transfer", legalJSON, String.class);
        Assert.assertEquals(true, response.getBody().contains("wrong sender's card number"));
    }
    @Test
    void postEmptyJSON_expect500(){
        String legalJSON = "{}";
        ResponseEntity<String> response = template1.postForEntity("/transfer", legalJSON, String.class);
        Assert.assertEquals(500, response.getStatusCodeValue());
    }


}
