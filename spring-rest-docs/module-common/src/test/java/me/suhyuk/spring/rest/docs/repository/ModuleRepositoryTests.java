package me.suhyuk.spring.rest.docs.repository;

import me.suhyuk.spring.rest.docs.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ModuleRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void add() {
        memberRepository.save(new Member("psyoblade", "psyoblade@naver.com"));
        Member saved = memberRepository.getOne(1L);
        assertThat(saved.getName(), is("psyoblade"));
    }

}
