package apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        SpringApplication.run(Application.class, args);
    }
}
