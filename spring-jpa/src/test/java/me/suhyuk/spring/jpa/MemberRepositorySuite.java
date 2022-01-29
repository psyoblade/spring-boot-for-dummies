package me.suhyuk.spring.jpa;

import me.suhyuk.spring.jpa.domain.order.Member;
import me.suhyuk.spring.jpa.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MemberRepositorySuite {

    @Autowired protected MemberRepository memberRepository;

    @AfterEach
    void clearRepository() {
        memberRepository.clear();
    }

    public Member generateMember(Long memberId, String memberName) {
        return memberRepository.save(Member.builder().id(memberId).name(memberName).build());
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

}
