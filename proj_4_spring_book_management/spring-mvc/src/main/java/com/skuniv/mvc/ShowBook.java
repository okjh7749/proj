package com.skuniv.mvc;

import java.util.List;

public class ShowBook {
	private BookDAO bookDAO;

	public ShowBook(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public List<Book> showBook() {
		return bookDAO.selectAll();
	}
}
