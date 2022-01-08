package me.suhyuk.spring.jpa.repository;

import me.suhyuk.spring.jpa.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        Member newMember = member.deepCopy(++sequence, member);
        store.put(newMember.getId(), newMember);
        return newMember;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(store.get(memberId));
    }

    @Override
    public Optional<Member> findByName(String memberName) {
        return store.values().stream()
                .filter(member -> member.getName().equals(memberName))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        sequence = 0L;
        store.clear();
    }
}
