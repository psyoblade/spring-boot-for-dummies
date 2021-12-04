package me.suhyuk.spring.admin.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.acl.AclOperation;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class KafkaTopicInfo {
    private final String clusterName;
    private final String topicName;
    private final Boolean isInternal;
}
