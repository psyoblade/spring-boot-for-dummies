package me.suhyuk.spring.prototype.conf;

import me.suhyuk.spring.prototype.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringPrototypeApplicationTest.class) // multiple @SpringBootConfiguration annotated classes
@ActiveProfiles("test")
public class AppConfigTest {
    private Logger logger = LoggerFactory.getLogger(AppConfigTest.class);

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
        logger.info("PrintObject {}", namedEntity);
    }

    @Test
    public void 일반적인_객체_생성하기() {
        NamedEntity namedEntity = new NamedEntity();
        namedEntity.setName("박수혁");
        namedEntity.setAge(45);
        printObject(namedEntity);
    }

    @Test
    public void 두개이상의_생성자가있는경우_기본생성자_호출() {
        NamedEntity namedEntity = applicationContext.getBean(NamedEntity.class);
        printObject(namedEntity);
        assertEquals("홍길동", namedEntity.getName());
        assertEquals(577, namedEntity.getAge());
    }

    @Autowired
    EntityFactory entityFactory;

    @Test
    public void 같은객체를_팩토리를통해_빈주입객체_가져오기() {
        NamedEntity namedEntity = entityFactory.getNamedEntity();
        printObject(namedEntity);
        assertEquals("홍길동", namedEntity.getName());
        assertEquals(577, namedEntity.getAge());
    }

    private String getHashCode(Object o) {
        String hashCode = Integer.toHexString(System.identityHashCode(o));
        return hashCode;
    }

    @Test
    public void 빈객체의_싱글톤여부_확인() {
        NamedEntity e1 = entityFactory.getNamedEntity();
        NamedEntity e2 = entityFactory.getNamedEntity();
        String c1 = getHashCode(e1);
        String c2 = getHashCode(e2);
        logger.info("{} == {}", c1, c2);
        assertEquals(c1, c2);
    }

    @Test
    public void 싱글톤객체의_파라메터사용() {
        NamedEntity e1 = entityFactory.getNamedEntity("박수혁", 45);
        NamedEntity e2 = entityFactory.getNamedEntity("이용환", 28);
        logger.info("{} == {}", e1, e2);
        assertEquals(e1, e2);
    }

    @Test
    public void 프로토타입객체_확인() {
        NumberEntry e1 = entityFactory.getNumberEntry();
        NumberEntry e2 = entityFactory.getNumberEntry();
        String c1 = getHashCode(e1);
        String c2 = getHashCode(e2);
        logger.info("{} != {}", c1, c2);
        assertNotSame(c1, c2);
    }

}
