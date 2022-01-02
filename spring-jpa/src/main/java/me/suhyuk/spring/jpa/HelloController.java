package me.suhyuk.spring.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Suhyuk Park");
        model.addAttribute("email", "psyoblade@naver.com");
        return "hello";
    }

    @GetMapping("foo")
    public String foo(Model model) {
        return "foo/bar";
    }
}