package me.suhyuk.springcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class MainConfiguration {

    @Bean
    public String sayHello() {
        return "hello-main-configuration";
    }
}
