package me.suhyuk.spring.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

//@Component
public class NormalBeanFactory {

    private Logger logger = LoggerFactory.getLogger(NormalBeanFactory.class);

//    @Autowired
//    private NormalBean normalBean;
//
//    @Bean
//    public NormalBean getNormalBean() {
//        return normalBean;
//    }
}
