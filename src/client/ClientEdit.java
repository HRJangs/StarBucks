package client;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import card.CardThread;
import dto.Card;
import dto.Member;

public class ClientEdit extends JPanel implements FocusListener,ActionListener{
	JTextArea t_coupon;
	JTextField t_id,t_name,t_nick,t_phone1,t_phone2;
	//Choice ch_phone,ch_year,ch_mon,ch_day; 
	JComboBox<String> cb_phone,ch_year,ch_mon,ch_day;
	JPasswordField t_pw,t_re_pw;
	JPanel p_img,p_container,p_content;
	Canvas can;
	BufferedImage img;
	ImageIO imageIO;
	URL url;
	String id;
	Member member;
	JButton bt_reg;
	ClientMain clientMain;
	
	public ClientEdit(ClientMain clientMain) {
		setLayout(new BorderLayout());
		this.member=clientMain.member;
		this.clientMain = clientMain;
		p_content = new JPanel();
		p_img =new JPanel();
		p_container = new JPanel();
		t_id = new JTextField("아이디",30);
		t_pw = new JPasswordField("비밀번호",30);
		t_re_pw = new JPasswordField("비밀번호 재확인",30);
		t_name= new JTextField("이름",30);
		t_nick = new JTextField("닉네임",30);
		t_coupon = new JTextArea(4, 29);
		t_phone1 = new JTextField(10);
		t_phone2 = new JTextField(10);
		ch_year = new JComboBox<>();
		ch_mon = new JComboBox<>();
		ch_day = new JComboBox<>();
		bt_reg = new JButton("수정");
		cb_phone=  new JComboBox<>();
		//cb_phone.addItem(item);
		
		try {
			url = new URL("http://211.238.142.120:9090/data/sb_join.png");
			img = imageIO.read(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0,500,250, this);
			}
		};
		t_id.setEditable(false);
		t_coupon.setEditable(false);

		t_id.setText(member.getMember_login_id());
		t_pw.setEchoChar('*');
		//t_pw.setText(member.getMember_login_pw());
	
		t_name.setText(member.getMember_name());
		t_nick.setText(member.getMember_nickname());
		t_coupon.setText(member.getMember_coupon());
		
		p_container.setBounds(100,100,300,630);
		p_content.setBackground(Color.white);
		cb_phone.addItem("010");
		cb_phone.addItem("011");
		cb_phone.addItem("019");
		cb_phone.addItem("016");
		
		
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		t_coupon.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		for(int i=0;i<79;i++){
			String year = Integer.toString(1937+i);
			ch_year.addItem(year);
		}
		for(int i=0;i<12;i++){
			String mon = Integer.toString(1+i);
			ch_mon.addItem(mon);
		}
		for(int i=0;i<31;i++){
			String day = Integer.toString(1+i);
			ch_day.addItem(day);
		}
		//크기 지정
		p_container.setPreferredSize(new Dimension(400, 400));
		t_id.setPreferredSize(new Dimension(150, 30));
		t_pw.setPreferredSize(new Dimension(150,30));
		t_re_pw.setPreferredSize(new Dimension(150, 30));
		t_name.setPreferredSize(new Dimension(150, 30));
		t_nick.setPreferredSize(new Dimension(150, 30));
		t_phone1.setPreferredSize(new Dimension(80, 30));
		t_phone2.setPreferredSize(new Dimension(80, 30));
		ch_year.setPreferredSize(new Dimension(109, 30));
		ch_mon.setPreferredSize(new Dimension(109, 30));
		ch_day.setPreferredSize(new Dimension(109, 30));
		cb_phone.setPreferredSize(new Dimension(97, 30));
		can.setPreferredSize(new Dimension(500, 250));
		//이벤트 연결
		t_id.addFocusListener(this);
		t_pw.addFocusListener(this);
		t_re_pw.addFocusListener(this);
		t_name.addFocusListener(this);
		t_nick.addFocusListener(this);
		
		t_pw.setEchoChar((char)0);
		t_re_pw.setEchoChar((char)0);
		//레이아웃
	
		//색상 설정
		p_container.setBackground(Color.white);
		p_img.setBackground(Color.white);
		//부착
		p_img.add(can);
		p_container.add(t_id);
		p_container.add(t_pw);
		p_container.add(t_re_pw);
		p_container.add(t_name);
		p_container.add(t_nick);
		p_container.add(cb_phone);
		p_container.add(t_phone1);
		p_container.add(t_phone2);
		p_container.add(ch_year);
		p_container.add(ch_mon);
		p_container.add(ch_day);
		p_container.add(t_coupon);
		p_container.add(bt_reg);
		bt_reg.addActionListener(this);
		
		add(p_img,BorderLayout.NORTH);
		p_content.add(p_container);
		add(p_content);
		setVisible(true);
		setPreferredSize(new Dimension(300*2, 400*2));
	}
	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		if(obj==t_id){
		   if (t_id.getText().equals("아이디")) {
	        	t_id.setText("");
	        	t_id.setForeground(Color.BLACK);
	        }
		}else if(obj==t_pw){
			t_pw.setEchoChar('*');
		  if (t_pw.getText().equals("비밀번호")) {
	        	t_pw.setText("");
	        	t_pw.setForeground(Color.BLACK);
	        }
		}else if(obj==t_re_pw){
			t_re_pw.setEchoChar('*');
			if (t_re_pw.getText().equals("비밀번호 재확인")) {
	        	t_re_pw.setText("");
	        	t_re_pw.setForeground(Color.BLACK);
	        }	
		}else if(obj==t_name){
			if (t_name.getText().equals("이름")) {
				t_name.setText("");
				t_name.setForeground(Color.BLACK);
	        }	
		}
		else if(obj==t_nick){
			if (t_nick.getText().equals("닉네임")) {
				t_nick.setText("");
				t_nick.setForeground(Color.BLACK);
	        }	
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if(obj==t_id){
		  if (t_id.getText().isEmpty()) {
	        	t_id.setForeground(Color.GRAY);
	        	t_id.setText("아이디");
	        }
		}else if(obj==t_pw){
			if (t_pw.getText().isEmpty()) {
	        	t_pw.setForeground(Color.GRAY);
	        	t_pw.setEchoChar((char)0);
	        	t_pw.setText("비밀번호");
	        }
		}else if(obj==t_re_pw){
			if (t_re_pw.getText().isEmpty()) {
	        	t_re_pw.setForeground(Color.GRAY);
	        	t_re_pw.setEchoChar((char)0);
	        	t_re_pw.setText("비밀번호 재확인");
	        }
		}else if(obj==t_name){
			if (t_name.getText().isEmpty()) {
				t_name.setForeground(Color.GRAY);
				t_name.setText("이름");
	        }
		}
		else if(obj==t_nick){
			if (t_nick.getText().isEmpty()) {
				t_nick.setForeground(Color.GRAY);
				t_nick.setText("닉네임");
	        }
		}
	}
	public void editMember(){
		
		Member member = new Member();
		String pw = new String(t_pw.getPassword());
		member.setMember_login_id(t_id.getText());
		member.setMember_login_pw(pw);
		member.setMember_name(t_name.getText());
		member.setMember_nickname(t_nick.getText());
		member.setMember_phone((String)(cb_phone.getSelectedItem())+t_phone1.getText()+t_phone2.getText());
		member.setMember_birth((String)(ch_year.getSelectedItem())+(String)(ch_mon.getSelectedItem())+(String)(ch_day.getSelectedItem()));
		
		MemberThread thread = new MemberThread(member, this);
		thread.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_reg){
			System.out.println(cb_phone.getSelectedItem());

			if(t_pw.getText().equals("비밀번호")||t_re_pw.getText().equals("비밀번호 재확인")){
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
				System.out.println("dd");
			}else if(t_name.getText().equals("이름")||t_name.getText().equals("")){
				JOptionPane.showMessageDialog(this, "이름을 입력해주세요");
			}else if(t_phone1.getText().equals("")||t_phone2.getText().equals("")){
				JOptionPane.showMessageDialog(this, "핸드폰번호를 입력해주세요");
			}else if(t_nick.getText().equals("")){
				JOptionPane.showMessageDialog(this, "닉네임을 입력해주세요");
			}else{
				System.out.println("으잉");
				editMember();
				}
			}
	}
}