package com.skuniv.mvc;

public class AddMember implements AddMemberI{
	private MemberDAO memberDAO; 
	
	public AddMember(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public void addMember(String ID,String Password) {
		Member member=new Member(ID,Password);
		memberDAO.insert(member);
	}
}
