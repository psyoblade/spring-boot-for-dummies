package me.suhyuk.spring.admin;

import me.suhyuk.spring.admin.component.IKafkaAdminClient;
import me.suhyuk.spring.admin.configuration.KafkaConfiguration;
import me.suhyuk.spring.admin.service.KafkaAdminService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaAddress;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;

@EmbeddedKafka
public class KafkaTestSuite {

    public static final String CLUSTER_NAME = "kafka-test";
    public static final String TOPIC_NAME = "events";

    @Autowired public KafkaConfiguration.AdminClients adminClients;
    @Autowired public IKafkaAdminClient kafkaAdminClientV1;
    @Autowired public KafkaAdminService kafkaAdminService;

    @BeforeAll
    static void setUpAll() {
        System.out.println("######## START #########");
    }

    @BeforeEach
    void setUp(@EmbeddedKafkaAddress String bootstrapServers) throws ExecutionException, InterruptedException {
        AdminClient adminClient = getAdminClient(bootstrapServers);
        adminClient.createTopics(Collections.singleton(new NewTopic(TOPIC_NAME, 1, (short) 1)));
        Mockito.lenient()
                .when(adminClients.of(any(String.class)))
                .thenReturn(adminClient);
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("######## END #########");
    }

    public AdminClient getAdminClient(String bootstrapServers) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient client = KafkaAdminClient.create(props);
        return client;
    }

}
