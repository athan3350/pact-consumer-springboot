package consumer.pact.springboot;

import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider-springboot")
class UserPactTest {

    @Pact(consumer="consumer-springboot")
    public RequestResponsePact getUser(PactDslWithProvider builder) {

        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("id", "1");
        body.stringType("name", "Sebastian Suarez");
        body.stringType("email", "sebastian@email.com");
        body.stringType("phone", "3145289654");
        body.stringType("password", "xhzASfe&1");

        return builder
                .given("a product with ID 1 exists")
                .uponReceiving("a request to get a user")
                .path("/api/v1/users/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getUser")
    void testGetProduct(MockServer mockServer) throws IOException {
        UserData user = new UserRequest().setUrl(mockServer.getUrl()).getUser(1);

        assertThat(user.getName(), is("Sebastian Suarez"));
    }
}
