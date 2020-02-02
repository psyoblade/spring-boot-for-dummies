package me.suhyuk.spring.webmvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@EnableWebMvc // 설정이 너무 많기 때문에 직접 수정할 일은 없을 것이다
public class WebConfig implements WebMvcConfigurer  {
    private Logger logger = LoggerFactory.getLogger(WebConfig.class);
    // 인터페이스에서 각종 내가 커스터마이징 할 것 들은 이렇게 구현할 수 있다
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("addResourceHandlers applied");
        registry.addResourceHandler("/m/**").setCachePeriod(0);
    }
}
