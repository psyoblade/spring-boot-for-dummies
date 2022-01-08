package me.suhyuk.spring.web.repository;

import me.suhyuk.spring.web.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        List<Member> query = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), memberId);
        return query.stream().findAny();
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public Optional<Member> findByName(String memberName) {
        List<Member> query = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), memberName);
        return query.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    public void clear() {
        jdbcTemplate.execute("delete from member");
    }
}
