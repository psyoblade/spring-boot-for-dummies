package me.suhyuk.spring.webclient;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@SpringBootApplication
public class WebClientApplication implements ApplicationRunner {

    private static final String URL1 = "http://localhost:8080/url1";
    private static final String URL2 = "http://localhost:8080/url2";
    private static final String URL3 = "http://localhost:8080/url3";
    private static final String URL4 = "http://localhost:8080/url4";

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
        usingOkHttpSynchronousGET();
        usingOkHttpAsynchronousGET();
    }

    /**
     * 순차적으로 로그가 찍히고 각 API 호출도 Blocking 되는 것을 확인할 수 있고
     * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#rest-client-access">1.8. REST Endpoints</a>
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
     * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client">2. Web Client</a>
     */
    private void usingWebClient() {
        System.out.println("웹 클라이언트를 이용한 웹 클라이언트 Started");
        WebClient webClient = webClientBuilder.build();
        webClient.get().uri(URL1).retrieve().bodyToMono(String.class).subscribe();
        webClient.get().uri(URL2).retrieve().bodyToMono(String.class).subscribe();
        System.out.println("웹 클라이언트를 이용한 웹 클라이언트 Ended");
    }

    /**
     * 스프링 외에도 동기 블로킹 및 비동기 논블로킹 HTTP Client 중에 OkHttp 를 사용해 보았습니다
     * <a href="https://www.baeldung.com/guide-to-okhttp">Guide to OkHttp</a>
     */
    private void usingOkHttpSynchronousGET() throws IOException {
        System.out.println("Ok Http 동기 동기 메소드 를 이용한 웹 클라이언트 Started");
        OkHttpClient client = new OkHttpClient();
        client.newCall(new Request.Builder().url(URL3).build()).execute();
        client.newCall(new Request.Builder().url(URL4).build()).execute();
        System.out.println("Ok Http 동기 동기 메소드 를 이용한 웹 클라이언트 Ended");
    }


    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * OkHttp 클라이언트 이용한 비동기 논블로킹 예제
     */
    private void usingOkHttpAsynchronousGET() throws IOException {
        System.out.println("Ok Http 비동기 비동기 메소드 를 이용한 웹 클라이언트 Started");
        okHttpClient.newCall(new Request.Builder().url(URL3).build()).execute();
        okHttpClient.newCall(new Request.Builder().url(URL4).build()).execute();
        System.out.println("Ok Http 비동기 비동기 메소드 를 이용한 웹 클라이언트 Ended");
    }

}
