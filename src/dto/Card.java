package dto;

public class Card {
	private int card_id;
	private int member_id;
	private String card_number;
	private String card_username;
	private String card_valid;
	private String card_companyname;
	
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCard_username() {
		return card_username;
	}
	public void setCard_username(String card_username) {
		this.card_username = card_username;
	}
	public String getCard_valid() {
		return card_valid;
	}
	public void setCard_valid(String card_valid) {
		this.card_valid = card_valid;
	}
	public String getCard_companyname() {
		return card_companyname;
	}
	public void setCard_companyname(String card_companyname) {
		this.card_companyname = card_companyname;
	}
	
	

}
