package me.suhyuk.spring.prototype.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class EntityFactory {
    private Logger logger = LoggerFactory.getLogger(EntityFactory.class);

    public EntityFactory() {
        logger.info("네임드 엔티티 팩토리 객체 생성");
    }

    @Bean
    public NamedEntity getNamedEntity() {
        NamedEntity namedEntity = new NamedEntity();
        namedEntity.setName("홍길동");
        namedEntity.setAge(577);
        return namedEntity;
    }

    @Bean
    public NamedEntity getNamedEntity(String name, int age) {
        return new NamedEntity(name, age);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public NumberEntry getNumberEntry() {
        NumberEntry numberEntry = new NumberEntry();
        numberEntry.setName("홍길동");
        numberEntry.setAge(577);
        return numberEntry;
    }

}
