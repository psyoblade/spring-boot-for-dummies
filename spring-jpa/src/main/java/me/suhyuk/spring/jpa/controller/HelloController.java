package me.suhyuk.spring.jpa.controller;

import me.suhyuk.spring.jpa.domain.foo.Bar;
import me.suhyuk.spring.jpa.domain.foo.FooBar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private FooBar fooBar;
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

    @GetMapping("foobar")
    public String fooBar() {
        return "fooBar";
    }
}
