package me.suhyuk.springjpa.strategy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(
        name = "SEQUENCE_GENERATOR",
        sequenceName = "SEQUENCE",
        initialValue = 1,  // 초기 값
        allocationSize = 50 // 미리 50개를 선점해 놓고, 여러번 오가는 네트워크 횟수를 줄이기 워한 방법
)
public class SequenceType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GENERATOR")
    private Long id;

    private String name;

    public SequenceType(String name) {
        this.name = name;
    }
}
