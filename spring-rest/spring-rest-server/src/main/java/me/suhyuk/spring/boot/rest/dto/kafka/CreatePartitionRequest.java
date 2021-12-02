package me.suhyuk.spring.boot.rest.dto.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePartitionRequest {
    private String clusterName;
    private String topicName;
    private int totalCount;
}
