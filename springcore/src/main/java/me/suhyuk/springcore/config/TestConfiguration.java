package me.suhyuk.springcore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// 특정 프로파일에서만 동작하는 경우에는 해당 프로파일이 아닌 경우는 fail to load application 문제가 생길 수 있다
@Profile("test")
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class TestConfiguration {

    @Bean
    public String sayHello() {
        return "hello-test-configuration";
    }
}
