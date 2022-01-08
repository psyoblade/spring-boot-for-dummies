package me.suhyuk.spring.web.repository;

import me.suhyuk.spring.web.domain.Member;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(em.find(Member.class, memberId));
    }

    @Override
    public Optional<Member> findByName(String memberName) {
        return em.createQuery("select m from Member as m where m.name = :name", Member.class)
                .setParameter("name", memberName).getResultList().stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public void clear() {
    }
}
