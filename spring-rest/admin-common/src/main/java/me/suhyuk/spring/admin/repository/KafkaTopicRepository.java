package me.suhyuk.spring.admin.repository;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.AllArgsConstructor;
import me.suhyuk.spring.admin.common.ExceptionUtils;
import me.suhyuk.spring.admin.common.Pair;
import me.suhyuk.spring.admin.dao.KafkaTopic;
import me.suhyuk.spring.admin.dao.KafkaTopicDesc;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 카프카 토픽 관리를 위한 더미 레포지토리
 *
 * @author psyoblade
 */
@Repository
@AllArgsConstructor
public class KafkaTopicRepository {

    private Map<String, Pair<String, AdminClient>> adminClients;

    private AdminClient getClient(String clusterName) {
        if (!adminClients.containsKey(clusterName)) {
            throw new IllegalArgumentException(String.format("존재하지 않는 클러스터 이름 '%s' 입니다", clusterName));
        }
        return adminClients.get(clusterName).snd;
    }

    // CREATE 1 TOPIC
    public void createTopic(String clusterName, String topicName, int numPartitions, short replicationFactor) {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        try {
            getClient(clusterName).createTopics(Collections.singleton(newTopic)).all().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalException(String.format("토픽 생성시에 오류가 발생했습니다 - %s", ExceptionUtils.printStackTrace(e)));
        }
    }

    // READ 1 TOPIC
    public KafkaTopicDesc readTopic(String clusterName, String topicName) {
        Map<String, TopicDescription> topics = null;
        try {
            topics = getClient(clusterName).describeTopics(Collections.singleton(topicName)).all().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalException(String.format("토픽정보 조회시에 오류가 발생했습니다 - %s", ExceptionUtils.printStackTrace(e)));
        }
        if (!topics.containsKey(topicName))
            throw new IllegalArgumentException(String.format("존재하지 않는 토픽 이름 '%s-%s' 입니다", clusterName, topicName));
        TopicDescription desc = topics.get(topicName);
        return KafkaTopicDesc.builder()
                .clusterName(clusterName)
                .topicName(topicName)
                .isInternal(desc.isInternal())
                .partitions(desc.partitions())
                .authorizedOperations(desc.authorizedOperations())
                .build();
    }

    // UPDATE 1 TOPIC - NOT SUPPORT

    // LIST N TOPICS
    public List<KafkaTopic> listTopics(String clusterName) {
        Collection<TopicListing> listings = null;
        try {
            listings = getClient(clusterName).listTopics().listings().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalException(String.format("토픽목록 조회시에 오류가 발생했습니다 - %s", ExceptionUtils.printStackTrace(e)));
        }
        List<KafkaTopic> list = new ArrayList<>();
        listings.forEach(topic -> list.add(new KafkaTopic(clusterName, topic.name(), topic.isInternal())));
        return list;
    }

    // DELETE 1 TOPIC
    public void deleteTopic(String clusterName, String topicName) {
        getClient(clusterName).deleteTopics(Collections.singleton(topicName));
    }

}
