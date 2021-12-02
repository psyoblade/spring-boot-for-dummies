package me.suhyuk.spring.admin.component;

import me.suhyuk.spring.admin.domain.KafkaTopicConfigs;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;
import me.suhyuk.spring.admin.dto.*;
import me.suhyuk.spring.admin.exception.InternalServerException;

import java.util.List;

public interface IKafkaAdminClient {

    List<KafkaTopicInfo> listTopics(String clusterName);
    void createTopic(String clusterName, CreateKafkaTopicRequest req);
    KafkaTopicInfo readTopicInfo(String clusterName, String topicName);
    void updateTopicPartitions(String clusterName, String topicName, UpdateKafkaTopicRequest req);
    void deleteTopic(String clusterName, String topicName);
    KafkaTopicConfigs readTopicConfigs(String clusterName, String topicName);
}
