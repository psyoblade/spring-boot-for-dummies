package me.suhyuk.spring.foo.domain;

import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = KafkaBaseConnector.TABLE_NAME)
@SuperBuilder
public class KafkaBaseConnector extends BaseConnector {
    public static final String TABLE_NAME = "KAFKA_BASE_CONNECTOR";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "BROKERS", joinColumns = @JoinColumn(name = "ID"))
    private List<String> brokers;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<String> brokers) {
        this.brokers = brokers;
    }

    public KafkaBaseConnector() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void createdBy(User user) {
        this.user = user;
    }

}