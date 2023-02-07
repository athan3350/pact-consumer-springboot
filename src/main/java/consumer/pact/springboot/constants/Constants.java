package consumer.pact.springboot.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS_MESSAGE_EXCEPTION);
    }

    public static final String GET_USER_PATH = "/api/v1/users/1";
    public static final String APPLICATION_JSON = "application/json; charset=utf-8";
    public static final String UTILITY_CLASS_MESSAGE_EXCEPTION = "Utility class";

}
