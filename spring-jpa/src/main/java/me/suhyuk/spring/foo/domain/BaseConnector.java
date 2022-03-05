package me.suhyuk.spring.foo.domain;

import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
public class BaseConnector {
    private String createdBy;
    private LocalDateTime createdTime;
    public BaseConnector() {
    }
}
