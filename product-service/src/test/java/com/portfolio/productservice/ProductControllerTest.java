package com.portfolio.productservice;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {

    private ProductController controller = new ProductController();

    @Test
    void shouldReturnThreeProducts() {
        var response = controller.getAllProducts();
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    void shouldReturnLaptopForProductOne() {
        var response = controller.getProduct(1L);
        assertThat(response.getBody().getName()).isEqualTo("Laptop");
        assertThat(response.getBody().getPrice()).isEqualTo(999.99);
    }

    @Test
    void shouldCreateProductWithGeneratedId() {
        var product = new Product(null, "Keyboard", 79.99, 200);
        var response = controller.createProduct(product);
        assertThat(response.getBody().getName()).isEqualTo("Keyboard");
        assertThat(response.getBody().getId()).isNotNull();
    }
}
