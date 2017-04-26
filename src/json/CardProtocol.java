package json;

import dto.Card;

public class CardProtocol {
	StringBuffer sb = new StringBuffer();
	
	public CardProtocol(Card card, String type) {
		sb.append("{");
		sb.append("\"requestType\":\"card\",");
		sb.append("\"type\":\"" + type + "\",");
		sb.append("\"card_id\" : " + card.getCard_id() + ",");
		sb.append("\"member_id\" : " + card.getMember_id() + ",");
		sb.append("\"card_number\" : \" "+ card.getCard_number() +"\",");
		sb.append("\"card_username\" : \""+ card.getCard_username() + "\",");
		sb.append("\"card_valid\" : \"" + card.getCard_valid() + "\",");
		sb.append("\"card_companyname\" : \""+ card.getCard_companyname() +"\"");
		sb.append("\"card_password\" : \""+ card.getCard_password() +"\"");
		sb.append("}");
	}
	
	public String getProtocol() {
		return sb.toString();
	}
}
