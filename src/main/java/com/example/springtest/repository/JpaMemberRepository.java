package com.example.springtest.repository;

import com.example.springtest.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public List<Member> findAll(String username) {

        if(username.isEmpty()){
            return em.createQuery("select m from Member m", Member.class)
                    .getResultList();
        } else{
            return em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", username)
                    .getResultList();
        }
    }
    public Optional<Member> findByUsername(String username) {
        List<Member> result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
        return result.stream().findAny();
    }
}
