package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // DI (dependency Injection)
    // @Autowired <- 생성자 하나일 땐 생략 가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member) {

        // 같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member); // 중복 회원 검증

        /*
        Optional<Member> result = memberRepository.findById(member.getId());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
            // m의 값이 있으면 이미 존재하는 회원입니다 에러(IllegalStateException)를 내보내라.
            // .ifPresnet() => null이 아니라 값이 있으면 ()안의 동작 실행
            // optional이기 때문에 가능함. optional로 한번 감싸면, optional이라는 것 안에 객체가 있기 때문에 optional 안의 여러 메소드 사용 가능.
            // .orElseGet() => 값이 있으면 꺼내고, 없으면 ()안의 동작을 수행
        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
