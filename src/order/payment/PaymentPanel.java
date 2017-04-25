package order.payment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	public PaymentPanel(Payment payment, String type) {
		this.payment=payment;
		this.orders_payment_type=type;
		
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
			//manager.disConnection(con);
			
			//이건 나중에 지워야 됨 일단 재고처리까지 되는걸로 처리해버림~~!
			//Orders dto = new Orders();
			//new GoodsMain(dto);
			
			//위에꺼지우면 여기서 다시 켜야됨~!
			System.exit(0);
		}
		
		
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
