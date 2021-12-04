package me.suhyuk.spring.admin.service;

import me.suhyuk.spring.admin.dao.KafkaTopic;
import me.suhyuk.spring.admin.dao.KafkaTopicConfigs;
import me.suhyuk.spring.admin.dao.KafkaTopicDesc;
import me.suhyuk.spring.admin.dao.KafkaTopicsConfigs;
import me.suhyuk.spring.admin.dto.CreateKafkaTopicRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IKafkaAdminService {

    // CREATE 1 TOPIC
    void createTopic(String clusterName, CreateKafkaTopicRequest req) throws ExecutionException, InterruptedException;

    // DESCRIBE 1 TOPIC
    public KafkaTopicDesc describeTopic(String clusterName, String topicName) throws ExecutionException, InterruptedException;

    // LIST N TOPICS
    public List<KafkaTopic> listTopics(String clusterName) throws ExecutionException, InterruptedException;

    // DELETE 1 TOPIC
    void deleteTopic(String clusterName, String topicName);

    // GET 1 TOPIC CONFIGS
    public KafkaTopicConfigs getTopicConfigs(String clusterName, String topicName) throws ExecutionException, InterruptedException;

    // GET N TOPICS CONFIGS
    public KafkaTopicsConfigs getTopicsConfigs(String clusterName) throws ExecutionException, InterruptedException;

    // UPDATE 1 TOPIC CONFIGS
    void setTopicConfigs(String clusterName, String topicName, Map<String, String> params) throws ExecutionException, InterruptedException;

    // UPDATE N TOPIC CONFIGS
    void setTopicsConfigs(String clusterName, Map<String, Map<String, String>> params) throws ExecutionException, InterruptedException;

    // RESET 1 TOPIC CONFIGS
    void resetTopicConfigs(String clusterName, String topicName);

    // UPDATE 1 TOPIC PARTITIONS
    void updatePartition(String clusterName, String topicName, int numPartitions);
}
