package order.payment;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db.DBManager;
import dto.Orders;

public class CouponPanel extends PaymentPanel{
	Payment payment;
	String type;
	JLabel la_coupon_number;
	JTextField t_coupon_number;
	JButton bt_check;
	JLabel la_title; //조회 여부 확인 
	JLabel la_content; //조회 여부 확인
	DBManager manager=DBManager.getInstance();
	Connection con=manager.getConnection();
	Font font = new Font("돋움", Font.BOLD, 15);
//	Orders dto;


	
	//쿠폰관련 패널을 아예 만들어서 처음에는 상품이 1개초과로 선택되면 아예 처음에 쿠폰 버튼이 눌리지 않도록 하자~!
	public CouponPanel(Payment payment, String type) {
		super(payment, type);
		
		this.payment=payment;
		this.type=type;
//		dto = payment.orders_list.get(0);
		
		la_coupon_number = new JLabel("쿠폰번호");
		t_coupon_number = new JTextField(20);
		bt_check = new JButton("쿠폰 번호 확인");
		
		la_title= new JLabel("");
		la_title.setFont(font);
		
		la_content = new JLabel("");
		
		//버튼 리스너
		bt_check.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				if(obj == bt_check){
					if(t_coupon_number.getText().equals("")){
						JOptionPane.showMessageDialog(CouponPanel.this, "쿠폰번호를 입력해주세요.");
					}else{
						String sql="select coupon_status, product_name, product_price from coupon c, product p where p.product_id=c.product_id and coupon_number='"+t_coupon_number.getText()+"'";
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						
						try {
							pstmt=con.prepareStatement(sql);
							rs=pstmt.executeQuery();						

							//issued라면, 상품이름 띄워주고 결제하기 활성화시키기~!
							//해당 상품이랑 비교해서 안되면 안된다고 말해줘라~!
							System.out.println(payment.orders_list.size());
							String product_name = payment.orders_list.get(0).getProduct_name();
							if(rs.next()){
								if(rs.getString("coupon_status").equalsIgnoreCase("issued")){
									if(rs.getString("product_name").equalsIgnoreCase(product_name)){
										la_title.setForeground(Color.BLUE);
										la_title.setText(rs.getString("product_name")+" 1매 쿠폰 사용가능");
										la_content.setText("<html><head>해당 쿠폰을 사용하려면<br>결제완료를 눌러주세요.</head></html>");
										completeFlag();//결제완료 버튼 활성화
									}else{
										la_title.setForeground(Color.RED);
										la_title.setText(rs.getString("product_name")+" 쿠폰입니다.");
										la_content.setText("<html><head>쿠폰을 사용하려면<br>"+rs.getString("product_name")+"를 골라주세요.</head></html>");
									}
								}else{
									la_title.setForeground(Color.RED);
									la_title.setText("사용할 수 없는 쿠폰번호입니다.");
									la_content.setText("쿠폰번호를 다시 한번 확인해주세요.");
								}
							}else{//결과가 없다면, 유효하지 않은 쿠폰번호입니다.
								la_title.setForeground(Color.RED);
								la_title.setText("존재하지 않는 쿠폰번호입니다.");
								la_content.setText("쿠폰번호를 다시 한번 확인해주세요.");
							}
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						
						
					}
				}
			}
		});
		
		//쿠폰패널 설정
		p_center.add(la_coupon_number);
		p_center.add(t_coupon_number);
		p_center.add(bt_check);
		p_center.add(la_title);
		p_center.add(la_content);
		
		
	}
	

	
	
	@Override
	public void setCouponStatus() {
		String sql = "update coupon set coupon_status='used' where coupon_number='"+t_coupon_number.getText()+"'";
		
		PreparedStatement pstmt= null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			JOptionPane.showMessageDialog(this, "쿠폰 사용 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//쿠폰 읽어들이기~!
	public void couponReader(){
		Thread thread = new Thread(){
			public void run() {
				try {
					long data = (long)System.in.read();
					System.out.println(data);
					t_coupon_number.setText(Long.toString(data));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

}
