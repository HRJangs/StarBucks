package json;

import dto.Card;

public class CardProtocol {
	StringBuffer sb = new StringBuffer();
	
	public CardProtocol(Card card) {
		sb.append("{");
		sb.append("\"requestType\":\"card\",");
		sb.append("\"member_id\" : 2,");
		sb.append("\"card_number\" : \" "+ card.getCard_number() +"\",");
		sb.append("\"card_username\" : \""+ card.getCard_username() + "\",");
		sb.append("\"card_valid\" : \"" + card.getCard_valid() + "\",");
		sb.append("\"card_companyname\" : \""+ card.getCard_companyname() +"\"");
		sb.append("}");
	}
	
	public String getProtocol() {
		return sb.toString();
	}
}
