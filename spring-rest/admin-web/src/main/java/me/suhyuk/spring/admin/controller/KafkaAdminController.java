package me.suhyuk.spring.admin.controller;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.admin.component.IKafkaAdminClient;
import me.suhyuk.spring.admin.domain.KafkaTopicConfigs;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;
import me.suhyuk.spring.admin.dto.CreateKafkaTopicRequest;
import me.suhyuk.spring.admin.dto.UpdateKafkaTopicRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/kafka/clusters")
public class KafkaAdminController {

    private final IKafkaAdminClient kafkaAdminClient;


    @GetMapping("/{clusterName}/topics")
    @ResponseBody
    public List<KafkaTopicInfo> listTopics(@PathVariable String clusterName) {
        return kafkaAdminClient.listTopics(clusterName);
    }

    @PostMapping("/{clusterName}/topics")
    public void createTopic(@PathVariable String clusterName, @RequestBody CreateKafkaTopicRequest req) {
        kafkaAdminClient.createTopic(clusterName, req);
    }

    @GetMapping("/{clusterName}/topics/{topicName}")
    @ResponseBody
    public KafkaTopicInfo readTopicInfo(@PathVariable String clusterName, @PathVariable String topicName) {
        return kafkaAdminClient.readTopicInfo(clusterName, topicName);
    }

    // 특정 토픽의 파티션을 주어진 조건에 맞도록 평준화 하도록 구성하는 것
    @PutMapping("/{clusterName}/topics/{topicName}/partitions")
    public void updateTopicPartition(@PathVariable String clusterName, @PathVariable String topicName,
                                     @RequestBody UpdateKafkaTopicRequest req) {
        kafkaAdminClient.updateTopicPartitions(clusterName, topicName, req);
    }

    @DeleteMapping("/{clusterName}/topics/{topicName}")
    public void deleteTopic(@PathVariable String clusterName, @PathVariable String topicName) {
        kafkaAdminClient.deleteTopic(clusterName, topicName);
    }

    @GetMapping("/{clusterName}/topics/{topicName}/configs")
    @ResponseBody
    public KafkaTopicConfigs readTopicConfigs(@PathVariable String clusterName, @PathVariable String topicName) {
        return kafkaAdminClient.readTopicConfigs(clusterName, topicName);
    }

}
