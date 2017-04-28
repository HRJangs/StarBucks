package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import db.DBManager;

public class ServerThread extends Thread {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	DBManager manager = DBManager.getInstance();
	Connection con;
	JSONObject obj;
	String type;
	String resType;
	String cardType;
	boolean flag=true;

	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		con = manager.getConnection();
	}

	// 주문 쿼리 날리기!
	public void sendQuery() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"insert into orders (product_id, orders_date, orders_emp_id, orders_client_id, orders_status, orders_payment_type, orders_type)");
		sb.append(" values(?,current_timestamp(),?,?,?,?,?)");

		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sb.toString());
			System.out.println(obj.get("product_id"));
			pstmt.setInt(1, Long.valueOf((long) obj.get("product_id")).intValue());
			pstmt.setInt(2, Long.valueOf((long) obj.get("orders_emp_id")).intValue());
			pstmt.setInt(3, Long.valueOf((long) obj.get("orders_client_id")).intValue());
			pstmt.setString(4, (String) obj.get("orders_status"));
			pstmt.setString(5, (String) obj.get("orders_payment_type"));
			pstmt.setString(6, (String) obj.get("orders_type"));

			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("쿼리 성공");
				send();
				System.out.println("결과 전송");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public void sendCard() {
		cardType = (String) obj.get("type");
		PreparedStatement pstmt = null;
		
		if(cardType.equals("insert")) {
			String sql = "insert into card(member_id, card_number, card_username, card_valid, card_companyname, card_password) values(?, ?, ?, ?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				//System.out.println(obj.get("member_id") instanceof Long);
				pstmt.setInt(1,  Long.valueOf((long)obj.get("member_id")).intValue());
				pstmt.setString(2, (String)obj.get("card_number"));
				pstmt.setString(3, (String)obj.get("card_username"));
				pstmt.setString(4, (String)obj.get("card_valid"));
				pstmt.setString(5, (String)obj.get("card_companyname"));
				pstmt.setString(6, (String)obj.get("card_password"));
				
				int result = pstmt.executeUpdate();
				
				if(result != 0) {
					System.out.println("성공");
					send();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(cardType.equals("delete")) {
			String sql = "delete from card where card_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1,  Long.valueOf((long)obj.get("card_id")).intValue());
				
				int result = pstmt.executeUpdate();
				
				if(result != 0) {
					System.out.println("성공");
					send();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public void reservationType() {
		resType = (String) obj.get("type");
		System.out.println("서버스레드 : " + resType);
		PreparedStatement pstmt = null;
		
		if(resType.equals("insert")) {
			String sql = "insert into reservation(reservation_room_num, reservation_current_time, reservation_member_login_id, reservation_time_unit, reservation_start_time, reservation_year, reservation_month, reservation_date) values(?, current_timestamp(), ?, ?, ?, ?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				System.out.println("insert : " + sql);
				pstmt.setInt(1, Long.valueOf((long)obj.get("reservation_room_num")).intValue());
				pstmt.setString(2, (String)obj.get("reservation_member_login_id"));
				pstmt.setInt(3, Long.valueOf((long)obj.get("reservation_time_unit")).intValue());
				pstmt.setInt(4, Long.valueOf((long)obj.get("reservation_start_time")).intValue());
				pstmt.setInt(5, Long.valueOf((long)obj.get("reservation_year")).intValue());
				pstmt.setInt(6, Long.valueOf((long)obj.get("reservation_month")).intValue());
				pstmt.setInt(7, Long.valueOf((long)obj.get("reservation_date")).intValue());
				
				int result = pstmt.executeUpdate();
				
				if(result != 0) {
					System.out.println("성공");
					send();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else if(resType.equals("delete")) {
			String sql = "delete from reservation where reservation_year = ? and reservation_month = ? and reservation_date = ? and reservation_start_time = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				System.out.println("delete : " + sql);
				pstmt.setInt(1, Long.valueOf((long)obj.get("reservation_year")).intValue());
				pstmt.setInt(2, Long.valueOf((long)obj.get("reservation_month")).intValue());
				pstmt.setInt(3, Long.valueOf((long)obj.get("reservation_date")).intValue());
				pstmt.setInt(4, Long.valueOf((long)obj.get("reservation_start_time")).intValue());
				
				int result = pstmt.executeUpdate();
				System.out.println(Long.valueOf((long)obj.get("reservation_start_time")).intValue() + "시 결과 : " + result);
				if(result != 0) {
					System.out.println("성공");
					send();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void EditMember() {
 		PreparedStatement pstmt = null;
 		String id = (String) obj.get("member_login_id");
 		id.replace(" ", "");
 		System.out.println("하하"+id);
 		String sql = "update member set member_login_pw =?  where member_login_id='"+id+"'";
 		try {
 			//비번수정
 			System.out.println(sql);
 			pstmt = con.prepareStatement(sql);
 			pstmt.setString(1, (String)obj.get("member_login_pw"));
 			int pw =  pstmt.executeUpdate();
 			
 			sql = "update member set member_name =?   where member_login_id='"+id+"'";
 			pstmt = con.prepareStatement(sql);
 			pstmt.setString(1, (String)obj.get("member_name"));
 			int name = pstmt.executeUpdate();
 			
 			sql = "update member set member_nickname =?  where member_login_id='"+id+"'";
 			pstmt = con.prepareStatement(sql);
 			pstmt.setString(1, (String)obj.get("member_nickname"));
 			int nickname = pstmt.executeUpdate();
 			
 			sql = "update member set member_birth =?  where member_login_id='"+id+"'";
 			pstmt = con.prepareStatement(sql);
 			pstmt.setString(1, (String)obj.get("member_birth"));
 			int birth= pstmt.executeUpdate();
 			
 			sql = "update member set member_phone =?   where member_login_id='"+id+"'";
 			pstmt = con.prepareStatement(sql);
 			pstmt.setString(1, (String)obj.get("member_phone"));
 			int phone = pstmt.executeUpdate();
 			
 			System.out.println("여긴들어오나");
 			send();
 			System.out.println(pw);
 			System.out.println(name);
 			System.out.println(nickname);
 			System.out.println(birth);
 			System.out.println(phone);
 			if(pw != 0 && name !=0 && nickname !=0 && birth !=0 && phone !=0) {
 				System.out.println("성공");
 			}
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}finally{
 			System.out.println("들어오긴하니");
 		}
 	}
	
	public void listen() {
		try {
			String data = buffr.readLine();
			// 제이슨파싱
			JSONParser parser = new JSONParser();
			obj = (JSONObject) parser.parse(data);
			String requestType = (String) obj.get("requestType");
			// 타입이 오더라면, 주문 쿼리 넣어줘야 한다.
			if (requestType.equals("order")) {
				type = "order";
				sendQuery();
			} else if(requestType.equals("card")) {
				type = "card";
				sendCard();
			} else if(requestType.equals("reservation")) {
				type = "reservation";
				reservationType();
			} else if(requestType.equals("member")){
				type = "member";
				EditMember();
			}
			// 쿼리문 한번 날리고 한번 쉬자!
			Thread.sleep(100);
		} catch (IOException e) {
			flag=false;//런죽이기~!
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void send() {
		String str = null;
		
		if(type.equals("order")) {
			str = "주문완료";
		} else if(type.equals("card") && cardType.equals("insert")) {
			str = "카드등록완료";
		} else if(type.equals("card") && cardType.equals("delete")) {
			str = "카드삭제완료";
		} else if(type.equals("reservation") && resType.equals("insert")) {
			str = "예약 완료";
		} else if(type.equals("reservation") && resType.equals("delete")) {
			str = "예약 변경&삭제 완료";
		} else if(type.equals("member")) {
			str = "회원수정완료";
		} 
		
		try {
			buffw.write(str + "\n");
			buffw.flush();
			System.out.println("보냄");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 여기서 이제 서버가 처리해줄 껄 다 작성해줘야한다.
	public void run() {
		while(flag){
			listen();
		}

	}

}
