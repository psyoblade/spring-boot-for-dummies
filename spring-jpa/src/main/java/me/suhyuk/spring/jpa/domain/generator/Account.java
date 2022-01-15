package me.suhyuk.spring.jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@Getter
@Setter
@TableGenerator(
        name = "ACCOUNT_TAB_GENERATOR",
        table = "SEQ_TAB_ACCOUNT",
        pkColumnValue = "SEQ_ACCOUNT"
)
@SequenceGenerator(
        name = "ACCOUNT_SEQ_GENERATOR",
        sequenceName = "SEQ_ACCOUNT",
        initialValue = 1
)
public class Account implements Serializable { // Composite-id class must implement Serializable
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GENERATOR")
    private Long id;

    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "ACCOUNT_TAB_GENERATOR")
    private Long num;

    private String name;
}
