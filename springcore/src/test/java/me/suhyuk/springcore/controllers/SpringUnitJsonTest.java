package me.suhyuk.springcore.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class SpringUnitJsonTest {

    @Autowired
    private JacksonTester<SpringUnit> json;

    @Test
    public void testParseJson() throws IOException {
        String content = "{\"make\":\"Ford\",\"model\":\"Focus\"}";
        String suhyuk = "{\"name\":\"suhyuk\"}";
        assertThat(this.json.parseObject(suhyuk).getName()).isEqualTo("suhyuk");
    }
}
