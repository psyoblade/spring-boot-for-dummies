package me.suhyuk.spring.web.service;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.web.domain.Member;
import me.suhyuk.spring.web.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MemberService {

    public static final String ALREADY_EXISTS_MEMBER = "이미 존재하는 고객 입니다";
    private MemberRepository memberRepository;

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException(ALREADY_EXISTS_MEMBER);
        });
    }


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
