package com.portfolio.productservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(List.of(
            new Product(1L, "Laptop", 999.99, 50),
            new Product(2L, "Phone", 599.99, 100),
            new Product(3L, "Tablet", 449.99, 75)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(new Product(id, "Laptop", 999.99, 50));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(System.currentTimeMillis());
        return ResponseEntity.status(201).body(product);
    }
}
