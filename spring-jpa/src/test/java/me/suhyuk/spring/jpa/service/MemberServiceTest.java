package me.suhyuk.spring.jpa.service;

import me.suhyuk.spring.jpa.MemberRepositorySuite;
import me.suhyuk.spring.jpa.SpringJpaApplicationTest;
import me.suhyuk.spring.jpa.domain.order.Member;
import me.suhyuk.spring.jpa.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles(profiles="test-jpa")
@ContextConfiguration(classes = SpringJpaApplicationTest.class)
class MemberServiceTest extends MemberRepositorySuite {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void join() {
        // When ... Then
        Member member = Member.builder().id(1L).name("박수혁").build();
        // Execute
        Long memberId = memberService.join(member);
        // Assert
        Member foundMember = memberService.findOne(memberId).get();
        assertEquals(member, foundMember);
    }

    @Test
    @DisplayName("회원찾기-1")
    void findMembers() {
        // When ... Then
        generateMembers(Arrays.asList("박수혁", "박송희", "백건호"));
        // Execute
        List<Member> foundMembers = memberService.findMembers();
        // Assert
        assertEquals(3, foundMembers.size());
    }

    @Test
    @DisplayName("회원찾기-2")
    void findOne() {
        // When ... Then
        Member member = generateMember(1L, "박수혁");
        // Execute
        Member foundMember = memberService.findOne(member.getId()).get();
        // Assert
        assertEquals(member, foundMember);
    }

    @Test
    @DisplayName("중복가입 확인")
    void duplicateMember() {
        // When ... Then
        Member member = generateMember(1L, "박수혁");
        // Execute, Assert
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(MemberService.ALREADY_EXISTS_MEMBER);
    }
}