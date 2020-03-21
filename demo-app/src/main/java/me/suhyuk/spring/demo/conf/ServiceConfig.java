package me.suhyuk.spring.demo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Autowired
    private HiddenService hiddenService;

    public ServiceConfig(HiddenService localService) {
        this.hiddenService = localService;
    }

    public boolean executeQuery(String args) {
        return hiddenService.executeQuery(args);
    }

}
