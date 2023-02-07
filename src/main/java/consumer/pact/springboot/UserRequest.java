package consumer.pact.springboot;

import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import static consumer.pact.springboot.constants.Constants.APPLICATION_JSON;
import static consumer.pact.springboot.constants.Constants.GET_USER_PATH;

public class UserRequest {
    private String url;

    public UserRequest setUrl(String url) {
        this.url = url;

        return this;
    }


    public UserData getUser(int id) throws IOException {
        return Request.Get(this.url + GET_USER_PATH + id)
                .addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                .execute().handleResponse(httpResponse -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readValue(httpResponse.getEntity().getContent(), UserData.class);
                    } catch (JsonMappingException e) {
                        throw new IOException(e);
                    }
                });
    }

}
