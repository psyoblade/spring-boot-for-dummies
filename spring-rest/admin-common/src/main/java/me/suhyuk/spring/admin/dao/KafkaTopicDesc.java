package me.suhyuk.spring.admin.dao;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.acl.AclOperation;

import java.util.List;
import java.util.Set;

@Getter
@Setter(AccessLevel.PROTECTED)
public class KafkaTopicDesc extends KafkaTopic {
    private final List<TopicPartitionInfo> partitions;
    private final Set<AclOperation> authorizedOperations;
    @Builder
    public KafkaTopicDesc(String clusterName, String topicName, Boolean isInternal, List<TopicPartitionInfo> partitions, Set<AclOperation> authorizedOperations) {
        super(clusterName, topicName, isInternal);
        this.partitions = partitions;
        this.authorizedOperations = authorizedOperations;
    }
}