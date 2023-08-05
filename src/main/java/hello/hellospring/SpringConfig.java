package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    ((( 직접 스프링빈을 등록하는 방법 )))

    스프링이 실행될 때 configuration을 읽어오고
    그 안(@Bean)에 있는 것을 스프링빈에 등록한다

    스프링이 실행될 때 멤버서비스와 멤버리포지토리를 둘 다 스프링빈에 등록을 하고
    그러면서 스프링빈에 등록되어 있는 멤버리포지토리를 멤버서비스에 넣어줌.
    멤버서비스와 리포지토리를 스프링이 올라올 때 얘들을 스프링빈 컨테이너에 올림.
    그러고 컨트롤러는 스프링이 관리 하는 것이기 때문에 컴포넌트 스캔에 올라감.
*/

@Configuration
public class SpringConfig {

    @Bean //스프링빈을 등록하겠다는 의미
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        // 깨알상식 : 인터페이스는 new가 안 된다
    }
}
