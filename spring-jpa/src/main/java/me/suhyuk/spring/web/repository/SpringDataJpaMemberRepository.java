package me.suhyuk.spring.web.repository;

import me.suhyuk.spring.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // 즉, 인터페이스 이름만으로도 쿼리를 동적으로 만들어내는 것이 가능합니다
    // select m from Member m where m.name = ?
    Optional<Member> findByName(String memberName);
}