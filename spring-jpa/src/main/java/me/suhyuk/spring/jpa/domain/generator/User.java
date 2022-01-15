package me.suhyuk.spring.jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "SEQ_USER",
        initialValue = 1
)
@TableGenerator( // create table seq_tab_user (sequence_name varchar(255) not null, next_val bigint, primary key (sequence_name))
        name = "USER_TAB_GENERATOR",
        table = "SEQ_TAB_USER",
        pkColumnValue = "SEQ_USER",
        initialValue = 1
)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @Column(name = "id")
    private long id;

    @GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_TAB_GENERATOR")
    @Column(name = "seq")
    private long seq;

}
