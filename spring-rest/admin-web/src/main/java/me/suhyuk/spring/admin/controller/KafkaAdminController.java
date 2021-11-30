package me.suhyuk.spring.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/kafka/clusters")
public class KafkaAdminController {

    private KafkaConfiguration kafkaConfiguration;
    private KafkaAdminService kafkaAdminService;

    @GetMapping("/{clusterName}/topics")
    @ResponseBody
    public ResponseEntity<String> listTopics(@PathVariable String clusterName) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(kafkaAdminService.listTopics(clusterName).toString());
    }

    @GetMapping("/{clusterName}/topics/{topicName}")
    @ResponseBody
    public ResponseEntity<String> getTopicInfo(@PathVariable String clusterName, @PathVariable String topicName) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(kafkaAdminService.getTopicInfo(clusterName, topicName).toString());
    }

    @GetMapping("/{clusterName}/topics/{topicName}/configs")
    @ResponseBody
    public ResponseEntity<String> getTopicConfigs(@PathVariable String clusterName, @PathVariable String topicName) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(kafkaAdminService.getTopicConfigs(clusterName, topicName).toString());
    }

    @PostMapping("/{clusterName}/topics")
    public void createTopic(@PathVariable String clusterName,
                            @RequestBody CreateTopicRequest req) throws ExecutionException, InterruptedException {
        kafkaAdminService.createTopic(clusterName, req.getTopicName(), req.getNumPartitions(), req.getReplicationFactor());
    }

}
