package me.suhyuk.spring.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootGlobalExceptionApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootGlobalExceptionApplication.class);
        app.run(args);
    }

}
