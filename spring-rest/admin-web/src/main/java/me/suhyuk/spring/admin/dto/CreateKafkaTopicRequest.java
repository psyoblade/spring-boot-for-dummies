package me.suhyuk.spring.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateKafkaTopicRequest {
    private String clusterName;
    private String topicName;
    private int numPartitions;
    private short replicationFactor;
}
