package me.suhyuk.spring.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

public class SingletonBean {

    private Logger logger = LoggerFactory.getLogger(SingletonBean.class);

    @Autowired
    private PrototypeBean prototypeBean;

    public SingletonBean() {
        logger.info("singleton instance created");
    }

    public PrototypeBean getPrototypeBean() {
        logger.info(String.valueOf(LocalTime.now()));
        return prototypeBean;
    }

}
