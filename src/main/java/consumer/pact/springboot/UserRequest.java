package consumer.pact.springboot;

import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class UserRequest {
    private String url;

    public UserRequest setUrl(String url) {
        this.url = url;

        return this;
    }


    public UserData getUser(int id) throws IOException {
        return Request.Get(this.url + "/api/v1/users/" + id)
                .addHeader("Accept", "application/json")
                .execute().handleResponse(httpResponse -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readValue(httpResponse.getEntity().getContent(), UserData.class);
                    } catch (JsonMappingException e) {
                        throw new IOException(e);
                    }
                });
    }

    public List<UserData> getUsers() throws IOException {
        return Request.Get(this.url + "/api/v1/users")
                .addHeader("Accept", "application/json")
                .execute().handleResponse(httpResponse -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readValue(httpResponse.getEntity().getContent(), new TypeReference<List<UserData>>() {
                        });
                    } catch (JsonMappingException e) {
                        throw new IOException(e);
                    }
                });
    }
}
