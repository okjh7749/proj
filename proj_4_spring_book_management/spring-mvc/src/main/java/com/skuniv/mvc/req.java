package com.skuniv.mvc;

public class req {
	Long mnum;
	String id;
	String password;
	boolean ban =true;
	Long bnum;
	String bname ;
	String publisher;
	String author;

	
	public void setMnum(Long Mnum) {
		this.mnum = Mnum;
	}
	public void setId(String ID) {
		this.id = ID;
	}
	public void setPassword(String Password) {
		this.password = Password;
	}
	public void setBan(boolean Ban) {
		this.ban = Ban;
	}
	
	public Long getMnum() {
		return mnum;
	}
	public String getID() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public boolean getBan() {
		return ban;
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
