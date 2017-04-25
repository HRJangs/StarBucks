//JSON 프로토콜 객체 틀을 만들어두고, 이걸 가지고 갖다쓰도록 하자!
//
package json;

import java.sql.Timestamp;

public class OrdersProtocol {
	StringBuffer sb = new StringBuffer();
	
	public OrdersProtocol(int product_id, int orders_client_id) {
		sb.append("{");
		sb.append("\"requestType\":\"order\",");
		sb.append("\"product_id\":"+product_id+",");
		sb.append("\"orders_emp_id\":0,"); //온라인 주문은 직원번호를 0으로 처리
		sb.append("\"orders_client_id\":"+orders_client_id+",");
		sb.append("\"orders_status\":\"ready\",");
		sb.append("\"orders_payment_type\":\"card\",");
		sb.append("\"orders_type\":\"online\"");
		sb.append("}");
	}
	public StringBuffer getProtocol(){
		return sb;
	}
}
