package me.suhyuk.spring.foo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Team.TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    public static final String TABLE_NAME = "TEAM";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 50)
    private String name;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<TeamTag> teamTag = new ArrayList<>();

    public List<TeamTag> getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(List<TeamTag> teamTag) {
        teamTag.forEach(tt -> tt.setTeam(this));
        this.teamTag = teamTag;
    }

    public List<User> getUsers() {
        return users;
    }

    public void hireUsers(List<User> users) {
        users.forEach(user -> user.joinTeam(this));
        this.users = users;
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

    @Builder
    public Team(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teamTag=" + teamTag +
                '}';
    }
}