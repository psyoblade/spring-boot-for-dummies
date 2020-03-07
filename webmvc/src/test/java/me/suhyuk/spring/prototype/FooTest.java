package me.suhyuk.spring.prototype;

import me.suhyuk.spring.prototype.beans.BeanFactory;
import me.suhyuk.spring.prototype.beans.NormalBean;
import me.suhyuk.spring.prototype.beans.PrototypeBean;
import me.suhyuk.spring.prototype.beans.SingletonBean;
import me.suhyuk.spring.prototype.conf.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringPrototypeApplicationTest.class)
public class FooTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void 어플리케이션_컨텍스트() {
        NormalBean normalBean = applicationContext.getBean(NormalBean.class);
        System.out.println(normalBean.getName());
    }

    @Autowired
    private AppConfig appConfig;

    @Test
    public void 프로파일_읽어오기() {
        assertEquals("test", appConfig.getEnvironment());
    }

    @Autowired
    private BeanFactory beanFactory;
    /**
     * 싱글톤 빈은 항상 하나의 빈만 반환하지만 프로토타입 빈의 경우 항상 새로운 빈을 반환합니다
     */
    @Test
    public void 프로토타입_빈_생성() {
        PrototypeBean p1 = beanFactory.getPrototypeBean();
        PrototypeBean p2 = beanFactory.getPrototypeBean();
        assertTrue(p1 != p2);
    }


    /**
     * 프로토타입 빈을 스코프가 다른 싱글톤에 바인딩 하려고 할 때에 어떤 문제가 생기는가?
     * TODO 의도한 대로 생성된 싱글톤으로 동작할지? 아니면 매번 새로운 빈이 생성될 것인가? 동일한 싱글톤이 반환된다
     */
    @Test
    public void 싱글톤_빈_생성() {
        SingletonBean s1 = beanFactory.getSingletonBean();
        SingletonBean s2 = beanFactory.getSingletonBean();
//        assertTrue(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }


//    @Autowired
//    private NormalBeanFactory normalBeanFactory;

    // 1. 그냥 생성하기에는 ComponentScan 이 되어 있어야만 한다
    @Autowired
    private NormalBean normalBean;

    @Test
    public void 노멀_빈_생성() {
        assert(true);
        System.out.println(normalBean.getName());
//        System.out.println(normalBean.getName());
//        NormalBean n1 = normalBeanFactory.getNormalBean();
//        NormalBean n2 = normalBeanFactory.getNormalBean();
//        System.out.println(n1);
//        System.out.println(n2);
    }
}
