package me.suhyuk.spring.cors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class MiniCORSClientServer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MiniCORSClientServer.class);
        app.run(args);
    }
}
