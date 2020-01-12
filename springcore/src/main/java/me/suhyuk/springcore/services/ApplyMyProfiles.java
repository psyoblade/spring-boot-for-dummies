package me.suhyuk.springcore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplyMyProfiles implements ApplicationRunner {

    @Autowired
    private String thisIsNotSayHello;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(" 안녕하세요 : " + thisIsNotSayHello);
    }
}
