package me.suhyuk.spring.prototype.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import java.time.LocalTime;

//@Service
public class BeanFactory {

    private Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    @Bean
    public SingletonBean getSingletonBean() {
        return new SingletonBean(LocalTime.now().toString());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public PrototypeBean getPrototypeBean() {
        return new PrototypeBean(LocalTime.now().toString());
    }

}
