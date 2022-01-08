package me.suhyuk.spring.web.repository;

import me.suhyuk.spring.web.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository {
    Member save(Member member);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByName(String memberName);
    List<Member> findAll();
}
