package me.suhyuk.spring.admin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FooService {
    public String bar() {
        return "foo & bar";
    }
}
