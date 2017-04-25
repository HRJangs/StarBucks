package json;

import dto.Member;

public class MemberProtocol {
	StringBuffer sb = new StringBuffer();
	Member member;
	public MemberProtocol(Member member) {
		this.member=member;
		sb.append("{");
		sb.append("\"requestType\":\"member\",");
		sb.append("\"member_login_id\":\""+member.getMember_login_id()+"\",");
		sb.append("\"member_login_pw\":\""+member.getMember_login_pw()+"\",");
		sb.append("\"member_name\":\""+member.getMember_name()+"\",");
		sb.append("\"member_nickname\":\""+member.getMember_nickname()+"\",");
		sb.append("\"member_phone\":\""+member.getMember_phone()+"\",");
		sb.append("\"member_birth\":\""+member.getMember_birth()+"\",");
		sb.append("}");
	}
	public String getProtocol(){
		return sb.toString();
	}
}