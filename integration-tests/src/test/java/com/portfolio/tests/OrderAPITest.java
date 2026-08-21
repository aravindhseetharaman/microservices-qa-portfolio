package com.portfolio.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.*;

class OrderAPITest{

    @BeforeEach
    void setup(){
        RestAssured.port=8081;
        RestAssured.baseURI="http://localhost";
    }

    @Test
    void shouldReturnAllorders(){
        given()
                .contentType("application/json");
        when()
                .get("/orders")
                .then()
                .statusCode(200)
                .body("size()",equalTo(2))
                .body("[0].status",equalTo("CREATED"))
                .body("[0].status",equalTo("SHIPPED"));
    }

    @Test
    void shouldReturnOrderByID(){
        given()
                .contentType("application/json")
                .when()
                        .get("/orders/1")
                .then()
                        .statusCode(200)
                .body("id", equalTo(1))
                .body("status", equalTo("CREATED"))
                .body("totalprice",equalTo(1999.98f));
    }

@Test
    public void shouldValidateOrderFields(){
        given()
                .contentType("application/json")
                .when()
                .get("/orders/1")
                .then()
                .statusCode(200)
                .body("productID",notNullValue())
                .body("userID",notNullValue())
                .body("quantity",greaterThan(0));
    }

}
