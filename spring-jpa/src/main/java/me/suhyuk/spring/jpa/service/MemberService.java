package me.suhyuk.spring.jpa.service;

import lombok.AllArgsConstructor;
import me.suhyuk.spring.jpa.domain.order.Member;
import me.suhyuk.spring.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    public static final String ALREADY_EXISTS_MEMBER = "이미 존재하는 고객 입니다";
    private MemberRepository memberRepository; //  = new MemoryMemberRepository();

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getId()).ifPresent(m -> {
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
