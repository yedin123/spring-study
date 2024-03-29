package com.example.springtest.repository;

import com.example.springtest.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByUsername(String username);
    List<Member> findAll(String username);

}