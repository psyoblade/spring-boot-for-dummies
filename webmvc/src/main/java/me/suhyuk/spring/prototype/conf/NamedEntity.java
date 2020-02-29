package me.suhyuk.spring.prototype.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamedEntity {
    private Logger logger = LoggerFactory.getLogger(NamedEntity.class);
    private String name;
    private int age;
    private String description;

    public NamedEntity() {
        logger.info("네임드 엔티티 기본 생성자 호출");
    }

    public NamedEntity(String name, int age) {
        logger.info("네임드 엔티티 이름/나이 생성자 호출");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "name: " + name + ", " +
                "age: " + Integer.toString(age);
    }
}
