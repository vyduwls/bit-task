package com.auth.project.web.user;


import com.auth.project.config.security.JwtTokenProvider;
import com.auth.project.domian.user.Member;
import com.auth.project.domian.user.MemberRepository;

import com.auth.project.service.MemberService;
import com.auth.project.web.user.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/v1/member/join")
    public String join(@RequestBody  @Valid MemberRequestDto memberRequestDto, BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException(result.getAllErrors().toString());
        }

        Optional<Member> member = memberService.findByEmail(memberRequestDto.getEmail());
        if(member.isPresent() ){
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        return memberRepository.save(Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .name(memberRequestDto.getName())
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getEmail();
    }

    // 로그인
    @PostMapping("/v1/member/login")
    public String login(@RequestBody Map<String, String> user) {
        Member member = memberRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 EMAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        memberService.update(member);

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    // 정보조회
    @PostMapping("/v1/member/info")
    public String info(@RequestBody Map<String, String> user, String token) {
        //Optional<Member> member = = memberService.findByEmail(memberRequestDto.getEmail());

        return "";
    }
}