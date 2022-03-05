package me.suhyuk.spring.foo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = User.TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    public static final String TABLE_NAME = "USER";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 50)
    private String name;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "USER_DETAIL_ID")
    private UserDetail userDetail;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<KafkaBaseConnector> kafkaBaseConnector = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void joinTeam(Team team) {
        this.team = team;
    }

    public List<KafkaBaseConnector> getKafkaBaseConnector() {
        return kafkaBaseConnector;
    }

    public void createKafkaConnector(KafkaBaseConnector kafkaBaseConnector) {
        kafkaBaseConnector.createdBy(this);
        this.kafkaBaseConnector.add(kafkaBaseConnector);
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void addUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Builder
    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", team=" + team +
                '}';
    }
}