package me.suhyuk.spring.boot.rest.services.kafka;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.boot.rest.dto.kafka.*;
import me.suhyuk.spring.boot.rest.utils.Pair;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class KafkaAdminService implements IKafkaAdminService {
    private Map<String, Pair<String, AdminClient>> adminClients;

    private AdminClient getClient(String clusterName) {
        if (!adminClients.containsKey(clusterName)) {
            throw new IllegalArgumentException(String.format("존재하지 않는 클러스터 이름 '%s' 입니다", clusterName));
        }
        return adminClients.get(clusterName).snd;
    }

    @Override
    public KafkaTopicList listTopics(String clusterName) throws ExecutionException, InterruptedException {
        Collection<TopicListing> listings = getClient(clusterName).listTopics().listings().get();
        return KafkaTopicList.builder().listings(listings).build();
    }

    @Override
    public KafkaTopicInfo getTopicInfo(String clusterName, String topicName) throws ExecutionException, InterruptedException {
        Map<String, TopicDescription> topics = getClient(clusterName).describeTopics(Collections.singleton(topicName)).all().get();
        if (!topics.containsKey(topicName))
            throw new IllegalArgumentException(String.format("존재하지 않는 토픽 이름 '%s-%s' 입니다", clusterName, topicName));
        return KafkaTopicInfo.builder().description(topics.get(topicName)).build();
    }

    @Override
    public KafkaTopicConfigs getTopicConfigs(String clusterName, String topicName) throws ExecutionException, InterruptedException {
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, topicName);
        Map<ConfigResource, Config> resources = getClient(clusterName).describeConfigs(Collections.singleton(resource)).all().get();
        return KafkaTopicConfigs.builder().resources(resources).build();
    }

    @Override
    public void createTopic(String clusterName, String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        getClient(clusterName).createTopics(Collections.singleton(newTopic)).all().get();
    }

    public void updateTopicAssignment(String clusterName, UpdateTopicRequest req) throws ExecutionException, InterruptedException {
        Map<TopicPartition, Optional<NewPartitionReassignment>> assignments = new HashMap<>();
        for (UpdateTopicRequest.KafkaReplicaReassignment reassignment : req.getReassignments()) {
            TopicPartition key = new TopicPartition(reassignment.getTopicName(), reassignment.getPartitionNumber());
            NewPartitionReassignment value = new NewPartitionReassignment(reassignment.getBrokerIDs());
            assignments.put(key, Optional.of(value));
        }
        getClient(clusterName).alterPartitionReassignments(assignments).all().get();
    }

    public void createTopicPartition(String clusterName, String topicName, CreatePartitionRequest req) throws ExecutionException, InterruptedException {
        Map<String, NewPartitions> partitions = new HashMap<>();
        partitions.put(topicName, NewPartitions.increaseTo(req.getTotalCount()));
        getClient(clusterName).createPartitions(partitions).all().get();
    }
}
