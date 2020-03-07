package me.suhyuk.spring.prototype.beans;

import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.Provider;

public class SingletonProviderBean {

    @Autowired
    private Provider<PrototypeBean> myPrototypeBeanProvider;

    public PrototypeBean getPrototypeInstance() {
        return null; // myPrototypeBeanProvider.get(); <-- get 메소드 오류
    }
}
