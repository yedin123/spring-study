package com.example.springtest.repository;

import com.example.springtest.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }
    @Test
    public void save() {
        //given
        Member member = new Member();
        member.setUsername("spring");
        member.setPassword("spring");
        member.setEnabled(true);
        //when
        repository.save(member);
        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }
    @Test
    public void findByUsername() {
        //given
        Member member1 = new Member();
        member1.setUsername("spring1");
        member1.setPassword("spring1");
        member1.setEnabled(true);
        repository.save(member1);
        Member member2 = new Member();
        member2.setUsername("spring2");
        member2.setPassword("spring2");
        member2.setEnabled(true);
        repository.save(member2);
        //when
        Member result = repository.findByUsername("spring1").get();
        //then
        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setUsername("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setUsername("spring2");
        repository.save(member2);
        //when
        List<Member> result = repository.findAll("");
        //then
        assertThat(result.size()).isEqualTo(2);
    }
}
