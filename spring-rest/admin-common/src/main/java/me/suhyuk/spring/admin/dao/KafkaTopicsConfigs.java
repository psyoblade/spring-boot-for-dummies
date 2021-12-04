package me.suhyuk.spring.admin.dao;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.common.config.ConfigResource;

import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
public class KafkaTopicsConfigs {
    private final String clusterName;
    private final Map<ConfigResource, Config> resources;
    @Builder
    public KafkaTopicsConfigs(String clusterName, Map<ConfigResource, Config> resources) {
        this.clusterName = clusterName;
        this.resources = resources;
    }
}
