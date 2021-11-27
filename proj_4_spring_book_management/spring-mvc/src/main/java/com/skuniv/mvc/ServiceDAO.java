package com.skuniv.mvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ServiceDAO {
	private JdbcTemplate jdbcTemplate;

	public ServiceDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void Binsert(Long Mnum, Long Bnum) {
		jdbcTemplate.update("insert into BorrowTable (Mnum, Bnum) " + " values (?, ?) ", Mnum, Bnum);
	}

	public void Rinsert(Long Mnum, Long Bnum) {
		jdbcTemplate.update("insert into ReserveTable (Mnum, Bnum) " + " values (?, ?) ", Mnum, Bnum);
	}

	public void Bdelete(Long Mnum, Long Bnum) {
		jdbcTemplate.update("delete from BorrowTable where Mnum = ? and Bnum = ?", Mnum, Bnum);
	}

	public void Rdelete(Long Mnum, Long Bnum) {
		jdbcTemplate.update("delete from ReserveTable where Mnum = ? and Bnum = ?", Mnum, Bnum);
	}

	
	public List<BorrowTable> BRselect(Long Bnum, Long Mnum) {
		List<BorrowTable> results = jdbcTemplate.query("select * from BorrowTable where Mnum = ? and Bnum = ? ",
				new RowMapper<BorrowTable>() {
					@Override
					public BorrowTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						BorrowTable borrowTable = new BorrowTable(rs.getLong("Bnum"), rs.getLong("Mnum"));
						borrowTable.setBtnum(rs.getLong("BTnum"));
						return borrowTable;
					}
				}, Mnum, Bnum);
		return results.isEmpty() ? null : results;
	}

	public int BRcount(Long Mnum, Long Bnum) {
		List<Integer> count = jdbcTemplate.query("select count(*) from borrowTable where Mnum = ? and Bnum = ?",
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				}, Mnum, Bnum);
		return count.get(0);
	}

	public List<BorrowTable> BselectByBnum(Long Bnum) {
		List<BorrowTable> results = jdbcTemplate.query("select * from BorrowTable where Bnum = ?",
				new RowMapper<BorrowTable>() {
					@Override
					public BorrowTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						BorrowTable borrowTable = new BorrowTable(rs.getLong("Bnum"), rs.getLong("Mnum"));
						borrowTable.setBtnum(rs.getLong("BTnum"));
						return borrowTable;
					}
				}, Bnum);
		return results.isEmpty() ? null : results;
	}

	/*public List<BorrowTable> BselectByMnum(Long Mnum) {
		List<BorrowTable> results = jdbcTemplate.query("select * from BorrowTable where Mnum = ?",
				new RowMapper<BorrowTable>() {
					@Override
					public BorrowTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						BorrowTable borrowTable = new BorrowTable(rs.getLong("Mnum"), rs.getLong("Bnum"));
						borrowTable.setMnum(rs.getLong("BTnum"));
						return borrowTable;
					}
				}, Mnum);
		return results.isEmpty() ? null : results;
	}*/
	public List<Book> BselectByMnum2(Long Mnum) {
		List<Book> results = jdbcTemplate.query("select * from book where Bnum IN (select bt2.Bnum from BorrowTable as bt2 where Mnum = ?)",
				new RowMapper<Book>() {
					@Override
					public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
						Book Book = new Book(rs.getString("Bname"),rs.getString("Publisher"), rs.getString("Author"));
						Book.setBnum(rs.getLong("Bnum"));
						return Book;
					}
				}, Mnum);
		return results.isEmpty() ? null : results;
	}
	public List<Book> RselectByMnum2(Long Mnum) {
		List<Book> results = jdbcTemplate.query("select * from book where Bnum IN (select bt2.Bnum from ReserveTable as bt2 where Mnum = ?)",
				new RowMapper<Book>() {
					@Override
					public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
						Book Book = new Book(rs.getString("Bname"),rs.getString("Publisher"), rs.getString("Author"));
						Book.setBnum(rs.getLong("Bnum"));
						return Book;
					}
				}, Mnum);
		return results.isEmpty() ? null : results;
	}

	public int BcountByBnum(Long Bnum) {
		List<Integer> count = jdbcTemplate.query("select count(*) from BorrowTable where Bnum = ? ",
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				}, Bnum);
		return count.get(0);
	}

	public int BcountByMnum(Long Mnum) {
		List<Integer> count = jdbcTemplate.query("select count(*) from BorrowTable where Mnum = ?",
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				}, Mnum);
		return count.get(0);
	}

	public List<ReserveTable> RselectByBnum(Long Bnum) {
		List<ReserveTable> results = jdbcTemplate.query("select * from ReserveTable where Bnum = ?",
				new RowMapper<ReserveTable>() {
					@Override
					public ReserveTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						ReserveTable reserveTable = new ReserveTable(rs.getLong("Bnum"), rs.getLong("Mnum"));
						reserveTable.setRtnum(rs.getLong("RTnum"));
						return reserveTable;
					}
				}, Bnum);
		return results.isEmpty() ? null : results;
	}
	

	/*public List<ReserveTable> RselectByMnum(Long Mnum) {
		List<ReserveTable> results = jdbcTemplate.query("select * from ReserveTable where Mnum = ?",
				new RowMapper<ReserveTable>() {
					@Override
					public ReserveTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						ReserveTable reserveTable = new ReserveTable(rs.getLong("Mnum"), rs.getLong("Bnum"));
						reserveTable.setMnum(rs.getLong("RTnum"));
						return reserveTable;
					}
				}, Mnum);
		return results.isEmpty() ? null : results;
	}*/

	public int RcountByBnum(Long Bnum) {
		List<Integer> count = jdbcTemplate.query("select count(*) from ReserveTable where Bnum = ?", new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		}, Bnum);
		return count.get(0);
	}

	public int RcountByMnum(Long Mnum) {
		List<Integer> count = jdbcTemplate.query("select count(*) from ReserveTable where Mnum = ?",
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				},Mnum);
		return count.get(0);
	}
	
	

}
