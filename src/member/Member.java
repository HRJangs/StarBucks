package member;

public class Member {

	private String member_id;
	private String member_login_id;
	private String member_login_pw;
	private String member_name;
	private String member_nickname;
	private String member_gender;
	private String member_phone;
	private String member_birth;
	private String member_coupon;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_login_id() {
		return member_login_id;
	}

	public void setMember_login_id(String member_login_id) {
		this.member_login_id = member_login_id;
	}

	public String getMember_login_pw() {
		return member_login_pw;
	}

	public void setMember_login_pw(String cs) {
		this.member_login_pw = cs;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_gender() {
		return member_gender;
	}

	public void setMember_gender(String member_gender) {
		this.member_gender = member_gender;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_birth() {
		return member_birth;
	}

	public void setMember_birth(Object object) {
		this.member_birth = (String) object;
	}

	public String getMember_coupon() {
		return member_coupon;
	}

	public void setMember_coupon(String member_coupon) {
		this.member_coupon = member_coupon;
	}

}
