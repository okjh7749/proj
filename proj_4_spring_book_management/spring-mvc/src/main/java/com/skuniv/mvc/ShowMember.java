package com.skuniv.mvc;

import java.util.List;

public class ShowMember implements ShowMemberI {
	private MemberDAO memberDAO;

	public ShowMember(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public List<Member> showMember() {
		return memberDAO.selectAll();
	}
}
