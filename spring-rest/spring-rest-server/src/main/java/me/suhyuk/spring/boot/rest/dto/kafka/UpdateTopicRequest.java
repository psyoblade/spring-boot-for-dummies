package me.suhyuk.spring.boot.rest.dto.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UpdateTopicRequest {
    private String clusterName;
    private List<KafkaReplicaReassignment> reassignments;

    @Getter
    @Setter
    public static class KafkaReplicaReassignment {
        private String topicName;
        private int partitionNumber;
        private List<Integer> brokerIDs;
    }
}
