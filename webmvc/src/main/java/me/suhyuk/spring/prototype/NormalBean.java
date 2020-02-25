package me.suhyuk.spring.prototype;

public class NormalBean {
    private String name;

    public NormalBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
