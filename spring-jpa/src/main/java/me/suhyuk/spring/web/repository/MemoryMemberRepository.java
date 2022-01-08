package me.suhyuk.spring.web.repository;

import me.suhyuk.spring.web.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        Member newMember = Member.builder().id(++sequence).name(member.getName()).build();
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

    public void clear() {
        sequence = 0L;
        store.clear();
    }
}
