package com.onwelo.emails.rest;

import com.onwelo.emails.db.User;
import com.onwelo.emails.db.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiTest {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private User firstUser;
    private User secondUser;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/users";

        firstUser = new User();
        firstUser.setName("First");
        firstUser.setEmail("firstUser@mail.pl");
        firstUser.setId(1L);

        secondUser = new User();
        secondUser.setName("Second");
        secondUser.setEmail("secondUser@mail.pl");
        secondUser.setId(2L);

        userRepository.save(firstUser);
        userRepository.save(secondUser);

    }

    @AfterEach
    public void afterSetup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindUser() {
        ResponseEntity<User> user =
                restTemplate.getForEntity(baseUrl + "/get?id=1", User.class, new HashMap<>());
        Assertions.assertEquals(user.getBody().getEmail(), "firstUser@mail.pl");
        Assertions.assertEquals(user.getBody().getName(), "First");
    }

    @Test
    void shouldFindAllUser() {
        ResponseEntity<User[]> userList =
                (ResponseEntity<User[]>) restTemplate.getForEntity(baseUrl + "/getAll", User[].class, new ArrayList<>());
        Assertions.assertEquals(Arrays.stream(userList.getBody()).count(), 2);
        Assertions.assertEquals(Arrays.stream(userList.getBody()).findFirst().get().getName(), "First");
        Assertions.assertEquals(Arrays.stream(userList.getBody()).findFirst().get().getEmail(), "firstUser@mail.pl");
        Assertions.assertEquals(Arrays.stream(userList.getBody()).findFirst().get().getId(), 1L);

        Assertions.assertEquals(Arrays.stream(userList.getBody()).collect(Collectors.toUnmodifiableList()).get(1).getName(), "Second");
        Assertions.assertEquals(Arrays.stream(userList.getBody()).collect(Collectors.toUnmodifiableList()).get(1).getEmail(), "secondUser@mail.pl");
        Assertions.assertEquals(Arrays.stream(userList.getBody()).collect(Collectors.toUnmodifiableList()).get(1).getId(), 2l);
    }

    @Test
    void shouldNotFindUser() {
        ResponseEntity<User> user =
                restTemplate.getForEntity(baseUrl + "/get?id=222", User.class, new HashMap<>());
        Assertions.assertNull(user.getBody());
    }

    @Test
    void shouldDeleteUser() {
        restTemplate.delete(baseUrl + "/delete?id=1");

        ResponseEntity<User> user =
                restTemplate.getForEntity(baseUrl + "/get?id=1", User.class, new HashMap<>());
        Assertions.assertNull(user.getBody());
    }

    @Test
    void shouldCreateUser() {

        String jsonToSent = "{\"id\":3,\"email\":\"thirdUser@mail.pl\",\"name\":\"third\"}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);

        ResponseEntity exchange = restTemplate.exchange(baseUrl + "/createOrUpdate", HttpMethod.POST, httpEntity, Void.class);

        ResponseEntity<User> user =
                restTemplate.getForEntity(baseUrl + "/get?id=3", User.class, new HashMap<>());
        Assertions.assertEquals(user.getBody().getEmail(), "thirdUser@mail.pl");

    }

}