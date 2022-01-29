package me.suhyuk.spring.jpa.domain.team;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Player {
    @Id @GeneratedValue
    @Column(name = "PLAYER_ID", nullable = false)
    private Long id;
    @JoinColumn(name = "TEAM_ID")
    @ManyToOne
    private Team team;
    @Column(name = "PLAYER_NAME")
    private String name;
    @Builder
    public Player(String name) {
        this.name = name;
    }
    public void changeTeam(Team team) {
        this.team = team;
        this.team.getPlayers().add(this);
    }
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
