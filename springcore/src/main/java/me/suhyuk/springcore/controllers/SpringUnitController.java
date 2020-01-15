package me.suhyuk.springcore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringUnitController {

    @Autowired
    private SpringUnit springUnit;

    @GetMapping("/hello")
    public String hello() {
        return springUnit.getName();
    }
}
