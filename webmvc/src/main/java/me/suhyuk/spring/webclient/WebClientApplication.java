package me.suhyuk.spring.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebClientApplication implements ApplicationRunner {

    private static final String URL1 = "http://localhost:8080/url1";
    private static final String URL2 = "http://localhost:8080/url2";

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebClientApplication.class);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        usingRestTemplate();
        usingWebClient();
    }

    /**
     * 순차적으로 로그가 찍히고 각 API 호출도 Blocking 되는 것을 확인할 수 있고
     */
    private void usingRestTemplate() {
        System.out.println("레스트 템플릿을 이용한 웹 클라이언트 Started");
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.getForObject(URL1, String.class);
        restTemplate.getForObject(URL2, String.class);
        System.out.println("레스트 템플릿을 이용한 웹 클라이언트 Ended");
    }

    /**
     * Started, Ended 로그가 미리 다 찍히고 두 API 호출이 동시에 수행되는 것을 확인할 수 있습니다
     */
    private void usingWebClient() {
        System.out.println("웹 클라이언트를 이용한 웹 클라이언트 Started");
        WebClient webClient = webClientBuilder.build();
        webClient.get().uri(URL1).retrieve().bodyToMono(String.class).subscribe();
        webClient.get().uri(URL2).retrieve().bodyToMono(String.class).subscribe();
        System.out.println("웹 클라이언트를 이용한 웹 클라이언트 Ended");
    }

}
