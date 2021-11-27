package com.skuniv.mvc;

public class FindMember implements FindMemberI{
	private MemberDAO memberDAO;
	public FindMember(MemberDAO memberDAO) {
		this.memberDAO=memberDAO;
	}
	
	public Member findMember(String ID) {
		Member member = memberDAO.selectByID(ID);
		return member;
	}

}
