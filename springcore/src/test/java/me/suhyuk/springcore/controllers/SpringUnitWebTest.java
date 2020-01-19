package me.suhyuk.springcore.controllers;

import me.suhyuk.springcore.entities.SpringUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

// 테스트용 서블릿 톰캣이 임의의 포트에 뜨게되고 테스트용 RestTemplate 을 사용합니다
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SpringUnitWebTest {

    private static String name;

    @BeforeClass
    public static void setUp() {
        name = "park.suhyuk";
    }

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testWebClient() {
        webClient.get().uri("/hello").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(name);
    }

    // 일반 Rest Controller 를 이용한 예제
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testRestTemplate() {
        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("suhyuk");
    }

    // SpringUnit 서비스가 너무 무거운 경우, 서비스를 목업하고 컨트롤러만 테스트하고 싶은 경우가 있는데,
    // 이 때에는 MockBean 을 이용하여 Bean 을 Mocking 할 수 있습니다
    @MockBean
    private SpringUnit springUnit;

    @Test
    public void testMockBeanController() {
        when(springUnit.getName()).thenReturn("yongwhan");
        String yongwhan = testRestTemplate.getForObject("/hello", String.class);
        assertThat(yongwhan).isEqualTo("yongwhan");
    }

    // Java5 Spring WebFlux 에 추가된 RestClient 인데 응답이 오면 콜백하는 Async 한 클라이언트 입니다
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testWebTestClient() {
        // 이 경우 클라이언트 웹플럭스 dependency 가 주입되어 있어야 하는데 일단 돌려보면... NoSuchBeanDefinitionException
        // spring-boot-starter-webflux dependency 추가가 필요합니다
        webTestClient.get().uri("/hello").exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo("suhyuk");
    }

}
