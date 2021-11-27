package com.skuniv.mvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class BookDAO {
	private JdbcTemplate jdbcTemplate;
	public BookDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<Book> selectAll() {
		List<Book> results = jdbcTemplate.query("select * from Book", (ResultSet rs, int rowNum) -> {
			Book book = new Book(rs.getString("Bname"), rs.getString("Publisher"),rs.getString("Author"));
			book.setBnum(rs.getLong("Bnum"));
			return book;
		});
		return results;
	}

	public Book selectByBname(String Bname) {
		List<Book> results = jdbcTemplate.query("select * from Book where Bname = ?", new RowMapper<Book>() {
			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				Book book = new Book(rs.getString("Bname"), rs.getString("Author"),rs.getString("Publisher"));
				book.setBnum(rs.getLong("Bnum"));
				return book;
			}
		}, Bname);
		return results.isEmpty() ? null : results.get(0);
	}

	public void insert(Book book) {
		jdbcTemplate.update("insert into Book (Bname, Author,Publisher) " + " values (?, ?, ?) ", book.getBname(),
				book.getAuthor(),book.getPublisher());
	}

	public void update(Book book) {
		jdbcTemplate.update("update Member set Bname = ?, Author = ?, Publisher = ? " + " where Bname = ? ", book.getBname(),
				book.getAuthor(),book.getPublisher(),book.getBname());
	}


}
