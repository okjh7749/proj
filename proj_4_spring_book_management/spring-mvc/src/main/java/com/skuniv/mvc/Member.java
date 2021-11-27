package com.skuniv.mvc;

public class Member {
	private Long mnum;
	private String id;
	private String password;
	private boolean ban =true;
	
	Member(){
		this.id =null;
		this.password=null;
		this.ban=true;
	}
	Member(String ID,String Password){
		this.id =ID;
		this.password=Password;
		this.ban=false;
	}
	
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
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public boolean getBan() {
		return ban;
	}

	
}
