package consumer.pact.springboot;
import lombok.Data;

@Data
public class UserData {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
}
