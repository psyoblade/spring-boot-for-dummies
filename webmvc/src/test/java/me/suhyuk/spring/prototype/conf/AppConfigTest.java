package me.suhyuk.spring.prototype.conf;

import me.suhyuk.spring.prototype.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringPrototypeApplicationTest.class) // multiple @SpringBootConfiguration annotated classes
@ActiveProfiles("test")
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void 프로파일_읽어오기() {
        assertEquals("test", appConfig.getEnvironment());
    }

    private void printObject(Object o) {
        String id = Integer.toHexString(System.identityHashCode(o));
        String namedEntity = String.format("[%s] %s", id, o);
        System.out.println(namedEntity);
    }

    @Test
    public void 일반적인_객체_생성하기() {
        NamedEntity namedEntity = new NamedEntity("박수혁", 30);
        printObject(namedEntity);
    }

    @Ignore
    public void 싱글톤_빈객체_어플리케이션_컨텍스트를_통해_생성하기() {
        NamedEntity namedEntity = applicationContext.getBean(NamedEntity.class);
        printObject(namedEntity);
    }

    @Autowired
    EntityFactory entityFactory;

    @Test
    public void 싱글톤_빈객체_생성하기() {
        NamedEntity namedEntity = entityFactory.getNamedEntity();
        printObject(namedEntity);
    }

    @Test
    public void 싱글톤_빈객체_파라메터_전달하여_생성하기() {
    }

    @Autowired
    NameCardFactory nameCardFactory;

    @Test
    public void 생성되어있는_빈객체_변경하여_받아오기() {
        NameCard u1 = nameCardFactory.singletonNameCard("팀장");
        NameCard u2 = nameCardFactory.singletonNameCard("팀원");
        assertTrue(u1 == u2);
    }

    @Test
    public void 싱글톤_빈객체_반환하기() {
        NamedEntity s1 = entityFactory.getNamedEntity("박수혁", 30);
        printObject(s1);
        NamedEntity s2 = entityFactory.getNamedEntity("이용환", 40); // 여기서 같은 객체를 변경하게 된다
        printObject(s1);
        printObject(s2);
        assertTrue(s1 == s2);
    }

    @Test
    public void 프로토타입_빈객체_반환하기() {
        NumberEntry p1 = entityFactory.getNumberEntry();
        printObject(p1);
//        UnNamedEntity p2 = namedEntityFactory.getPrototypeUnNamedEntity();
//        printObject(p1);
//        printObject(p2);
//        assertTrue(p1 != p2);
    }
}
