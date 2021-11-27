package com.skuniv.mvc;

public class ReserveTable {
	Long rtnum;
	Long bnum;
	Long mnum;
	
	ReserveTable(Long Bnum, Long Mnum){
		this.rtnum = null;
		this.bnum =Bnum;
		this.mnum =Mnum;
		
	}
	
	public void setBnum(Long Bnum) {
		this.bnum =Bnum;
	}
	public void setMnum(Long Mnum) {
		this.mnum =Mnum;
	}
	public void setRtnum(Long RTnum) {
		this.rtnum = RTnum;
	}

	public Long getBnum() {
		return bnum;
	}
	public Long getMnum() {
		return mnum;
	}
	public Long getRtnum() {
		return rtnum;
	}
}
