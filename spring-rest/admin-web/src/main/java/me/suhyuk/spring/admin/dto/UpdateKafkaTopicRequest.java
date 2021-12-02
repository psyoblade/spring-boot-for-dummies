package me.suhyuk.spring.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateKafkaTopicRequest {
    private String clusterName;
    private String topicName;
    private int partitionNumber;
    private short replicationFactor;
}
