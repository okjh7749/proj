package com.skuniv.mvc;

import java.util.List;

public class ReserveBook implements ReserveBookI {
	private MemberDAO memberDAO;
	private BookDAO bookDAO;
	private ServiceDAO serviceDAO;

	public ReserveBook(MemberDAO memberDAO, BookDAO bookDAO,ServiceDAO serviceDAO) {
		this.bookDAO = bookDAO;
		this.memberDAO = memberDAO;
		this.serviceDAO = serviceDAO;
	}
	public List<ReserveTable> reserveBook(String ID, String Bname) {
		Member member = memberDAO.selectByID(ID);
		Book book = bookDAO.selectByBname(Bname);
		if(serviceDAO.BcountByBnum(book.getBnum())>0) {
			serviceDAO.Rinsert(member.getMnum(), book.getBnum());
	
			return serviceDAO.RselectByBnum(book.getBnum());
		}
		else {
			return null;
		}
		
	}

}
