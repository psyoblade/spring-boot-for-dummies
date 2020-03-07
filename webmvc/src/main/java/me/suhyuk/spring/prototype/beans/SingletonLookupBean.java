package me.suhyuk.spring.prototype.beans;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

//@Component
public class SingletonLookupBean {

    @Lookup
    public PrototypeBean getPrototypeBean() { //String name) {
        return null; // new PrototypeBean(name);
    }
}
