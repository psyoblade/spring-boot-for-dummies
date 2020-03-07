package me.suhyuk.spring.prototype.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//@Service
public class NormalBean {
    private Logger logger = LoggerFactory.getLogger(NormalBean.class);
    private String name;

    public NormalBean() {
        logger.info("##### 노멀 빈 생성 완료 #####");
    }

    public NormalBean(String name) {
        logger.info("##### 노멀 빈 " + name + " 생성 완료 #####");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
