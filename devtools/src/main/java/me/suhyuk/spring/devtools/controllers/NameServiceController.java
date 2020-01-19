package me.suhyuk.spring.devtools.controllers;

import me.suhyuk.spring.devtools.services.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NameServiceController {

    @Autowired
    private NameService nameService;

    @RequestMapping("/name")
    public String getName() {
        return nameService.getName();
    }
}
