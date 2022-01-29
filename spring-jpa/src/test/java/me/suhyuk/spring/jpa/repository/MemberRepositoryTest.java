package me.suhyuk.spring.jpa.repository;

import me.suhyuk.spring.jpa.domain.order.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberRepositoryTest {

    MemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    void clearRepository() {
        memberRepository.clear();
    }

    Member generateMember(Long memberId, String memberName) {
        return Member.builder().id(memberId).name(memberName).build();
    }

    /**
     * 이름만으로 멤버 생성을 하는 map 결과를 통해 외부에 영향을 받는 Repository 결과를 members 에 저장하여 반환합니다
     * @param names
     * @return
     */
    public List<Member> generateMembers(List<String> names) {
        List<Member> members = new ArrayList<>();
        names.stream().map(name -> Member.builder().name(name).build()).forEach(member -> members.add(memberRepository.save(member)));
        return members;
    }

    @Test
    @DisplayName("멤버 생성 및 저장")
    void save() {
        // When ... Then
        Member member = generateMember(1L, "박수혁");
        // Execute
        Member savedMember = memberRepository.save(member);
        // Assert
        assertAll(
            () -> assertThat(savedMember).isEqualTo(member),
            () -> assertEquals(member, savedMember)
        );
    }

    /**
     * stream().map() 함수 예제 : 컬렉션에 대해서 특정 함수를 통과시킨 결과를 반환하고 싶은 경우 말 그대로 map pipeline
     * input: List<Integer> -> List<String>
     */
    @DisplayName("stream().map() 함수 사용 예제")
    @Test
    void convertIntToStringList() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        intList.stream().forEach(i -> assertEquals(Integer.class, i.getClass()));
        List<String> strList = intList.stream().map(i -> i.toString()).collect(Collectors.toList());
        strList.stream().forEach(s -> assertThat(s.getClass()).isEqualTo(String.class));
    }

    @Test
    @DisplayName("ID를 통해 고객정보를 찾는 예제")
    void findById() {
        // When ... Then
        List<Member> members = generateMembers(Arrays.asList("박수혁", "박송희", "백건호"));
        // Execute
        Member foundMember = memberRepository.findById(3L).get();
        // Assert
        assertEquals(members.get(2), foundMember);
    }

    @Test
    @DisplayName("이름을 통해 고객정보를 찾는 예제")
    void findByName() {
        // When ... Then
        List<Member> members = generateMembers(Arrays.asList("박수혁", "박송희", "백건호"));
        // Execute
        Member foundMember = memberRepository.findByName("박송희").get();
        // Assert
        assertEquals(members.get(1), foundMember);
    }

    @Test
    @DisplayName("모든 고객정보를 찾는 예제")
    void findAll() {
        // When ... Then
        generateMembers(Arrays.asList("박수혁", "박송희", "백건호"));
        // Execute
        List<Member> foundMembers = memberRepository.findAll();
        // Assert
        assertEquals(3, foundMembers.size());
    }
}