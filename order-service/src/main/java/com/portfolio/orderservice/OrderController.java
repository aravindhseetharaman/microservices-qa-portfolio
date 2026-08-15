package com.portfolio.orderservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String productServiceUrl = "http://localhost:8082";
    private final String userServiceUrl = "http://localhost:8083";

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(List.of(
            new Order(1L, 1L, 1L, 2, 1999.98, "CREATED"),
            new Order(2L, 2L, 1L, 1, 599.99, "SHIPPED")
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(
            new Order(id, 1L, 1L, 2, 1999.98, "CREATED")
        );
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Product product = restTemplate.getForObject(
            productServiceUrl + "/products/" + order.getProductId(),
            Product.class);

        User user = restTemplate.getForObject(
            userServiceUrl + "/users/" + order.getUserId(),
            User.class);

        double totalPrice = product.getPrice() * order.getQuantity();
        Order created = new Order(
            System.currentTimeMillis(),
            order.getProductId(),
            order.getUserId(),
            order.getQuantity(),
            totalPrice,
            "CREATED"
        );
        return ResponseEntity.status(201).body(created);
    }
}
