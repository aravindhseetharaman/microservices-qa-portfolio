package com.portfolio.demo.tests;

import com.portfolio.demo.model.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class UserApiTest   {
    @BeforeEach
    void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    //Check size is 10
    @Test
    void getallusers(){
        given()
                    .contentType("application/json")
                .when()
                    .get("/users")
                .then()
                    .statusCode(200)
                    .body("size()",equalTo(10));
    }

    //check name
    @Test
    void getuserbyId(){
        given()
                .contentType("application/json")
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("name",equalTo("Leanne Graham"))
                .body("email",equalTo("Sincere@april.biz"))
                .body("username",equalTo("Bret"));
    }


    //check city
    @Test
    void getusernestedaddress(){
        given()
                .contentType("application/json")
                .when()
                .get("users/1")
                .then()
                .statusCode(200)
                .body("address.street",equalTo("Kulas Light"))
                .body("address.city",equalTo("Gwenborough"))
                .body("username",equalTo("Bret"));
    }



    @Test
    void shouldDeserializeUserToPojo(){
        User user = given()
                .contentType("application/json")
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(User.class);

        // top level
        assertThat(user.getName()).isEqualTo("Leanne Graham");
        assertThat(user.getEmail()).isEqualTo("Sincere@april.biz");

        // nested object
        assertThat(user.getAddress()).isNotNull();
        assertThat(user.getAddress().getCity()).isEqualTo("Gwenborough");
        assertThat(user.getAddress().getStreet()).isEqualTo("Kulas Light");

        // deeply nested
        assertThat(user.getAddress().getGeo()).isNotNull();
        assertThat(user.getAddress().getGeo().getLat()).isEqualTo("-37.3159");

        // nested company
        assertThat(user.getCompany()).isNotNull();
        assertThat(user.getCompany().getName()).isEqualTo("Romaguera-Crona");
    }

}

