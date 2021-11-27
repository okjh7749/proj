package com.skuniv.mvc;

import java.util.List;

public class ReturnBook implements ReturnBookI {
	private MemberDAO memberDAO;
	private BookDAO bookDAO;
	private ServiceDAO serviceDAO;

	public ReturnBook(MemberDAO memberDAO, BookDAO bookDAO, ServiceDAO serviceDAO) {
		this.bookDAO = bookDAO;
		this.memberDAO = memberDAO;
		this.serviceDAO = serviceDAO;
	}

	public List<Book> returnBook(String ID, String Bname) {
		Member member = memberDAO.selectByID(ID);
		Book book = bookDAO.selectByBname(Bname);
		if (serviceDAO.BRcount(member.getMnum(), book.getBnum()) > 0) {
			serviceDAO.Bdelete(member.getMnum(), book.getBnum());
		}
		else
			return null;
		return serviceDAO.BselectByMnum2(member.getMnum());
	}

}
