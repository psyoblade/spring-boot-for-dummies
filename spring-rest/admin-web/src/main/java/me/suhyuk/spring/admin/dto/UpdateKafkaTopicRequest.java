package me.suhyuk.spring.admin.dto;

import lombok.Builder;
import lombok.Getter;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;

import java.util.List;

@Getter
@Builder
public class UpdateKafkaTopicRequest {
    private String clusterName;
    private List<KafkaReplicaReassignment> reassignments;

    @Getter
    public class KafkaReplicaReassignment {
        private String topicName;
        private int partitionNumber;
        private List<Integer> brokerIDs;
    }
}
