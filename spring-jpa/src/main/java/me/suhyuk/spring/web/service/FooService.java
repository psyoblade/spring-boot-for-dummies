package me.suhyuk.spring.web.service;

import me.suhyuk.spring.web.repository.BarRepository;

public class FooService {

    private BarRepository barRepository;

    public FooService(BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    public String bar() {
        return barRepository.call();
    }
}
