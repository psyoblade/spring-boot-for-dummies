package me.suhyuk.spring.admin.component;

import me.suhyuk.spring.admin.KafkaTestSuite;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaAddress;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@ActiveProfiles(value = "local")
@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
class KafkaAdminClientV1Test extends KafkaTestSuite {

    @Test
    @DisplayName("목업 카프카 어드민 서비스")
    void mockUpAdminService() {
        List<KafkaTopicInfo> list = kafkaAdminService.listTopics(CLUSTER_NAME);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(TOPIC_NAME, list.get(0).getTopicName());
    }

    @Test
    @DisplayName("목업 카프카 클라이언트")
    void mockUpAdminClients() {
        List<KafkaTopicInfo> list = kafkaAdminClientV1.listTopics(CLUSTER_NAME);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(TOPIC_NAME, list.get(0).getTopicName());
    }

}