package me.suhyuk.spring.prototype.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class EntityFactory {
    private Logger logger = LoggerFactory.getLogger(EntityFactory.class);

    @Autowired
    private NamedEntity namedEntity;

    public EntityFactory() {
        logger.info("네임드 엔티티 팩토리 객체 생성");
    }

    @Bean
    public NamedEntity getNamedEntity() {
        return new NamedEntity("홍길동", 0);
    }

    @Bean
    public NamedEntity getNamedEntity(String name, int age) {
        namedEntity.setName(name);
        namedEntity.setAge(age);
        return namedEntity;
    }

    @Autowired
    private NumberEntry numberEntry;

//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,
//            proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Bean
    public NumberEntry getNumberEntry() {
        numberEntry.setName("홍길동");
        return numberEntry;
    }

}
