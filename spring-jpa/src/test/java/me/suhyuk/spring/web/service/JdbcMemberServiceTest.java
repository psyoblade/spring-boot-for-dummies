package me.suhyuk.spring.web.service;

import me.suhyuk.spring.web.SpringWebApplicationTest;
import me.suhyuk.spring.web.domain.Member;
import me.suhyuk.spring.web.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles="test-web")
@ContextConfiguration(classes = SpringWebApplicationTest.class)
class JdbcMemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
//        memberService.clear();
    }

    @Test
    void testMemberServiceJdbc() {
        // When ... Then
        Member member = Member.builder().id(1L).name("박수혁").build();
        // Execute
        Long id = memberService.join(member);
        // Assert
        assertEquals(member.getId(), id);
    }
}