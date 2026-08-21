package com.portfolio.tests;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

class ProductApiTest {

    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl = "http://localhost:8082";

    @Test
    void shouldReturnAllProducts() {
        var response = restTemplate.getForEntity(
            baseUrl + "/products", Object[].class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    void shouldReturnProductById() {
        var response = restTemplate.getForEntity(
            baseUrl + "/products/1", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("Laptop");
    }

    @Test
    void shouldCreateProduct() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(
            "{\"name\":\"Keyboard\",\"price\":79.99,\"stock\":200}", 
            headers);
        var response = restTemplate.postForEntity(
            baseUrl + "/products", entity, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).contains("Keyboard");
    }
}
