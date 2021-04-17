package com.example.mtservice1;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEndpoint_confirmOperation {
    @Autowired
    TestRestTemplate template1;
    public static GenericContainer<?> app = new GenericContainer("m-t-service1_my-course-app").withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @ParameterizedTest
    @ValueSource(ints = {101, 102, 103, 104, 105, 106, 107, 108, 109, 210, 211, 212, 213, 214, 215, 216, 217, 218,
            219, 320, 433, 434, 435, 436, 437, 438, 439, 440, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550,
            651, 652, 653, 654, 655, 656, 657, 988, 989, 990, 991, 992, 993, 994, 995, 996, 997, 998, 999,
    })
    void postLegalNotRegisteredTransactions_expected400(int args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "0000");
        map.put("operationId", args);

        ResponseEntity<String> response = template1.postForEntity("/confirmOperation", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 102, 103, 104, 105, 106, 107, 108, 109, 210})
    void postWrongJSON(int args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "0000");
        map.put("operationId", args);

        ResponseEntity<String> response = template1.postForEntity("/confirmOperation", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void postNullMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "0000");
        map.put("operationId", null);

        ResponseEntity<String> response = template1.postForEntity("/confirmOperation", map, String.class);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test()
    void postNullMap1() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "0000");
        map.put(null, null);

        try {
            template1.postForEntity("/confirmOperation", map, String.class);
        } catch (Exception ex) {
            Assertions.assertTrue(ex.getMessage().contains("Could not write JSON"));
        }
    }


    @Test
    void postNullMap2() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(null, null);

        try {
            template1.postForEntity("/confirmOperation", map, String.class);
        } catch (Exception ex) {
            Assertions.assertTrue(ex.getMessage().contains("Could not write JSON"));
        }
    }

}
