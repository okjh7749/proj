package com.skuniv.mvc;

public class FindBook implements FindBookI{
	private BookDAO bookDAO; 
	public FindBook(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}
	
	public Book findBook(String bookname) {
		Book book = bookDAO.selectByBname(bookname);
		return book;
	}

}
