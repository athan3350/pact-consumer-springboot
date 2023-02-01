package consumer.pact.springboot;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientData {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
}
