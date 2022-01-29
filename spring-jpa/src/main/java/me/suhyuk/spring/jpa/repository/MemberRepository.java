package me.suhyuk.spring.jpa.repository;

import me.suhyuk.spring.jpa.domain.order.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByName(String memberName);
    List<Member> findAll();
    void clear();
}
