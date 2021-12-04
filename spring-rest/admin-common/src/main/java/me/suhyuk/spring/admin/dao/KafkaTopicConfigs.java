package me.suhyuk.spring.admin.dao;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.Config;

@Getter
@Setter(AccessLevel.PROTECTED)
public class KafkaTopicConfigs extends KafkaTopic {
    private final Config config;
    @Builder
    public KafkaTopicConfigs(String clusterId, String topicName, Boolean isInternal, Config config) {
        super(clusterId, topicName, isInternal);
        this.config = config;
    }
}
