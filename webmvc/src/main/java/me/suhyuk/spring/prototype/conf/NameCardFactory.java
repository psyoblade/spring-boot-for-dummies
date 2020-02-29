package me.suhyuk.spring.prototype.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class NameCardFactory {

    @Autowired
    private NameCard nameCard;

    @Bean
    public NameCard singletonNameCard() {
        return new NameCard();
    }

    // 아래와 같이 별도로 객체를 생성하지 않아야 Autowired 된 객체를 사용하게 된다
    @Bean
    public NameCard singletonNameCard(String name) {
        nameCard.setName(name);
        return nameCard;
    }

}
