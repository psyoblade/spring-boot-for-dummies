package me.suhyuk.springcore.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/* Created by psyoblade on 2023-06-12 */
// 아래와 같이 테스트를 수행하는 경우 resource 경로에 있는 application.yml 설정을 읽어서 프로파일을 로딩하게 된다
@SpringBootTest // 의존성 주입을 위해서 명시적으로 선언해 주어야만 의존성 객체가 생성된다
@ActiveProfiles("test") // 프로파일이 여러개인 경우 지정해준다
@EnableConfigurationProperties(ConfigProperties.class) // ConfigProperties 객체를 명시적으로 활성화 해주어야 한다
@RunWith(SpringRunner.class)
public class TestConfigurationTest {

    @Autowired ConfigProperties configProperties;
    @Autowired ExternalProperties externalProperties;

    @Test
    public void testConfig() {
        Assertions.assertThat(true).isTrue();
    }

    @Test
    public void testConfigProperties() {
        System.out.println("configProperties = " + configProperties);
    }

    @Test
    public void testAccessProperties() {
        Assertions.assertThat(externalProperties.getAge()).isEqualTo(1);
    }

    // 프로퍼티 파일에 프로파일이 존재해야 혼돈이 오지 않는다 - 프로파일이 없는 경우 엉뚱한 프로파일을 읽어올 수 있음에 유의
    @Test
    public void testDefaultNickName() {
        // 해당하는 키가 존재하는 경우 default 값과 무관하게 해당 값을 반환
        Assertions.assertThat(configProperties.getExistsInProperties()).isEqualTo("real-nick-name-test");
        // 해당 키가 존재하더라도 값이 없는 경우(empty) 그대로 empty 반환
        Assertions.assertThat(configProperties.getExistsNullInProperties()).isEmpty();
        // 키가 아예 존재하지 않는 경우에는 default 값을 반환
        Assertions.assertThat(configProperties.getNotExistsInProperties()).isEqualTo("declared-nick-name");
        // 파일에도 없고 기본값도 없는 경우를 위해 get 메소드에서 처리된 값을 반환
        Assertions.assertThat(configProperties.getNotExistsAnywhere()).isEqualTo("get-null-nick-name");
    }
}
