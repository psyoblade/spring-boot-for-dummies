package me.suhyuk.spring.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrototypeBean {

    private Logger logger = LoggerFactory.getLogger(PrototypeBean.class);
    private String name;

    public PrototypeBean(String name) {
        this.name = name;
        logger.info("##### Prototype instance " + name + " created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
