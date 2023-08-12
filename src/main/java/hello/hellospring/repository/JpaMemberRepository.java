package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    // JPA는 EntityManger라는 것으로 모든 것이 동작함
    // build.gradle에 org...jpa 입력하면 스프링부트가 자동으로
    // 현재 데이터 베이스랑 다 연결해서 EntityManger라는 걸 생성해줌
    // 우리는 이 만들어진 걸 injection 받으면 됨
    // properties에 세팅한 정보들이랑 DB Connection정보랑 다 자동으로 짬뽕을 해서
    // 스프링부트가 엔티티 매니저를 만들어줌
    // 얘는 내부적으로 데이터 소스를 다 들고 있어서 DB랑 통신하고 이런 걸 이 내부에서 다 처리함

    // JPA에 EntityManager 주입
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    // 결론 : JPA를 사용하려면 EntityManager를 주입 받아야 한다

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
        // persist : 영속하다, 영구 저장하다
        // 엔티티 메니저의 persist를 사용하면 jpa가 insert 쿼리 다 만들어서 db에 집어넣고
        // id까지 이 member에 setId까지 다 해줌
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        // find : 조회할 때 사용하는 메서드
        // .find(조회할 타입, pk값)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> list = em.createQuery(
                "select m from Member m where m.name = :name",
                        Member.class)
                .setParameter("name", name)
                .getResultList();
        return list.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> list = em.createQuery("select m from Member m",
                Member.class).getResultList();
        return list;
    }
}
