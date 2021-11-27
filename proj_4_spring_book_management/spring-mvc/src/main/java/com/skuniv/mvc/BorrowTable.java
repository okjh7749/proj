package com.skuniv.mvc;

public class BorrowTable {
	Long btnum;
	Long bnum;
	Long mnum;
	
	BorrowTable(Long Bnum, Long Mnum){
		this.btnum = null;
		this.bnum =Bnum;
		this.mnum=Mnum;
		
	}
	
	public void setBtnum(Long BTnum) {
		this.btnum=BTnum;
	}
	public void setBnum(Long Bnum) {
		this.bnum =Bnum;
	}
	public void setMnum(Long Mnum) {
		this.mnum =Mnum;
	}
	

	public Long getBtnum() {
		return btnum;
	}
	public Long getBnum() {
		return bnum;
	}
	public Long getMnum() {
		return mnum;
	}
}


