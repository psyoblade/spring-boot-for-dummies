package me.suhyuk.spring.admin.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration
public class KafkaAdminTestConfiguration {

    @Bean
    @Primary
    public KafkaConfiguration.AdminClients getAdminClients() {
        return Mockito.mock(KafkaConfiguration.AdminClients.class);
    }
}
