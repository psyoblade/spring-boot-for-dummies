package me.suhyuk.spring.admin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class KafkaTopicConfigs {
    private Map<String, String> configs;
}
