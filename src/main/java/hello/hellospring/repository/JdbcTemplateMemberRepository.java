package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        // ㄴ 위에 4줄로 insert문 만들어줌

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        // executeAndReturnKey에서 key값을 받고, 그걸 가지고 member에서 setId 해서 넣어줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> list = jdbcTemplate.query(
                "select * from member where id = ?", memberRowMapper(), id);
        // 결과가 나오는 걸 rowMapping 이라는 거로 mapping 해줘야 함
        // 앞에 있는 sql을 실행한 결과를 뒤에다 넣음
        // 맨 마지막 자리는 파라미터 자리 (?에 들어갈 값 자리)
        return list.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> list = jdbcTemplate.query(
                "select * from member where name = ?", memberRowMapper(), name);
        return list.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        // ResultSet 결과를 여기서 멤버 객체로 Mapping을 해서 돌려줌
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }

}
