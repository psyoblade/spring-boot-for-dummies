package me.suhyuk.spring.webclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebClientController {

    @GetMapping("/url1")
    @StopWatchLog
    public String urlOne() throws InterruptedException {
        Thread.sleep(5000L);
        return "respond url one after 1 second";
    }

    @GetMapping("/url2")
    @StopWatchLog
    public String urlTwo() throws InterruptedException {
        Thread.sleep(5000L);
        return "respond url two after 2 seconds";
    }

}
