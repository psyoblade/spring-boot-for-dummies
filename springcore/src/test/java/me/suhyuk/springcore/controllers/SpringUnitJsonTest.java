package me.suhyuk.springcore.controllers;

import me.suhyuk.springcore.entities.SpringUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

// 아무런 종속성이 없는 스프링과 무관한 단위 테스트가 필요한 경우 혹은 JSON 관련 라이브러리만 사용하는 경우
@RunWith(SpringRunner.class)
@JsonTest
public class SpringUnitJsonTest {

    @Autowired
    private JacksonTester<SpringUnit> json;

    @Test
    public void testParseJson() throws IOException {
        String suhyuk = "{\"name\":\"suhyuk\", \"age\":45}";
        assertThat(this.json.parseObject(suhyuk).getName())
                .isEqualTo("suhyuk");
    }
}
