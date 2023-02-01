
import consumer.pact.springboot.ClientData;
import consumer.pact.springboot.ClientRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider-springboot")
class ClientsPactTest {

    @Pact(consumer="consumer-springboot")
    public RequestResponsePact getClient(PactDslWithProvider builder) {

        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("id", "1");
        body.stringType("name", "Sebastian Suarez");
        body.stringType("email", "sebastian@email.com");
        body.stringType("phone", "3145289654");
        body.stringType("password", "xhzASfe&1");

        return builder
                .given("a product with ID 1 exists")
                .uponReceiving("a request to get a client")
                .path("/client/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getClient")
    void testGetProduct(MockServer mockServer) throws IOException {
        ClientData client = new ClientRequest().setUrl(mockServer.getUrl()).getClient(1);

        assertThat(client.getName(), is("Sebastian Suarez"));
    }
}
