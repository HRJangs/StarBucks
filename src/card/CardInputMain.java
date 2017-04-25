package card;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.Card;
import json.CardProtocol;

public class CardInputMain extends JFrame implements ActionListener{
	CardCompanyMain main;
	String company_name;
	JPanel p_center, p_south, p1, p2, p3;
	JTextField t_num1, t_num2, t_num3, t_num4;
	JLabel la_company_name, la_num, la_name, la_valid, la_dash1, la_dash2, la_dash3, la_slash;
	JTextField t_name;
	Choice ch_month, ch_year;
	JButton bt_commit, bt_cancel;
	
	public CardInputMain(String company_name, CardCompanyMain main) {
		this.company_name = company_name;
		this.main = main;
		
		p_center = new JPanel();
		p_south = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		t_num1 = new JTextField(5);
		t_num2 = new JTextField(5);
		t_num3 = new JTextField(5);
		t_num4 = new JTextField(5);
		
		la_company_name = new JLabel(company_name);
		la_num = new JLabel("카드 번호 입력");
		la_name = new JLabel("카드 사용자 이름");
		la_valid = new JLabel("카드 유효 기간");
		la_dash1 = new JLabel(" - ");
		la_dash2 = new JLabel(" - ");
		la_dash3 = new JLabel(" - ");
		la_slash = new JLabel(" / ");
		
		t_name = new JTextField(20);
		
		ch_month = new Choice();
		ch_year = new Choice();
		
		bt_commit = new JButton("등록");
		bt_cancel = new JButton("취소");
		
		ch_month.add("월 선택");
		ch_year.add("년도 선택");
		
		for(int i = 1; i <= 12; i++) {
			if(i < 10) {
				ch_month.add("0" + i);
			}
			else {
				ch_month.add(Integer.toString(i));
			}
		}
		
		for(int i =2017; i <= 2030; i++) {
			ch_year.add(Integer.toString(i));
		}
		
		p1.setPreferredSize(new Dimension(450, 30));
		p2.setPreferredSize(new Dimension(450, 30));
		p3.setPreferredSize(new Dimension(450, 30));
		la_company_name.setPreferredSize(new Dimension(450, 20));
		la_company_name.setFont(new Font("굴림", Font.BOLD, 15));
		
		p1.add(la_num);
		p1.add(t_num1);
		p1.add(la_dash1);
		p1.add(t_num2);
		p1.add(la_dash2);
		p1.add(t_num3);
		p1.add(la_dash3);
		p1.add(t_num4);
		
		p2.add(la_name);
		p2.add(t_name);
		
		p3.add(la_valid);
		p3.add(ch_month);
		p3.add(la_slash);
		p3.add(ch_year);
		
		p_center.add(p1);
		p_center.add(p2);
		p_center.add(p3);
		
		p_south.add(bt_commit);
		p_south.add(bt_cancel);
		
		add(la_company_name, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		bt_commit.addActionListener(this);
		bt_cancel.addActionListener(this);
		
		setSize(450, 200);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public void insertCard() {
		StringBuffer cardNumber = new StringBuffer();
		cardNumber.append(t_num1.getText() + "-");
		cardNumber.append(t_num2.getText() + "-");
		cardNumber.append(t_num3.getText() + "-");
		cardNumber.append(t_num4.getText());
		
		String name = t_name.getText();
		String valid = ch_month.getSelectedItem() + "/" + ch_year.getSelectedItem();
		
		Card card = new Card();
		card.setCard_number(cardNumber.toString());
		card.setCard_username(name);
		card.setCard_valid(valid);
		card.setCard_companyname(company_name);
		
		CardThread thread = new CardThread(card, this);
		thread.start();
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == bt_cancel) {
			this.dispose();
		} else if(obj == bt_commit) {
			if(!t_num1.getText().equals("") && !t_num2.getText().equals("") && !t_num3.getText().equals("") && !t_num4.getText().equals("") && !t_name.getText().equals("") && ch_month.getSelectedIndex() != 0 && ch_year.getSelectedIndex() != 0) {
				insertCard();
			}
			else {
				JOptionPane.showMessageDialog(this, "모두 입력해주세요!");
			}
			
		}
	}

}
