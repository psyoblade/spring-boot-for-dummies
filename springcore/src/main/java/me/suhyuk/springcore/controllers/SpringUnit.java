package me.suhyuk.springcore.controllers;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SpringUnit implements Serializable {
    public String getName() {
        return "suhyuk";
    }
}
