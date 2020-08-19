package com.auth.project.service;

import com.auth.project.domian.user.Member;
import com.auth.project.domian.user.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Optional<Member> findByEmail (String userEmail) {
        return memberRepository.findByEmail(userEmail);
    }

    public Member update(Member member) {

        memberRepository.save(Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .fnl_date(LocalDateTime.now())
                .build());
        return member;

    }
}
