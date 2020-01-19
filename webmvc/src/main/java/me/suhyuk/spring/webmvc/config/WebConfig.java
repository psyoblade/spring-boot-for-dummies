package me.suhyuk.spring.webmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc 설정이 너무 많기 때문에 직접 수정할 일은 없을 것이다
public class WebConfig implements WebMvcConfigurer  {
    // 인터페이스에서 각종 내가 커스터마이징 할 것 들은 이렇게 구현할 수 있다
}
