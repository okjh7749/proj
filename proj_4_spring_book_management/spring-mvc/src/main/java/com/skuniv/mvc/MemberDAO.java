package com.skuniv.mvc;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import org.springframework.jdbc.core.*;

public class MemberDAO {
	private JdbcTemplate jdbcTemplate;

	public MemberDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select * from Member", (ResultSet rs, int rowNum) -> {
			Member member = new Member(rs.getString("ID"), rs.getString("Password"));
			member.setMnum(rs.getLong("Mnum"));
			return member;
		});
		return results;
	}

	public Member selectByID(String ID) {
		List<Member> results = jdbcTemplate.query("select * from Member where ID = ?", new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member(rs.getString("ID"), rs.getString("Password"));
				member.setMnum(rs.getLong("Mnum"));
				return member;
			}
		}, ID);
		return results.isEmpty() ? null : results.get(0);
	}

	public void insert(Member member) {
		jdbcTemplate.update("insert into Member (ID, Password) " + " values (?, ?) ", member.getId(),
				member.getPassword());
	}

	public void update(Member member) {
		jdbcTemplate.update("update Member set ID = ?, Password = ? " + " where ID = ? ", member.getId(),
				member.getPassword(), member.getId());
	}

}
