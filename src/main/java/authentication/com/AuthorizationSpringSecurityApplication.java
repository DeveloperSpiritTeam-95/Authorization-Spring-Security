package authentication.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationSpringSecurityApplication.class, args);
        System.out.println("Running...@");
    }

}
