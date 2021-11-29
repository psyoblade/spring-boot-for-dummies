package me.suhyuk.spring.rest.docs.service;

import me.suhyuk.spring.rest.docs.domain.Member;
import me.suhyuk.spring.rest.docs.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceCustom {

    private MemberRepository memberRepository;

    public MemberServiceCustom(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signup(Member member) {
        return memberRepository.save(member).getId();
    }
}
