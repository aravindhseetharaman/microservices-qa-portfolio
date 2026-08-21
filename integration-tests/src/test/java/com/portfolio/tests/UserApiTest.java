package com.portfolio.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiTest {
    @BeforeEach
    void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port=8083;

    }
    @Test
    void shouldReturnAllUsers(){
        given()
                .contentType("application/json")
                .when()
                    .get("/users")
                .then()
                    .statusCode(200)
                     .body("size()",equalTo(3))
                    .body("[0].name",equalTo("John Doe"))
                .body("[0].email",equalTo("john@example.com"));
    }
    @Test
    void shouldReturnUserById(){
        given()
                .contentType("application/json")
                .when()
                .get("users/1")
                .then()
                .statusCode(200)
                .body("id",equalTo(101))
                .body("name",equalTo("JohnDoe"))
                .body("[0].email",equalTo("john@example.com"));

    }
    @Test
    void shouldCreateuser(){
        given()
                .contentType("application/json")
                .body("{\"name\":\"Alice\",\"email\":\"alice@example.com\",\"phone\":\"+44111111111\"}")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name",equalTo("Alice"))
                .body("id",notNullValue());




    }
}
