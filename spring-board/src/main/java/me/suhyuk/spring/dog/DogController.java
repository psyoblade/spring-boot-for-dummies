package me.suhyuk.spring.dog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dog")
public class DogController {

    private DogService service;

    @GetMapping
    public List<Dog> getDogs() {
        return service.getDogs();
    }

    @Autowired
    public void setService(DogService service) {
        System.out.println("DogController setter called");
        this.service = service;
    }

}
