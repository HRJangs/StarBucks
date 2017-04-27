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
		sb.append("<head><span>서강대점 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;서울 마포구 백범로23</span>");
		sb.append("<div> 대표: 민진호 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;201-81-21515</div>");
		sb.append("<div>[POS 00] &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+str+"</div><br><hr>");
		sb.append("<p>고객명(A-086"+list.get(0).getOrders_client_id()+")<br></p><hr>");
		
		int total=0;
		for(int i=0; i<list.size();i++){
			sb.append("<div>"+list.get(i).getProduct_name()+" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
			sb.append(CommaUtil.toNumFormat(list.get(i).getPrice())+" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 1");
			sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; "+CommaUtil.toNumFormat(list.get(i).getPrice())+"</div><br>");
			total +=list.get(i).getPrice();
		}
		
		Emp emp = paymentPanel.payment.emp;//주문 받은 직원 정보
		
		sb.append("<hr><p>Total "+CommaUtil.toNumFormat(total)+"<br></p><p>결제방법: "+type+"<br></p>");
		sb.append("<p>주문번호: "+list.get(0).getOrders_client_id()+" 호출시 주문번호를 확인해주세요.^^ <br></p><p>Partner name: ["+emp.getEmp_job()+" "+emp.getEmp_id()+"]"+emp.getEmp_name()+"<br></p>");
		sb.append("<p>결제수단 변경은 구입하신 매장에서<br>전체취소/재결제로 가능하며, 반드시<br>결제했던 카드를 소지하셔야 합니다.<br>");
		sb.append("(변경 가능기간: "+str_end+"까지)<br>www.istarbucks.co.kr<br></p><hr>");
		sb.append("<p>Wi-Fi SSID: KT_starbucks/ PW:없음<br>가입된 통신사에 관계없이 전고객 사용가능<br>");
		sb.append("KT외 고객은 약관동의 및 인증 후 사용가능<br></p><hr><p>스타벅스 1,000호점 오픈 기념!<br>");
		sb.append("등록된 스타벅스 카드로 모든 상품<br>구매시 만원 당 1개의 별을 추가 적립해<br>드립니다.</p>");
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

		
		
		//만약 카드 싸인이 있다면! 싸인도 붙여서 보내라!
		if(type.equals("credit")){
			JLabel la_sign = new JLabel("서명란");
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
