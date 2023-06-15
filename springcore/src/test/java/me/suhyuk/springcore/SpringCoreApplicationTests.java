package me.suhyuk.springcore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

// 아래와 같은 설정은 하나의 Spring Application 이 아니므로 단위 테스트에서 참조하여 활성화 되지 않는다
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:/local.properties")
@SpringBootTest(properties = "psyoblade.age=${random.int}")
public class SpringCoreApplicationTests {

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {
        assertThat(environment.getProperty("psyoblade.name"))
                .isEqualTo("park.suhyuk");
    }

}
