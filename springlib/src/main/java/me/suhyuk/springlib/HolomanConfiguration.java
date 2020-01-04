package me.suhyuk.springlib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HolomanConfiguration {

    @Bean
    @ConditionalOnMissingBean // 여기서 설정된 빈이 ComponentScan 보다 우선하지 않도록 설정하기 위해서
    public Holoman holoman() { // 즉, 빈이 없는 경우에만 이 빈을 써라, 뭔가 프로젝트 내에 빈이 있으면 그걸 써라
        Holoman holoman = new Holoman();
        holoman.setHowLong(45);
        holoman.setName("suhyuk");
        return holoman;
    }
}
