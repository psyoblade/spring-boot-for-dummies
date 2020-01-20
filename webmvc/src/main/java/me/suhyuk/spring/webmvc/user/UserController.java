package me.suhyuk.spring.webmvc.user;

import org.springframework.web.bind.annotation.*;

@RestController // RestController 경우 ResponseBody 생략해도 됩니다 -> MessageConverter 사용이 됩니다.
public class UserController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/user")
    public @ResponseBody User create(@RequestBody User user) {
        return null;
    }

    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        return user;
    }

}
