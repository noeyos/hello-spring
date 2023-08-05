package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService service;
    MemoryMemberRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
        service = new MemberService(repository);
    }

    @AfterEach
    void clear() {
        repository.clearStore();
    }

    // 테스트에서는 메소드명을 한국어로 해도 됨
    @Test
    void 회원가입() {
        // given | 무언가 주어졌을 때 (이 데이터를 기반으로)
        Member member = new Member();
        member.setName("hello");

        // when | 이것을 실행했을 때 (이것을 검증)
        Long saveId = service.join(member);

        // then | 결과가 이게 나와야 된다 (검증 부분)
        Member findMember = service.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member m1 = new Member();
        m1.setName("spring");

        Member m2 = new Member();
        m2.setName("spring");

        // when
        service.join(m1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(m2));
        // IllegalStateException이 발생하는지 확인하려고 함
        // service.join(m2) 메서드를 실행하는데, 이 부분에서
        // IllegalStateException 예외가 발생할 것으로 예상된다.
        // 만약 service.join(m2) 실행 중에 IllegalStateException 예외가 발생하지 않는다면 테스트 실패
        // 그렇지 않다면 assertThrowssms 발생한 예외 객체를 e 변수에 할당하게 된다.
        // assertThrows : 특정 예외가 발생하는지를 확인하고, 발생한 예외 객체를 반환해주는 역할을 합니다.
/*
        try {
            service.join(m2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}