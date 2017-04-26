package client;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import dto.Card;
import dto.Product;

public class OrdersPay extends JFrame implements ActionListener, FocusListener, ItemListener{
	JPanel p_center, p_south;
	Choice choice;
	JButton bt_send;
	JPasswordField t_pw;
	ClientMain main;
	Vector<Product> orders_list;
	Vector<Card> card_list = new Vector<Card>();
	
	String card_pw;
	int index;
	
	public OrdersPay(ClientMain main, Vector<Product> orders_list) {
		this.main = main;
		this.orders_list = orders_list;
		
		p_center = new JPanel();
		p_south = new JPanel();
		choice = new Choice();
		bt_send = new JButton("주문 완료");
		//bt_cancel = new JButton("취소");
		t_pw = new JPasswordField("PW입력", 20);
		
		choice.add("카드 선택해주세요.");
		
		t_pw.setEchoChar((char) 0);
		
		bt_send.addActionListener(this);
		/*bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});*/
		t_pw.addFocusListener(this);
		t_pw.setEchoChar((char) 0);
		choice.addItemListener(this);
		
		p_center.add(choice);
		p_center.add(t_pw);
		
		p_south.add(bt_send);
		//p_south.add(bt_cancel);
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(300, 150);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		init();
		
	}
	
	public void init() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from card where member_id = ?";
		System.out.println("로그인한 아디이의 id : " + main.member.getMember_id());
		try {
			pstmt = main.con.prepareStatement(sql);
			
			pstmt.setInt(1, main.member.getMember_id());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Card card = new Card();
				card.setCard_id(rs.getInt("card_id"));
				card.setCard_number(rs.getString("card_number"));
				card.setCard_password(rs.getString("card_password"));
				card.setCard_username(rs.getString("card_username"));
				card.setCard_companyname(rs.getString("card_companyname"));
				card.setCard_valid(rs.getString("card_valid"));
				card.setMember_id(rs.getInt("member_id"));
				
				card_list.add(card);
				
				choice.add(card.getCard_companyname() + " : " + card.getCard_number());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		String pw = t_pw.getText();
		
		System.out.println("사용자 입력 : " + pw);
		if(index != 0) {
			if(pw.equals(card_pw)) {
				ClientThread thread = new ClientThread(main, orders_list, this);
				thread.start();
			}
			else {
				JOptionPane.showMessageDialog(this, "카드 비밀번호를 확인해주세요.");
			}
		}
		else {
				JOptionPane.showMessageDialog(this, "카드 선택을 해주세요!");
		}
		
		
	}

	public void focusGained(FocusEvent e) {
		if (e.getSource() == t_pw) {
			t_pw.setEchoChar('*');
			if (t_pw.getText().equals("PW입력")) {
				t_pw.setText("");
				t_pw.setForeground(Color.BLACK);
			}
		}
	}

	public void focusLost(FocusEvent e) {
		if (e.getSource() == t_pw) {
			if (t_pw.getText().isEmpty()) {
				t_pw.setForeground(Color.GRAY);
				t_pw.setEchoChar((char) 0);
				t_pw.setText("PW입력");
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		index = choice.getSelectedIndex();
		
		if(index != 0) {
			card_pw = card_list.get(index - 1).getCard_password();
			
			System.out.println("선택된 카드 비밀번호 : " + card_pw);
		} 
	}

}

