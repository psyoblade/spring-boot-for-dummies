package me.suhyuk.springcore.listeners;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class BeforeApplicationContextListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("=============================");
        System.out.println("Before Application is started");
        System.out.println("=============================");

    }
}
