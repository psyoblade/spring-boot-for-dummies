package me.suhyuk.spring.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class SingletonBean {

    private Logger logger = LoggerFactory.getLogger(SingletonBean.class);
    private String name;

    public SingletonBean(String name) {
        this.name = name;
        logger.info("##### Singleton instance " + name + " created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
