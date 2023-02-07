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
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static consumer.pact.springboot.constants.Constants.APPLICATION_JSON;
import static consumer.pact.springboot.constants.Constants.GET_USER_PATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider-springboot")
class UserPactTest {

    @Pact(consumer = "consumer-springboot")
    public RequestResponsePact getUser(PactDslWithProvider builder) {

        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("id", "1");
        body.stringType("ownerName", "Sebastian Suarez");
        body.stringType("ownerEmail", "sebastian@email.com");
        body.stringType("phone", "3145289654");
        body.stringType("password", "xhzASfe&1");

        return builder
                .given("a user with ID 1 exists")
                .uponReceiving("a request to get a user")
                .path(GET_USER_PATH)
                .method("GET")
                .headers(headers())
                .willRespondWith()
                .status(200)
                .headers(headers())
                .body(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getUser")
    void testGetProduct(MockServer mockServer) throws IOException {
        UserData user = new UserRequest().setUrl(mockServer.getUrl()).getUser(1);

        assertThat(user.getOwnerName(), is("Sebastian Suarez"));
        assertThat(user.getId(), is(1L));
        assertThat(user.getPhone(), is("3145289654"));
        assertThat(user.getOwnerEmail(), is("sebastian@email.com"));
    }

    private Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return headers;
    }
}
