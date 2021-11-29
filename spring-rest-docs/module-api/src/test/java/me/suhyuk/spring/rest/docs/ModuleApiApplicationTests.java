package me.suhyuk.spring.rest.docs;

import me.suhyuk.spring.rest.docs.domain.Member;
import me.suhyuk.spring.rest.docs.service.MemberServiceCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleApiApplicationTests {

    @Autowired
    private MemberServiceCustom memberServiceCustom;

    @Test
    public void save() {
        Member member = new Member("psyoblade", "psyoblade@naver.com");
        Long id = memberServiceCustom.signup(member);
        assertThat(id, is(1L));
    }
}
