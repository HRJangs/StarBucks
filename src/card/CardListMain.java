package card;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.ClientMain;
import db.DBManager;
import dto.Card;

public class CardListMain extends JPanel implements ActionListener {
	DBManager manager = DBManager.getInstance();
	Connection con;
	Vector<Card> card_list = new Vector<Card>();
	Vector<JPanel> panel_list = new Vector<JPanel>();
	JPanel p_north, p_center;
	JButton bt;
	JLabel la_member;
	// String login_id;
	JScrollPane scroll;
	ClientMain clientMain;
	BufferedImage image = null;

	public CardListMain(ClientMain clientMain) {
		setLayout(new BorderLayout());
		this.clientMain = clientMain;
		con = manager.getConnection();

		p_north = new JPanel();
		p_center = new JPanel();
		la_member = new JLabel(clientMain.login_id + "님 안녕하세요.");
		bt = new JButton("카드 등록");
		scroll = new JScrollPane(p_center);

		la_member.setPreferredSize(new Dimension(300, 50));
		la_member.setFont(new Font("돋움", Font.ITALIC, 30));

		p_center.setPreferredSize(new Dimension(600, 600));

		p_north.add(la_member);
		p_north.add(bt);

		add(p_north, BorderLayout.NORTH);
		add(scroll);

		// 색
		p_center.setBackground(Color.WHITE);
		p_north.setBackground(new Color(35, 94, 160));
		la_member.setForeground(Color.WHITE);
		bt.setBackground(Color.WHITE);

		bt.setFocusPainted(false);

		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		bt.addActionListener(this);

		setPreferredSize(new Dimension(600, 800));
		setVisible(true);

		getList();
	}

	public void getList() {
		card_list.removeAll(card_list);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from card where member_id = " + clientMain.member.getMember_id();

		try {
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Card dto = new Card();
				dto.setCard_id(rs.getInt("card_id"));
				dto.setMember_id(rs.getInt("member_id"));
				dto.setCard_number(rs.getString("card_number"));
				dto.setCard_username(rs.getString("card_username"));
				dto.setCard_valid(rs.getString("card_valid"));
				dto.setCard_companyname(rs.getString("card_companyname"));

				card_list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * 패널안에 카드 회사, 유저네임 출력 --> 카드를 누르면 기존의 창에서 화면 전환 setVisible로? 카드 넘버,
		 * 유저네임, 유효기간, 회사이름다 나오는 거 패널 하나(.java로) 만들기
		 */
		addPanel();
	}

	public void addPanel() {
		panel_list.removeAll(panel_list);
		p_center.removeAll();

		// 이미지 url 얻어오기
		try {
			URL url = new URL("http://localhost:9090/data/creaditcard.png");
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < card_list.size(); i++) {
			JPanel panel = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage((Image) image, 0, 20, 260, 160, this);

					setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
					super.paint(g);
				}
			};

			panel.setLayout(null);
			JLabel la_num = new JLabel(card_list.get(i).getCard_number());
			JLabel la_user = new JLabel(card_list.get(i).getCard_username());
			JLabel la_company = new JLabel(card_list.get(i).getCard_companyname());
			la_num.setBounds(10, 130, 250, 30);
			la_user.setBounds(20, 100, 250, 30);
			la_company.setBounds(20, 10, 250, 50);
			la_num.setFont(new Font("굴림", Font.BOLD, 13));
			la_user.setFont(new Font("굴림", Font.BOLD, 13));
			la_company.setFont(new Font("굴림", Font.BOLD, 13));

			panel.add(la_company);
			panel.add(la_num);
			panel.add(la_user);

			panel.setPreferredSize(new Dimension(280, 200));
			panel.updateUI();

			p_center.add(panel);
			p_center.updateUI();
			
			panel.addMouseListener(new CardMouseAdapter(card_list.get(i), this));

		}
	}

	public void actionPerformed(ActionEvent e) {
		new CardCompanyMain(this);
	}

}
