package me.suhyuk.spring.admin.service;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.admin.component.IKafkaAdminClient;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KafkaAdminService {

    private IKafkaAdminClient kafkaAdminClientV1;

    public List<KafkaTopicInfo> listTopics(String clusterName) {
        return kafkaAdminClientV1.listTopics(clusterName);
    }

}
