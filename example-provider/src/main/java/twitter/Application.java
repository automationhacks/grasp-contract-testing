package twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter.datamodels.user.User;
import twitter.db.UserDB;

/**
 * "@SprintBootApplication" is a convenience method that adds below annotations in a single annotation
 * "@Configuration"
 * "@EnableAutoConfiguration"
 * "@ComponentScan"
 * <p>
 * Read about this on: https://spring.io/guides/gs/rest-service/
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        initializeDBWithUser();
        SpringApplication.run(Application.class, args);
    }

    private static void initializeDBWithUser() {
        UserDB db = UserDB.getInstance();
        db.addUser(new User("Sayantan", "sayantan", "secretpwd", "5bd9e2acf4d4530015fcf94d"));
    }
}


