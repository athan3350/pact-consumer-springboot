package consumer.pact.springboot;

import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ClientRequest {
    private String url;

    public ClientRequest setUrl(String url) {
        this.url = url;

        return this;
    }


    public ClientData getClient(int id) throws IOException {
        return (ClientData) Request.Get(this.url + "/client/" + id)
                .addHeader("Accept", "application/json")
                .execute().handleResponse(httpResponse -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readValue(httpResponse.getEntity().getContent(), ClientData.class);
                    } catch (JsonMappingException e) {
                        throw new IOException(e);
                    }
                });
    }

    public List<ClientData> getClients() throws IOException {
        return (List<ClientData>) Request.Get(this.url + "/clients")
                .addHeader("Accept", "application/json")
                .execute().handleResponse(httpResponse -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readValue(httpResponse.getEntity().getContent(), new TypeReference<List<ClientData>>() {
                        });
                    } catch (JsonMappingException e) {
                        throw new IOException(e);
                    }
                });
    }
}
