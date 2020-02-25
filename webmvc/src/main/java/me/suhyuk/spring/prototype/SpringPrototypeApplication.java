package me.suhyuk.spring.prototype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPrototypeApplication implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringPrototypeApplication.class);
        app.run();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(appConfig.getEnvironment());
    }
}
