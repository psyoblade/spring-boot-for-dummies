package me.suhyuk.spring.prototype;

import me.suhyuk.spring.prototype.conf.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPrototypeApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(SpringPrototypeApplication.class);

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringPrototypeApplication.class);
        app.run();
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("현재 설정된 프로파일은 {} 입니다.", appConfig.getEnvironment());
    }
}
