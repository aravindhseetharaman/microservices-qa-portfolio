package com.portfolio.productservice;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Verifies ProductService against every contract published for it in the Pact
 * Broker (e.g. by OrderService). Runs in the `verify` phase via failsafe, not
 * `test`, since it requires a running broker (see docker-compose.yml).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("ProductService")
@PactBroker(
    url = "${pactbroker.url:http://localhost:9292}",
    authentication = @PactBrokerAuth(
        username = "${pactbroker.username:pact}",
        password = "${pactbroker.password:pact}"
    )
)
class ProductServiceProviderIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        System.setProperty("pact.provider.version", System.getProperty("pact.provider.version", "0.0.1-local"));
        System.setProperty("pact.provider.tag", System.getProperty("pact.provider.tag", "local"));
        System.setProperty("pact.verifier.publishResults", System.getProperty("pact.verifier.publishResults", "false"));
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("product 1 exists")
    void productExists() {
        // Product data is hardcoded in the controller, so no fixture setup is needed.
    }
}
