package com.skuniv.mvc;

public class AddBook implements AddBookI{
	private BookDAO bookDAO; 
	
	public AddBook(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}
	
	public void addBook(String Bname,String Publisher, String Author) {
		Book book=new Book(Bname,Publisher,Author);
		bookDAO.insert(book);
	}
}
