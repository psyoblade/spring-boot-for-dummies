package me.suhyuk.spring.prototype;

import me.suhyuk.spring.prototype.conf.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPrototypeApplicationTest {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringPrototypeApplicationTest.class);
        application.run();
    }
}

