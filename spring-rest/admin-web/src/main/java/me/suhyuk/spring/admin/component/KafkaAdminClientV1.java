package me.suhyuk.spring.admin.component;

import me.suhyuk.spring.admin.configuration.KafkaConfiguration;
import me.suhyuk.spring.admin.configuration.KafkaConfiguration.AdminClients;
import me.suhyuk.spring.admin.domain.KafkaTopicConfigs;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;
import me.suhyuk.spring.admin.dto.CreateKafkaTopicRequest;
import me.suhyuk.spring.admin.dto.UpdateKafkaTopicRequest;
import me.suhyuk.spring.admin.exception.InternalServerException;
import me.suhyuk.spring.admin.utils.ExceptionUtils;
import org.apache.kafka.clients.admin.NewPartitionReassignment;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class KafkaAdminClientV1 implements IKafkaAdminClient {

    @Override
    public List<KafkaTopicInfo> listTopics(String clusterName) {
        try {
//            return AdminClients.of(clusterName).listTopics().listings().get()
//                    .stream().map(listing -> KafkaTopicInfo.builder().topicName(listing.name()).build())
//                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    // 값을 액세스 할 필요가 없다면 collect(Collectors.toList()), 필요가 있다면 map(value -> apply(value)) 이후에 collect
    @Override
    public List<KafkaTopicInfo> listTopics(String clusterName) {
        try {
            adminClients.of(clusterName)
            return AdminClients.of(clusterName).listTopics().listings().get()
                    .stream().map(listing -> KafkaTopicInfo.builder().topicName(listing.name()).build())
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new InternalServerException(ExceptionUtils.printStackTrace(e));
        } catch (ExecutionException e) {
            throw new IllegalArgumentException(ExceptionUtils.printStackTrace(e));
        }
    }

    @Override
    public void createTopic(String clusterName, CreateKafkaTopicRequest req) {
        assert(clusterName.equals(req.getClusterName()));
        NewTopic newTopic = new NewTopic(req.getTopicName(), req.getNumPartitions(), req.getReplicationFactor());
        AdminClients.of(clusterName)
                .createTopics(Collections.singleton(newTopic));
    }

    @Override
    public KafkaTopicInfo readTopicInfo(String clusterName, String topicName) {
        try {
            TopicDescription topicDescription = AdminClients.of(clusterName)
                    .describeTopics(Collections.singleton(topicName)).all().get()
                    .get(topicName);
            return KafkaTopicInfo.builder()
                    .clusterName(clusterName)
                    .topicName(topicDescription.name())
                    .isInternal(topicDescription.isInternal())
                    .build();
        } catch (InterruptedException e) {
            throw new InternalServerException(ExceptionUtils.printStackTrace(e));
        } catch (ExecutionException e) {
            throw new IllegalArgumentException(ExceptionUtils.printStackTrace(e));
        }
    }

    // 파티션 구성을 변경
    // 1. 주어진 클러스터, 토픽의 현재 구성 정보를 조회하고,
    // 2. 조회된 정보에서 복제본 평준화를 위한 알고리즘을 수행하고,
    // 3. 수행된 결과를 다시 재할당하는 명령을 수행하고
    // 4. 수행된 결과를 반환합니다
    @Override
    public void updateTopicPartitions(String clusterName, String topicName, UpdateKafkaTopicRequest req) {
        Map<TopicPartition, Optional<NewPartitionReassignment>> assignments = new HashMap<>();
        for (UpdateKafkaTopicRequest.KafkaReplicaReassignment reassignment : req.getReassignments()) {
            TopicPartition key = new TopicPartition(reassignment.getTopicName(), reassignment.getPartitionNumber());
            NewPartitionReassignment value = new NewPartitionReassignment(reassignment.getBrokerIDs());
            assignments.put(key, Optional.of(value));
        }
        AdminClients.of(clusterName).alterPartitionReassignments(null);
    }

    @Override
    public void deleteTopic(String clusterName, String topicName) {
    }

    @Override
    public KafkaTopicConfigs readTopicConfigs(String clusterName, String topicName) {
        return null;
    }
    */
}
