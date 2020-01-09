package me.suhyuk.springcore.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {
    // TODO application.properties 에 설정된 Key=Value 값을 액세스 하는 예제
    @Value("${psyoblade.name}")
    private String name;
    @Value("${psyoblade.age}")
    private int age;
    @Value("${psyoblade.fullName}")
    private String fullName;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(String.format("'%s' - '%s' is '%d' years old.", fullName, name, age));
    }
}
