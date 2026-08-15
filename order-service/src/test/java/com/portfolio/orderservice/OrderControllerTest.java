package com.portfolio.orderservice;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest {

    private OrderController controller = new OrderController();

    @Test
    void shouldReturnTwoOrders() {
        var response = controller.getAllOrders();
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void shouldReturnOrderById() {
        var response = controller.getOrder(1L);
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getStatus()).isEqualTo("CREATED");
        assertThat(response.getBody().getTotalPrice()).isEqualTo(1999.98);
    }

    @Test
    void shouldReturnCorrectStatusForOrder() {
        var response = controller.getOrder(1L);
        assertThat(response.getBody().getStatus()).isEqualTo("CREATED");
    }
}
