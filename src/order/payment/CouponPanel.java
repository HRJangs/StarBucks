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
	JLabel la_title; //��ȸ ���� Ȯ�� 
	JLabel la_content; //��ȸ ���� Ȯ��
	DBManager manager=DBManager.getInstance();
	Connection con=manager.getConnection();
	Font font = new Font("����", Font.BOLD, 15);
//	Orders dto;


	
	//�������� �г��� �ƿ� ���� ó������ ��ǰ�� 1���ʰ��� ���õǸ� �ƿ� ó���� ���� ��ư�� ������ �ʵ��� ����~!
	public CouponPanel(Payment payment, String type) {
		super(payment, type);
		
		this.payment=payment;
		this.type=type;
//		dto = payment.orders_list.get(0);
		
		la_coupon_number = new JLabel("������ȣ");
		t_coupon_number = new JTextField(20);
		bt_check = new JButton("���� ��ȣ Ȯ��");
		
		la_title= new JLabel("");
		la_title.setFont(font);
		
		la_content = new JLabel("");
		
		//��ư ������
		bt_check.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				if(obj == bt_check){
					if(t_coupon_number.getText().equals("")){
						JOptionPane.showMessageDialog(CouponPanel.this, "������ȣ�� �Է����ּ���.");
					}else{
						String sql="select coupon_status, product_name, product_price from coupon c, product p where p.product_id=c.product_id and coupon_number='"+t_coupon_number.getText()+"'";
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						
						try {
							pstmt=con.prepareStatement(sql);
							rs=pstmt.executeQuery();						

							//issued���, ��ǰ�̸� ����ְ� �����ϱ� Ȱ��ȭ��Ű��~!
							//�ش� ��ǰ�̶� ���ؼ� �ȵǸ� �ȵȴٰ� �������~!
							System.out.println(payment.orders_list.size());
							String product_name = payment.orders_list.get(0).getProduct_name();
							if(rs.next()){
								if(rs.getString("coupon_status").equalsIgnoreCase("issued")){
									if(rs.getString("product_name").equalsIgnoreCase(product_name)){
										la_title.setForeground(Color.BLUE);
										la_title.setText(rs.getString("product_name")+" 1�� ���� ��밡��");
										la_content.setText("<html><head>�ش� ������ ����Ϸ���<br>�����ϷḦ �����ּ���.</head></html>");
										completeFlag();//�����Ϸ� ��ư Ȱ��ȭ
									}else{
										la_title.setForeground(Color.RED);
										la_title.setText(rs.getString("product_name")+" �����Դϴ�.");
										la_content.setText("<html><head>������ ����Ϸ���<br>"+rs.getString("product_name")+"�� ����ּ���.</head></html>");
									}
								}else{
									la_title.setForeground(Color.RED);
									la_title.setText("����� �� ���� ������ȣ�Դϴ�.");
									la_content.setText("������ȣ�� �ٽ� �ѹ� Ȯ�����ּ���.");
								}
							}else{//����� ���ٸ�, ��ȿ���� ���� ������ȣ�Դϴ�.
								la_title.setForeground(Color.RED);
								la_title.setText("�������� �ʴ� ������ȣ�Դϴ�.");
								la_content.setText("������ȣ�� �ٽ� �ѹ� Ȯ�����ּ���.");
							}
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						
						
					}
				}
			}
		});
		
		//�����г� ����
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
			
			JOptionPane.showMessageDialog(this, "���� ��� �Ϸ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//���� �о���̱�~!
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
