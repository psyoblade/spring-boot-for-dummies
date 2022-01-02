package me.suhyuk.springspa.repository;

import me.suhyuk.springspa.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findAll();
    Optional<Member> findByName(String name);
}
