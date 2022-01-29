package me.suhyuk.spring.jpa.domain.team;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID", nullable = false)
    private Long id;
    @Column(name = "TEAM_NAME")
    private String name;
    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();
    @Builder
    public Team(String name) {
        this.name = name;
    }
}
