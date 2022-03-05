package me.suhyuk.spring.foo.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table()
@SuperBuilder
public class KafkaConnector extends Connector {
    @Column(name = "BROKER", length = 50)
    private String broker;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public KafkaConnector() {
    }
}