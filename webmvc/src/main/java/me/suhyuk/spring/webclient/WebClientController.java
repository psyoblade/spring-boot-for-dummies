package me.suhyuk.spring.webclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebClientController {
    private static final long threeSeconds = 3000L;

    @GetMapping("/url1")
    @StopWatchLog
    public String urlOne() throws InterruptedException {
        Thread.sleep(threeSeconds);
        return "respond url one after 3 second";
    }

    @GetMapping("/url2")
    @StopWatchLog
    public String urlTwo() throws InterruptedException {
        Thread.sleep(threeSeconds);
        return "respond url two after 3 seconds";
    }

    @GetMapping("/url3")
    @StopWatchLog
    public String urlThree() throws InterruptedException {
        Thread.sleep(threeSeconds);
        return "respond url three after 3 seconds";
    }

    @GetMapping("/url4")
    @StopWatchLog
    public String urlFour() throws InterruptedException {
        Thread.sleep(threeSeconds);
        return "respond url four after 3 seconds";
    }

}
