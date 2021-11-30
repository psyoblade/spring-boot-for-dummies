package me.suhyuk.spring.admin.service;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.admin.dao.KafkaTopic;
import me.suhyuk.spring.admin.dao.KafkaTopicConfigs;
import me.suhyuk.spring.admin.dao.KafkaTopicDesc;
import me.suhyuk.spring.admin.dao.KafkaTopicsConfigs;
import me.suhyuk.spring.admin.dto.CreateKafkaTopicRequest;
import me.suhyuk.spring.admin.repository.KafkaConfigsRepository;
import me.suhyuk.spring.admin.repository.KafkaTopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class KafkaAdminService implements IKafkaAdminService {

    private KafkaTopicRepository kafkaTopicRepository;
    private KafkaConfigsRepository kafkaConfigsRepository;

    @Override
    public void createTopic(String clusterName, CreateKafkaTopicRequest req) throws ExecutionException, InterruptedException {
        kafkaTopicRepository.createTopic(clusterName, req.getTopicName(), req.getNumPartitions(), req.getReplicationFactor());
    }

    @Override
    public KafkaTopicDesc describeTopic(String clusterName, String topicName) throws ExecutionException, InterruptedException {
        return kafkaTopicRepository.readTopic(clusterName, topicName);
    }

    @Override
    public List<KafkaTopic> listTopics(String clusterName) throws ExecutionException, InterruptedException {
        return kafkaTopicRepository.listTopics(clusterName);
    }

    @Override
    public void deleteTopic(String clusterName, String topicName) {
        kafkaTopicRepository.deleteTopic(clusterName, topicName);
    }

    @Override
    public KafkaTopicConfigs getTopicConfigs(String clusterName, String topicName) throws ExecutionException, InterruptedException {
        return kafkaConfigsRepository.getTopicConfigs(clusterName, topicName);
    }

    @Override
    public KafkaTopicsConfigs getTopicsConfigs(String clusterName) throws ExecutionException, InterruptedException {
        return kafkaConfigsRepository.getTopicsConfigs(clusterName);
    }

    @Override
    public void setTopicConfigs(String clusterName, String topicName, Map<String, String> params) throws ExecutionException, InterruptedException {
        kafkaConfigsRepository.setTopicConfigs(clusterName, topicName, params);
    }

    @Override
    public void setTopicsConfigs(String clusterName, Map<String, Map<String, String>> params) throws ExecutionException, InterruptedException {
        kafkaConfigsRepository.setTopicsConfigs(clusterName, params);
    }

    @Override
    public void resetTopicConfigs(String clusterName, String topicName) {
        kafkaConfigsRepository.resetTopicConfigs(clusterName, topicName);
    }
}
