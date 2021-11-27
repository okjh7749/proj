package com.skuniv.mvc;

public class Book {
	Long bnum;
	String bname;
	String publisher;
	String author;

	Book() {
		this.bnum = null;
		this.bname = null;
		this.publisher = null;
		this.author = null;
	}

	Book(String Bname, String Author, String publisher) {
		this.bname = Bname;
		this.publisher = publisher;
		this.author = Author;

	}

	public void setBnum(Long Bnum) {
		this.bnum = Bnum;
	}

	public void setBname(String Bname) {
		this.bname = Bname;
	}

	public void setPublisher(String Publisher) {
		this.publisher = Publisher;
	}

	public void setAuthor(String Author) {
		this.author = Author;
	}

	public Long getBnum() {
		return bnum;
	}

	public String getBname() {
		return bname;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getAuthor() {
		return author;
	}

}
