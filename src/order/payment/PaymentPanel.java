package order.payment;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBManager;
import dto.Orders;
import goods.GoodsMain;

public class PaymentPanel extends JPanel implements ActionListener{
	Payment payment;
	JButton bt_back, bt_payment_complete;
	JPanel p_south, p_center;
	String orders_payment_type;
	DBManager manager;
	Connection con;
	Vector<Point> point_list;
	
	public PaymentPanel(Payment payment, String type) {
		this.payment=payment;
		this.orders_payment_type=type;
		this.point_list=payment.point_list;
		
		setLayout(new BorderLayout());
		
		p_south=new JPanel();
		p_center = new JPanel();
		bt_back = new JButton("뒤로");
		bt_payment_complete = new JButton("결제완료");
		
		p_south.add(bt_back);
		p_south.add(bt_payment_complete);
		
		add(p_south, BorderLayout.SOUTH);
		add(p_center);
		
		bt_back.addActionListener(this);
		bt_payment_complete.addActionListener(this);
		
		//연결작업하자하자!
		init();
		
	}
	
	public void init(){
		//연결부터
		manager = DBManager.getInstance();
		con=manager.getConnection();
		
	}
	
	//dto를 주문 관리자에게 보내기
	//con에 연결해서 주문테이블에 type을 채워서 보내야 한다.
	public void send(){
		
		PreparedStatement pstmt = null;
		try {
			for(int i=0; i<payment.orders_list.size();i++){
			StringBuffer sb = new StringBuffer();
			sb.append("insert into orders (product_id, orders_date, orders_emp_id, orders_client_id, orders_status, orders_payment_type, orders_type)");
			sb.append(" values(?,current_timestamp(),?,?,?,?,?)");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, payment.orders_list.get(i).getProduct_id());
			//pstmt.setTimestamp(2, "current_timestamp()");
			pstmt.setInt(2, payment.orders_list.get(i).getOrders_emp_id());
			pstmt.setInt(3, payment.orders_list.get(i).getOrders_client_id());
			pstmt.setString(4, "ready");
			pstmt.setString(5, orders_payment_type);
			pstmt.setString(6, "offline");
			
			
			int result = pstmt.executeUpdate();
			}
				JOptionPane.showMessageDialog(this, "1건 결제 완료");
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "결제오류");
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		receipt(this, orders_payment_type);//영수증 출력
	}
	
	public void receipt(PaymentPanel paymentPanel, String type){
		//끄는 버튼까지 있어야한다~!
		new Receipt(paymentPanel, type);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		 if(obj==bt_back){
			 PaymentPanel.this.setVisible(false);
			 payment.p_menu.setVisible(true);
		}else if(obj==bt_payment_complete){
			send();

		}
	}

}
