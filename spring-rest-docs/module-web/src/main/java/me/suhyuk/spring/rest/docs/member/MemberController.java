package me.suhyuk.spring.rest.docs.member;

import me.suhyuk.spring.rest.docs.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/")
    public Member get() {
        return new Member("psyoblade", "psyoblade@naver.com");
    }
}
