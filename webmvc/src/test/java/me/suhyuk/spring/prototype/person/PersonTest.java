package me.suhyuk.spring.prototype.person;

import me.suhyuk.spring.prototype.SpringPrototypeApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = SpringPrototypeApplicationTest.class) // multiple @SpringBootConfiguration annotated classes
public class PersonTest {
    private Logger logger = LoggerFactory.getLogger(PersonTest.class);

    @Autowired
    PersonFactory personFactory;

    private String getHashCode(Object o) {
        String hashCode = Integer.toHexString(System.identityHashCode(o));
        return hashCode;
    }

    @Test
    public void 싱글톤() {
        Person p1 = personFactory.singletonPerson();
        Person p2 = personFactory.singletonPerson();
        logger.info("\nPerson:{}, \nPerson:{}", p1, p2);
        assertEquals(p1, p2);
    }

    @Test
    public void 프로토타입() {
        Person p1 = personFactory.prototypePerson();
        Person p2 = personFactory.prototypePerson();
        logger.info("\nPerson:{}, \nPerson:{}", p1, p2);
        assertNotSame(p1, p2);
    }

}