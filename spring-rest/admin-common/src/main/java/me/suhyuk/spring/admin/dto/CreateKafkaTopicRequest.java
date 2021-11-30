package me.suhyuk.spring.admin.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class CreateKafkaTopicRequest {
    private String topicName;
    private int numPartitions;
    private short replicationFactor;
    @Builder
    public CreateKafkaTopicRequest(String topicName, int numPartitions, short replicationFactor) {
        this.topicName = topicName;
        this.numPartitions = numPartitions;
        this.replicationFactor = replicationFactor;
    }
}
