package org.dreamexposure.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = SessionAutoConfiguration.class)
public class Main {
    public static void main(String[] args) throws IOException {
        //Init spring
        SpringApplication.run(Main.class, args);
    }
}
