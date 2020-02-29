package me.suhyuk.spring.prototype.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameCard {
    private Logger logger = LoggerFactory.getLogger(NameCard.class);
    private String name;

    public NameCard() {
        logger.info("네임카드 기본 생성자 호출");
    }

    public NameCard(String name) {
        logger.info("네임카드 이름 생성자 호출");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name: " + name;
    }
}
