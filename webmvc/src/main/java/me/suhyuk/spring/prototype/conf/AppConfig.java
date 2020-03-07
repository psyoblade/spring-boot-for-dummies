package me.suhyuk.spring.prototype.conf;

import me.suhyuk.spring.prototype.beans.BeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration // 빈 설정의 소스로써 클래스임을 표시한다
@EnableConfigurationProperties // 아래의 ConfigurationProperties 를 활성화한다는 의미
@ConfigurationProperties // 외부 configuration 값을 (properties or yml) configuration class 로 바인딩 한다
public class AppConfig {

    private String name;
    private String environment;
    private List<String> servers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

//    @Bean
//    public BeanFactory singletonBean() {
//        return new BeanFactory();
//    }

}
