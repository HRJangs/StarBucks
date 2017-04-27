package order.payment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.Emp;
import dto.Orders;

public class Receipt extends JFrame{
	Image image;
	JLabel label;
	Canvas can;
	JPanel panel;

	public Receipt(PaymentPanel paymentPanel, String type) {
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		this.setTitle("Receipt");
		try {
			image = ImageIO.read(new URL("http://211.238.142.120:9090/data/black_logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		String str_end = new SimpleDateFormat("yyyy-(MM+1)-dd").format(date);
		
		Vector<Orders> list = paymentPanel.payment.orders_list;
		
		StringBuffer sb=new StringBuffer();
		sb.append("<html>");
		sb.append("<head><span>�������� &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;���� ������ �����23</span>");
		sb.append("<div> ��ǥ: ����ȣ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;201-81-21515</div>");
		sb.append("<div>[POS 00] &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+str+"</div><br><hr>");
		sb.append("<p>����(A-086"+list.get(0).getOrders_client_id()+")<br></p><hr>");
		
		int total=0;
		for(int i=0; i<list.size();i++){
			sb.append("<div>"+list.get(i).getProduct_name()+" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
			sb.append(CommaUtil.toNumFormat(list.get(i).getPrice())+" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 1");
			sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; "+CommaUtil.toNumFormat(list.get(i).getPrice())+"</div><br>");
			total +=list.get(i).getPrice();
		}
		
		Emp emp = paymentPanel.payment.emp;//�ֹ� ���� ���� ����
		
		sb.append("<hr><p>Total "+CommaUtil.toNumFormat(total)+"<br></p><p>�������: "+type+"<br></p>");
		sb.append("<p>�ֹ���ȣ: "+list.get(0).getOrders_client_id()+" ȣ��� �ֹ���ȣ�� Ȯ�����ּ���.^^ <br></p><p>Partner name: ["+emp.getEmp_job()+" "+emp.getEmp_id()+"]"+emp.getEmp_name()+"<br></p>");
		sb.append("<p>�������� ������ �����Ͻ� ���忡��<br>��ü���/������� �����ϸ�, �ݵ��<br>�����ߴ� ī�带 �����ϼž� �մϴ�.<br>");
		sb.append("(���� ���ɱⰣ: "+str_end+"����)<br>www.istarbucks.co.kr<br></p><hr>");
		sb.append("<p>Wi-Fi SSID: KT_starbucks/ PW:����<br>���Ե� ��Ż翡 ������� ���� ��밡��<br>");
		sb.append("KT�� ���� ������� �� ���� �� ��밡��<br></p><hr><p>��Ÿ���� 1,000ȣ�� ���� ���!<br>");
		sb.append("��ϵ� ��Ÿ���� ī��� ��� ��ǰ<br>���Ž� ���� �� 1���� ���� �߰� ������<br>�帳�ϴ�.</p>");
		sb.append("</head></html>");
				
		label = new JLabel(sb.toString());
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createEmptyBorder());
		
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
			}
		};
		
		can.setPreferredSize(new Dimension(300, 80));
		
		panel.setPreferredSize(new Dimension(320, 500));
		
		panel.add(can);
		panel.add(label);

		
		
		//���� ī�� ������ �ִٸ�! ���ε� �ٿ��� ������!
		if(type.equals("credit")){
			JLabel la_sign = new JLabel("�����");
			Canvas can_credit_sign=new Canvas(){
				public void paint(Graphics g) {
					g.setColor(Color.WHITE);
					g.fillRect(0,0,250,150);
					g.setColor(Color.BLACK);
					for(int i=1;i<paymentPanel.point_list.size();i++){
						g.drawLine(paymentPanel.point_list.get(i-1).x, paymentPanel.point_list.get(i-1).y, paymentPanel.point_list.get(i).x, paymentPanel.point_list.get(i).y);
					}
				}
			};
			can_credit_sign.setBackground(Color.WHITE);
			can_credit_sign.setPreferredSize(new Dimension(250, 150));
			panel.add(la_sign);
			panel.add(can_credit_sign);
			
		}
		
		add(panel);
		
		setSize(320, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		
	}
	
	
}


//order_client_id
//
//pstmt.setInt(1, payment.orders_list.get(i).getProduct_id());
////pstmt.setTimestamp(2, "current_timestamp()");
//pstmt.setInt(2, payment.orders_list.get(i).getOrders_emp_id());
//pstmt.setInt(3, payment.orders_list.get(i).getOrders_client_id());
//pstmt.setString(4, "ready");
//pstmt.setString(5, orders_payment_type);
//pstmt.setString(6, "offline");
