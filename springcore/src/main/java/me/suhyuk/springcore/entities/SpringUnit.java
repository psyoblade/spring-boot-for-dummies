package me.suhyuk.springcore.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.Serializable;

public class SpringUnit implements Serializable {

    @Bean
    public String getName() {
        return "suhyuk";
    }
}
