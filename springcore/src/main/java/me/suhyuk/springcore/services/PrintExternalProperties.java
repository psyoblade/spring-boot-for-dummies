package me.suhyuk.springcore.services;

import me.suhyuk.springcore.config.ExternalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PrintExternalProperties implements ApplicationRunner {
    // TODO application.properties 에 설정된 Key=Value 값을 액세스 하는 예제
    @Value("${psyoblade.name}")
    private String name;
    @Value("${psyoblade.age}")
    private int age;
    @Value("${psyoblade.fullName}")
    private String fullName;

    @Autowired
    private ExternalProperties props = new ExternalProperties();

    private String getSessionDuration() {
        return String.format("%s", props.getSessionTimeout());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String defaultValue = String.format("'%s' - '%s' is '%d' years old.", fullName, name, age);
        String externalConf = String.format("'%s' - '%s' i s'%d' years old.", props.getName(), props.getFullName(), props.getAge());
        System.out.println("=====================");
        System.out.println("internal:" + defaultValue + ", external:" + externalConf + ", timeout:" + getSessionDuration());
        System.out.println("Full-name is " + props.getFullName());
        System.out.println("=====================");
    }
}
