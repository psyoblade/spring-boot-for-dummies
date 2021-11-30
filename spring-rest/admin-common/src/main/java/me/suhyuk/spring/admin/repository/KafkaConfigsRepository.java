package me.suhyuk.spring.admin.repository;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.admin.dao.KafkaTopicConfigs;
import me.suhyuk.spring.admin.dao.KafkaTopicsConfigs;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
@AllArgsConstructor
public class KafkaConfigsRepository {

    // READ 1 TOPIC CONFIGS
    public KafkaTopicConfigs getTopicConfigs(String clusterName, String topicName) throws ExecutionException, InterruptedException {
        return null;
    }

    // READ N TOPICS CONFIGS
    public KafkaTopicsConfigs getTopicsConfigs(String clusterName) throws ExecutionException, InterruptedException {
        return null;
    }


    // UPDATE 1 TOPIC CONFIGS
    public void setTopicConfigs(String clusterName, String topicName, Map<String, String> params) throws ExecutionException, InterruptedException {
    }

    // UPDATE N TOPIC CONFIGS
    public void setTopicsConfigs(String clusterName, Map<String, Map<String, String>> params) throws ExecutionException, InterruptedException {
    }

    // RESET 1 TOPIC CONFIGS
    public void resetTopicConfigs(String clusterName, String topicName) {
    }

}
