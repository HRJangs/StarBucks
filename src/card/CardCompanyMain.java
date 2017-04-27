package card;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardCompanyMain extends JFrame implements ActionListener {
	CardListMain main;
	JPanel p_north, p_center, p_south;
	JLabel la;
	JButton bt_cancel;
	JButton[] bt_card = new JButton[10];

	public CardCompanyMain(CardListMain main) {
		this.main = main;
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la = new JLabel("카드 선택");
		bt_cancel = new JButton("취소");
		bt_card[0] = new JButton("국민카드");
		bt_card[1] = new JButton("롯데카드");
		bt_card[2] = new JButton("신한카드");
		bt_card[3] = new JButton("삼성카드");
		bt_card[4] = new JButton("우리카드");
		bt_card[5] = new JButton("우체국카드");
		bt_card[6] = new JButton("현대카드");
		bt_card[7] = new JButton("하나카드");
		bt_card[8] = new JButton("NH농협카드");
		bt_card[9] = new JButton("BC카드");

		la.setPreferredSize(new Dimension(250, 50));
		la.setFont(new Font("돋움", Font.BOLD, 20));

		p_north.add(la);
		p_south.add(bt_cancel);

		for (int i = 0; i < bt_card.length; i++) {
			p_center.add(bt_card[i]);
			bt_card[i].addActionListener(this);
			bt_card[i].setBackground(new Color(236, 236, 236));
			bt_card[i].setForeground(new Color(138, 138, 138));
			bt_card[i].setFocusPainted(false);
		}

		//

		p_north.setBackground(new Color(35, 94, 160));
		la.setForeground(Color.WHITE);

		p_center.setBackground(Color.WHITE);

		p_south.setBackground(Color.WHITE);
		bt_cancel.setBackground(new Color(35, 94, 160));
		bt_cancel.setForeground(Color.WHITE);
		bt_cancel.setFocusPainted(false);

		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);

		bt_cancel.addActionListener(this);

		setSize(400, 300);
		setVisible(true);
		setLocationRelativeTo(main);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {
		JButton bt_select = (JButton) e.getSource();

		if (bt_select == bt_cancel) {
			this.dispose();
		} else {
			System.out.println(bt_select.getText());
			new CardInputMain(bt_select.getText(), this);
		}

	}

}
