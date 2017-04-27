/*
 * 주문 리스트를 출력하는 화면 - 완료 버튼을 누르면 주문테이블의 update가 일어나고
 * 매출테이블에 insert와 호출Frame에 보이게 됨. 
 * */
package order.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import db.DBManager;
import dto.Orders;

public class OrdersListMain extends JFrame implements Runnable{
	DBManager manager;
	Connection con;
	CallMain callMain;
	Vector<IndividualOrder> panelOrderList = new Vector<IndividualOrder>();
	Vector<Orders> orderList = new Vector<Orders>();
	JPanel p_title1, p_orderList;
	JLabel la_title;
	JScrollPane scroll;
	
	Thread thread;
	
	public OrdersListMain() {
		manager = DBManager.getInstance();
		con = manager.getConnection();
		
		this.setLayout(new FlowLayout());
		this.setTitle("                주문한 제품 이름                             client_id, orders_id");
		
		p_orderList = new JPanel();
		scroll = new JScrollPane(p_orderList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		/*la_title = new JLabel("주문한 제품 이름                         client_id, orders_id");
		la_title.setBackground(new Color(0, 111, 73));
		la_title.setOpaque(true);
		la_title.setPreferredSize(new Dimension(540, 30));*/
		
		thread = new Thread(this);
		thread.start();
		
		add(scroll);

		p_orderList.setPreferredSize(new Dimension(540, 650));
		scroll.setPreferredSize(new Dimension(560, 650));
		pack();
		//setSize(520, 600);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public void getOrderList() {//나중에 쓰레드 안에 들어갈 메서드
		orderList.removeAll(orderList);
		p_orderList.removeAll();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from orders o, product p where o.product_id = p.product_id and o.orders_status = 'ready'";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Orders order = new Orders();
				
				order.setOrders_id(rs.getInt("o.orders_id"));
				order.setProduct_id(rs.getInt("o.product_id"));
				order.setOrders_date(rs.getTimestamp("o.orders_date"));
				/*
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(rs.getTimestamp("orders_date"));
				order.setOrders_date(currentTime);*/
				
				order.setOrders_emp_id(rs.getInt("o.orders_emp_id"));
				order.setOrders_client_id(rs.getInt("o.orders_client_id"));
				order.setOrders_status(rs.getString("o.orders_status"));
				order.setOrders_payment_type(rs.getString("o.orders_payment_type"));
				order.setOrders_type(rs.getString("o.orders_type"));
				order.setProduct_name(rs.getString("p.product_name"));
				
				//rs당 하나씩 주문에 추가 생성자 매개변수에 주문 하나 dto 넘기기
				IndividualOrder panel_order = new IndividualOrder(this, order);
				
				panelOrderList.add(panel_order);
				orderList.add(order);//orders dto넣기
				p_orderList.add(panel_order);
				p_orderList.updateUI();
				
				System.out.println(order.getProduct_name() + ", " + order.getProduct_id() + ", " + order.getOrders_status());
			}
			p_orderList.setPreferredSize(new Dimension(540, orderList.size()*(30+5) + 50));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public void setCallMain(CallMain callMain) {
		this.callMain = callMain;
	}
	
	public CallMain getCallMain() {
		return callMain;
	}
	
	public void getFinish(int client_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//클라이언트 아이디가 같은 주문이 다 완료버튼이 눌리면 callMain으로 보내기 
		String select_sql ="select * from orders where orders_client_id = ?";
		//resultset으로 받아서 orders_status가 모두 완료이면 callMain의 callMember호출
		Vector<Orders> isCompleted = new Vector<Orders>();
		boolean flag = false;
		
		try {
			pstmt = con.prepareStatement(select_sql);
			pstmt.setInt(1, client_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Orders order = new Orders();
				
				order.setOrders_id(rs.getInt("orders_id"));
				order.setProduct_id(rs.getInt("product_id"));
				order.setOrders_date(rs.getTimestamp("orders_date"));
				//order.setOrders_date(rs.getString("orders_date"));
				order.setOrders_emp_id(rs.getInt("orders_emp_id"));
				order.setOrders_client_id(rs.getInt("orders_client_id"));
				order.setOrders_status(rs.getString("orders_status"));
				order.setOrders_payment_type(rs.getString("orders_payment_type"));
				order.setOrders_type(rs.getString("orders_type"));
				
				isCompleted.add(order);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		for (int i = 0; i < isCompleted.size(); i++) {
			if (isCompleted.get(i).getOrders_status().equals("완료")) {
				flag = true;
			}
			else {
				flag = false;
				break;
			}
			System.out.println("isCompleted " + isCompleted.get(i).getOrders_status());
		}
		System.out.println(flag);
		if (flag) {
			callMain.callMember(isCompleted);
		}
		
	}

	public void run() {
		while(true) {
			try {
				getOrderList();
				Thread.sleep(1000);
				p_orderList.updateUI();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}


	/*public static void main(String[] args) {
		new OrdersListMain();

	}*/

}
