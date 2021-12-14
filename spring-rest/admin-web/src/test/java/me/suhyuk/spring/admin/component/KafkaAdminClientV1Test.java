package me.suhyuk.spring.admin.component;

import me.suhyuk.spring.admin.configuration.KafkaConfiguration;
import me.suhyuk.spring.admin.domain.KafkaTopicInfo;
import me.suhyuk.spring.admin.service.KafkaAdminService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaAddress;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles(value = "local")
@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
class KafkaAdminClientV1Test {

    private static final String CLUSTER_NAME = "kafka-test";
    private static final String TOPIC_NAME = "events";

    @Autowired KafkaConfiguration.AdminClients adminClients;
    @Autowired IKafkaAdminClient kafkaAdminClientV1;
    @Autowired KafkaAdminService kafkaAdminService;

    private AdminClient getAdminClient(String bootstrapServers) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient client = KafkaAdminClient.create(props);
        return client;
    }

    @Test
    @DisplayName("목업 카프카 어드민 서비스")
    void mockUpAdminService(@EmbeddedKafkaAddress String bootstrapServers) throws ExecutionException, InterruptedException {

        AdminClient adminClient = getAdminClient(bootstrapServers);
        adminClient.createTopics(Collections.singleton(new NewTopic(TOPIC_NAME, 1, (short) 1)));
        Mockito.lenient()
                .when(adminClients.of(any(String.class)))
                .thenReturn(adminClient);

        List<KafkaTopicInfo> list = kafkaAdminService.listTopics(CLUSTER_NAME);
        for (KafkaTopicInfo topic : list) {
            String topicName = String.format("%s", topic.getTopicName());
            System.out.println("#########################################");
            System.out.println("topicName = " + topicName);
            System.out.println("#########################################");
        }
    }

    @Test
    @DisplayName("목업 카프카 클라이언트")
    void mockUpAdminClients(@EmbeddedKafkaAddress String bootstrapServers) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(bootstrapServers);
        adminClient.createTopics(Collections.singleton(new NewTopic(TOPIC_NAME, 1, (short) 1)));
        Mockito.lenient()
                .when(adminClients.of(any(String.class)))
                .thenReturn(adminClient);
        List<KafkaTopicInfo> list = kafkaAdminClientV1.listTopics(CLUSTER_NAME);
        for (KafkaTopicInfo topic : list) {
            String topicName = String.format("%s", topic.getTopicName());
            System.out.println("#########################################");
            System.out.println("topicName = " + topicName);
            System.out.println("#########################################");
        }
    }

}