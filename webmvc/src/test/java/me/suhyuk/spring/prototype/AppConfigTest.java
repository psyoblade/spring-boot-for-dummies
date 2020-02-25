package me.suhyuk.spring.prototype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles(profiles = "test")
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void 프로파일_읽어오기() {
        assertEquals("test", appConfig.getEnvironment());
    }

    @Autowired PrototypeBean firstPrototypeBean;
    @Autowired PrototypeBean secondPrototypeBean;
    @Test
    public void 프로토타입_빈_생성() {
        assertTrue(firstPrototypeBean != secondPrototypeBean);
    }

    /**
     * 싱글톤 빈은 항상 하나의 빈만 반환하지만 프로토타입 빈의 경우 항상 새로운 빈을 반환합니다
     */
    @Test
    public void 컨피그를_통한_프로토타입빈() {
        PrototypeBean p1 = appConfig.prototypeBean();
        PrototypeBean p2 = appConfig.prototypeBean();
        NormalBean p3 = appConfig.normalBean();
        NormalBean p4 = appConfig.normalBean();
        assertTrue(p1 != p2);
        assertTrue(p3 == p4);
    }

    /**
     * 프로토타입 빈을 스코프가 다른 싱글톤에 바인딩 하려고 할 때에 어떤 문제가 생기는가?
     * TODO 의도한 대로 생성된 싱글톤으로 동작할지? 아니면 매번 새로운 빈이 생성될 것인가? 동일한 싱글톤이 반환된다
     */
    @Test
    public void 싱글톤_프로토타입_빈생성() {
        PrototypeBean s1 = appConfig.singletonBean().getPrototypeBean();
        PrototypeBean s2 = appConfig.singletonBean().getPrototypeBean();
        assertTrue(s1 == s2);
    }


}
