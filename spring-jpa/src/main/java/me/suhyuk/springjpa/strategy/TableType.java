package me.suhyuk.springjpa.strategy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@TableGenerator( // 테이블 제너레이터를 사용하는 경우에는 반드시 @Id 명시되어 있어야 테이블 생성이 됩니다
        name = "TABLE_GENERATOR",
        table = "SEQ_TABLE",
        pkColumnValue = "COL_GEN_TAB"
)
public class TableType {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
    private Long id;

    private String name;

    public TableType(String name) {
        this.name = name;
    }
}
