package me.suhyuk.spring.admin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.acl.AclOperation;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@ToString
public class KafkaTopicInfo {
    private final String clusterName;
    private final String topicName;
    private final Boolean isInternal;
}
