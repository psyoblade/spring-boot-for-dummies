package me.suhyuk.spring.webclient;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    OkHttpClient okHttpClient;

    @Bean
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

}
