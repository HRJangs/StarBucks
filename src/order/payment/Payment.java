package order.payment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.Emp;
import dto.Orders;


public class Payment extends JFrame implements ActionListener{
	JPanel p_menu;
	PaymentPanel p_cash, p_credit;
	CouponPanel p_coupon;
	JButton bt_cash, bt_credit, bt_coupon;
	JLabel la_cash_in, la_cash_cost, la_cash_out;
	JLabel la_credit_cost, la_credit_number, la_credit_company;

	JTextField t_cash_in, t_cash_cost, t_cash_out;
	JTextField t_credit_cost, t_credit_number, t_credit_company;

	Vector<Orders> orders_list = new Vector<Orders>(); //주문정보 dto꾸러미
	int price;//결제 총 금액
	JButton bt_cardRead;//카드정보 읽어들이기
	Emp emp;//주문 받은 직원 정보
	
	//캔버스
	Canvas can_credit;
	JButton bt_erase;
	Vector<Point> point_list = new Vector<Point>();
	
	
	
	public Payment(Vector<Orders> orders_list, int price, Emp emp) {
		
		setTitle("결제창");
		
		this.emp=emp;
		
		//일단 리스트는 가져오는데 0으로 처리되니깐 일단 이걸 막아놔야한다~!
		for(int i=0; i<orders_list.size();i++){
			this.orders_list.add(orders_list.get(i));
		}
		this.price=price;//총액
		setLayout(new FlowLayout());
		
		//각 상황별 패널들
		p_menu = new JPanel();
		p_cash = new PaymentPanel(this, "cash");
		p_credit = new PaymentPanel(this, "credit");
		p_coupon = new CouponPanel(this, "coupon");
		
		//각 버튼들
		bt_cash = new JButton("현금");
		bt_credit = new JButton("카드");
		bt_coupon = new JButton("쿠폰");
		
		//쿠폰 버튼은 상품이 1개만 선택되었을때만 눌리도록 해주자~!
		if(orders_list.size()!=1){
			bt_coupon.setEnabled(false);
		}else{
			bt_coupon.setEnabled(true);
		}
		
		bt_cardRead = new JButton("카드긁기");
		
		//각 라벨들
		la_cash_in = new JLabel("받은 돈");
		la_cash_cost = new JLabel("결제금액");
		la_cash_out = new JLabel("거스름 돈");
		
		la_credit_cost = new JLabel("결제금액");
		la_credit_number = new JLabel("카드번호");
		la_credit_company = new JLabel("카드사");
		
		
		
		//각 텍스트필드
		t_cash_in = new JTextField(18);
		t_cash_cost = new JTextField(Integer.toString(price), 18);
		t_cash_out = new JTextField(18);

		t_credit_cost = new JTextField(Integer.toString(price), 18);
		t_credit_number = new JTextField(18);
		t_credit_company = new JTextField(18);
		
		
		
				
		//결제금액은 변경 금지
		t_cash_cost.setEditable(false);
		t_cash_out.setEditable(false);
		t_credit_cost.setEditable(false);
		
		//패널들 크기 확정
		p_menu.setPreferredSize(new Dimension(300,350));
		p_cash.setPreferredSize(new Dimension(300,350));
		p_credit.setPreferredSize(new Dimension(300,350));
		p_coupon.setPreferredSize(new Dimension(300,350));
		
		//메뉴패널 설정
		p_menu.setLayout(new GridLayout(3, 1));
		p_menu.add(bt_cash);
		p_menu.add(bt_credit);
		p_menu.add(bt_coupon);
		
		//현금패널 설정
		p_cash.p_center.add(la_cash_in);
		p_cash.p_center.add(t_cash_in);
		p_cash.p_center.add(la_cash_cost);
		p_cash.p_center.add(t_cash_cost);
		p_cash.p_center.add(la_cash_out);
		p_cash.p_center.add(t_cash_out);
		
		//카드패널 설정
		p_credit.p_center.add(la_credit_cost);
		p_credit.p_center.add(t_credit_cost);
		p_credit.p_center.add(la_credit_number);
		p_credit.p_center.add(t_credit_number);
		p_credit.p_center.add(la_credit_company);
		p_credit.p_center.add(t_credit_company);
		p_credit.p_center.add(bt_cardRead);
		

		
		//색깔설정
		p_menu.setBackground(Color.BLACK);
		p_cash.setBackground(Color.CYAN);
		p_credit.setBackground(Color.PINK);
		p_coupon.setBackground(Color.RED);
		
		add(p_menu);
		add(p_cash);
		add(p_credit);
		add(p_coupon);
		
		//각 패널 노출 여부
		p_menu.setVisible(true);
		p_cash.setVisible(false);
		p_credit.setVisible(false);
		p_coupon.setVisible(false);
		
		
		//버튼에 리스너 부착
		bt_cash.addActionListener(this);
		bt_credit.addActionListener(this);
		bt_coupon.addActionListener(this);
		bt_cardRead.addActionListener(this);
		
		//텍스트필드에 리스너 부착
		t_cash_in.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					int in=Integer.parseInt(t_cash_in.getText());
					int cost=Integer.parseInt(t_cash_cost.getText());
					t_cash_out.setText(Integer.toString(in-cost));
					t_cash_out.updateUI();
					p_cash.completeFlag();//현금 엔터 눌러야 결제완료 되게 하기~!
				}
			}
		});
		
		setSize(300,400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==bt_cash){
			p_menu.setVisible(false);
			p_cash.setVisible(true);
		}else if(obj==bt_credit){
			p_menu.setVisible(false);
			p_credit.setVisible(true);
		}else if(obj==bt_coupon){
			p_menu.setVisible(false);
			p_coupon.setVisible(true);
		}else if(obj==bt_cardRead){
			cardReader();
		}
	}
	
	public void cardReader(){
		String cardNumber = "6258-****-****6048";
		String cardCompany = "국민카드";
		
		t_credit_number.setText(cardNumber);
		t_credit_company.setText(cardCompany);
		
		cardSign();
				
	}
			
	public void cardSign(){	
		can_credit = new Canvas(){
			public void paint(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0,0,250,150);
				g.setColor(Color.BLACK);
				for(int i=1;i<point_list.size();i++){
					if(point_list.get(i)!=null){
						g.drawLine(point_list.get(i-1).x, point_list.get(i-1).y, point_list.get(i).x, point_list.get(i).y);
					}
				}
			}
		};
		
		can_credit.setBackground(Color.WHITE);
		can_credit.setPreferredSize(new Dimension(250, 150));
		
		can_credit.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				point_list.add(e.getPoint());
				can_credit.repaint();
			}
		});
		
		can_credit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				p_credit.completeFlag();//싸인 들어와야 결제완료 버튼 눌러지게 하기~!
			}
		});
		
		bt_erase = new JButton("서명 취소");
		bt_erase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				point_list.removeAllElements();
				can_credit.repaint();
				p_credit.bt_payment_complete.setEnabled(false); //싸인 지우면 결제완료 안눌리게하기~!
			}
		});
		
		p_credit.p_center.add(can_credit);
		p_credit.p_center.add(bt_erase);
				
		p_credit.p_center.updateUI();
	}
	
	

	
	
	
//	public static void main(String[] args) {
//		Orders dto = new Orders();
//		new Payment(dto, 4900);
//
//	}

}
