package com.skuniv.mvc;

import java.util.List;

public class BorrowBook implements BorrowBookI {
	private MemberDAO memberDAO;
	private BookDAO bookDAO;
	private ServiceDAO serviceDAO;

	public BorrowBook(MemberDAO memberDAO, BookDAO bookDAO, ServiceDAO serviceDAO) {
		this.bookDAO = bookDAO;
		this.memberDAO = memberDAO;
		this.serviceDAO = serviceDAO;
	}

	public List<Book> borrowBook(String ID, String Password, String Bname) {
		Member member = memberDAO.selectByID(ID);
		Book book = bookDAO.selectByBname(Bname);
		if (!(member.getBan())) {
			if (serviceDAO.BcountByMnum(member.getMnum()) < 3) {// 빌린책이 3권이하인지 체크
				if (serviceDAO.BcountByBnum(book.getBnum()) == 0) {// 해당책이 대출중인지 체크

					if (serviceDAO.RcountByBnum(book.getBnum()) > 0) {// 예약자가 있는지 체크
						if (serviceDAO.RselectByBnum(book.getBnum()).get(0).getMnum()==(member.getMnum())) {// 예약자의
							System.out.println(serviceDAO.RselectByBnum(book.getBnum()).get(0).getMnum());
							serviceDAO.Binsert(member.getMnum(), book.getBnum());
							serviceDAO.Rdelete(member.getMnum(), book.getBnum());
						}
						else
							return null;
					}
					else
						serviceDAO.Binsert(member.getMnum(), book.getBnum());
				}
				else
					return null;
			}
			else
				return null;
		}

		return serviceDAO.BselectByMnum2(member.getMnum());
	}

}
