package me.suhyuk.spring.admin.component;

import org.junit.jupiter.api.Test;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaAddress;

import java.util.concurrent.ExecutionException;

@EmbeddedKafka
public class EmbeddedKafkaTest {

    @Test
    public void firstTest(@EmbeddedKafkaAddress String bootstrapServers) {
        System.out.println("bootstrapServers = " + bootstrapServers);
    }

    @Test
    // Here we use the annotation again, overriding the broker properties
    @EmbeddedKafka(brokerProperties = "auto.create.topics.enable=false")
    public void anotherTest() {
    }
}

