package me.suhyuk.spring.web.service;

import me.suhyuk.spring.web.HelloWorldApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = HelloWorldApplication.class)
class FooServiceTest {

    @Autowired
    FooService fooService;

    @Test
    void bar() {
        // When ... Then // Execute // Assert
        assertEquals("this is bar repository", fooService.bar());
    }
}