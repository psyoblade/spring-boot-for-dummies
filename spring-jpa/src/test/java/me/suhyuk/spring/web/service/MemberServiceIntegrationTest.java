package me.suhyuk.spring.web.service;

import me.suhyuk.spring.web.HelloWorldApplication;
import me.suhyuk.spring.web.domain.Member;
import me.suhyuk.spring.web.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles(profiles = "test-web")
@Transactional
@ContextConfiguration(classes = HelloWorldApplication.class)
public class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = Member.builder().name("hello").build();
        System.out.println(member);
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        Assert.assertEquals(member.getName(), findMember.getName());
        System.out.println(findMember);
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = Member.builder().name("spring").build();
        Member member2 = Member.builder().name("spring").build();
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo(MemberService.ALREADY_EXISTS_MEMBER);
    }
}
