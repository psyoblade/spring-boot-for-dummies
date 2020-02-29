package me.suhyuk.spring.prototype;

import me.suhyuk.spring.prototype.conf.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("test")
public class SpringPrototypeApplicationTest {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringPrototypeApplicationTest.class);
        System.out.println("현재 프로파일은 무엇인가요?");
        app.run();
    }
}
