package me.suhyuk.spring.admin.component;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;

@SpringBootTest
@ActiveProfiles(value = "local")
@EmbeddedKafka
public class ClientTest {

}
