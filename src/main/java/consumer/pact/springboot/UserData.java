package consumer.pact.springboot;
import lombok.Data;

@Data
public class UserData {
    private Long id;
    private String ownerName;
    private String ownerEmail;
    private String phone;
    private String password;
}
