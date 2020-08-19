package web;

import com.auth.project.domian.user.Member;
import com.auth.project.domian.user.MemberRepository;
import com.auth.project.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberService memberService;

    @Test
    public void join_test() throws Exception{

        memberRepository.save(Member.builder()
                .email("dsdjd@gmail.com")
                .password(passwordEncoder.encode("dlsdksl"))
                .name("dskds")
                .fnl_date(LocalDateTime.now())
                .build());

        Optional<Member> member = memberService.findByEmail("dsdjd@gmail.com");

        System.out.println(member.get());

    }
}
