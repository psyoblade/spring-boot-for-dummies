package me.suhyuk.spring.admin.repository;

import me.suhyuk.spring.admin.common.Pair;
import me.suhyuk.spring.admin.domain.KafkaTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKafkaRepository<T extends KafkaTopic> extends CrudRepository<KafkaTopic, Pair<String, String>> {

    // CreateTopic - Topic (clusterId, topicName)

    // ReadTopic - TopicConfigs

    // UpdateTopic - TopicConfigs

    // DeleteTopic - Topic

}
