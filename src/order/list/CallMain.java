package order.list;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DBManager;
import dto.Orders;

public class CallMain extends JFrame{
	DBManager manager = DBManager.getInstance();
	Connection con;
	Vector<String> callList = new Vector<String>(6);
	JPanel container;
	JPanel[] panel = new JPanel[6];
	JLabel[] label = new JLabel[6];
	
	public CallMain() {
		con = manager.getConnection();
		
		container = new JPanel();
		container.setLayout(new GridLayout(2, 3));
		
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			label[i] = new JLabel("");
			label[i].setFont(new Font("돋움", Font.PLAIN, 20));
			panel[i].add(label[i]);
			panel[i].setBackground(Color.LIGHT_GRAY);
			panel[i].setPreferredSize(new Dimension(100, 100));
			panel[i].setBounds((i+10)* 100, 0, 100, 100);
			panel[i].setBorder(BorderFactory.createLineBorder(Color.white));
			container.add(panel[i]);
		}

		label[0].setFont(new Font("돋움", Font.BOLD, 30));
		
		//add(new JLabel("호출용"), BorderLayout.NORTH);
		add(container);
		setSize(400, 300);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void callMember(Vector<Orders> orderList) {
		/*
		 * 근데..?? 같은 클라이언트가 여러개를 주문하면 
		 * orders_type가 온라인이면 닉네임 호출 조인해서
		 * 오프라인이면 client_id로 호출
		*/
		String sql = null;
		
		Vector<String> backupList = new Vector<String>();
		/*
		 * 항상 첫번째 패널에 에 호출하기
		 * 리스트에 들어있는 나머지 호출은 한칸씩 옆으로 넘기고 --> 덮어쓰기
		*/
		
		if(orderList.get(0).getOrders_type().equals("offline")) {
			//네임 출력하디
			//label[0].setText(orderList.get(0).getOrders_client_id() + "님 음료 나왔습니다.");
			//나머지 배열은 옆으로 덮어쓰기
			
			backupList.add(Integer.toString(orderList.get(0).getOrders_client_id()));
			
		} else if(orderList.get(0).getOrders_type().equals("online")) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			sql = "select m.member_login_id from member m, orders o where m.member_id = o.orders_client_id and o.orders_client_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, orderList.get(0).getOrders_client_id());
				
				rs = pstmt.executeQuery();
				
				String member_id = null;
				System.out.println(orderList.get(0).getOrders_client_id());
				while(rs.next()) {
					//멤버 로그인 아이디를 받아와서 첫번째 아이디를 받아서 backuplist에 받아두기
					member_id = rs.getString("m.member_login_id");
				}
				
				backupList.add(member_id);
				System.out.println(member_id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		for (int i = 0; i < callList.size(); i++) {
			backupList.add(callList.get(i));
		}
		
		callList.removeAll(callList);

		callList.addAll(backupList);
		
		System.out.println(callList.size());
		
		for (int i = 0; i < label.length; i++) {
			if(i >= backupList.size()) {
				label[i].setText("");
			}
			else {
				label[i].setText(backupList.get(i));
			}
		}
		
		backupList.removeAll(backupList);
	}
	/*
	public static void main(String[] args) {
		new CallMain();
	}*/

}
