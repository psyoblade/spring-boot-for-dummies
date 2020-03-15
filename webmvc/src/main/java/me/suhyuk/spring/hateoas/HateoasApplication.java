package me.suhyuk.spring.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HateoasApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HateoasApplication.class);
        app.run(args);
    }
}
