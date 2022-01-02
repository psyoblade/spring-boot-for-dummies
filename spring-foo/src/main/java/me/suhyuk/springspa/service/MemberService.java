package me.suhyuk.springspa.service;

import me.suhyuk.springspa.domain.Member;
import me.suhyuk.springspa.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    public static final String ALREADY_EXISTS_MEMBER = "이미 존재하는 고객 입니다";
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
