package com.portfolio.orderservice;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProductService", pactVersion = PactSpecVersion.V3)
class ProductServicePactTest {

    @Pact(consumer = "OrderService")
    public RequestResponsePact getProductPact(PactDslWithProvider builder) {
        return builder
            .given("product 1 exists")
            .uponReceiving("a request for product 1")
                .path("/products/1")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                    .numberType("id", 1)
                    .stringType("name", "Laptop")
                    .decimalType("price", 999.99)
                    .integerType("stock", 50))
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getProductPact")
    void shouldGetProductFromProductService(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplate();

        Product product = restTemplate.getForObject(
            mockServer.getUrl() + "/products/1",
            Product.class);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Laptop");
        assertThat(product.getPrice()).isEqualTo(999.99);
        assertThat(product.getStock()).isEqualTo(50);
    }
}
