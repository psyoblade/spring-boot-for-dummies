package me.suhyuk.spring.prototype.person;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class PersonFactory {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    Person singletonPerson() {
        return new Person();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    Person prototypePerson() {
        return new Person();
    }
}
