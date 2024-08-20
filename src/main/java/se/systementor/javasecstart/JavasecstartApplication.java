package se.systementor.javasecstart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.systementor.javasecstart.security.UserDataSeeder;

@SpringBootApplication
public class JavasecstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavasecstartApplication.class, args);
    }

    @Bean
    public CommandLineRunner userSeed(UserDataSeeder userDataSeeder) {
        return (args) -> {
            userDataSeeder.seed();
        };
    }

}
