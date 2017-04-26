package client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import board.BoardMain;
import card.CardListMain;
import db.DBManager;
import dto.Member;
import dto.Product;
import member.MemberWindow;

public class ClientMain extends JPanel implements ActionListener {
	// 메인 화면~!
	JPanel p_page = new JPanel(); // 페이지들 담아둔거
	JPanel p_main = new JPanel();
	JPanel p_north = new JPanel();

	JButton bt_home,bt_map;
	Canvas can_logo;
	JLabel la_north = new JLabel("안녕하세요. 스타벅스입니다.", JLabel.CENTER);

	JPanel[] pageList = new JPanel[6];

	// 이미지
	URL[] url = new URL[6];
	String[] path = { "http://localhost:9090/data/logo.png", "http://localhost:9090/data/main_reward_cup_ic.png",
			"http://localhost:9090/data/main_card_ic.png", "http://localhost:9090/data/main_siren_ic.png",
			"http://localhost:9090/data/home.png","http://localhost:9090/data/map4.png" };
	BufferedImage[] image = new BufferedImage[6];

	// 클라이언트 화면

	JPanel p_center,p_map;
	JButton bt_rewards, bt_orders, bt_myPage, bt_event, bt_card;

	// 아예 처음 킬때 회원정보랑 싹 다 가져올거다!
	DBManager manager = DBManager.getInstance();
	Connection con;
	public String login_id;
	public Member member;
	Vector<Product> product_list = new Vector<Product>();
	ClientOrders orders; // 주문창

	public ClientMain(MemberWindow memberWindow) {
		
		this.login_id = memberWindow.id;
		this.setLayout(new BorderLayout());
		p_center = new JPanel();
		p_map = new JPanel();
		p_map.setLayout(new BorderLayout());
		// 이미지 url 얻어오기
		try {
			for (int i = 0; i < path.length; i++) {
				url[i] = new URL(path[i]);
				image[i] = ImageIO.read(url[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		can_logo = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage((Image) image[0], 150, 0, 300, 30, this);

			}
		};
		bt_home = new JButton(new ImageIcon(image[4]));
		bt_map = new JButton(new ImageIcon(image[5]));
		bt_rewards = new JButton("Rewards", new ImageIcon(image[1]));
		bt_event = new JButton("Event");
		bt_myPage = new JButton("My page");
		bt_card = new JButton("Card", new ImageIcon(image[2]));
		bt_orders = new JButton("Siren Order", new ImageIcon(image[3]));

		// 위치
		p_center.setPreferredSize(new Dimension(600, 750));

		p_center.setLayout(null);
		p_center.add(p_north);

		p_center.add(bt_orders);
		p_center.add(bt_myPage);
		p_center.add(bt_event);
		p_center.add(bt_card);
		p_center.add(bt_rewards);

		p_north.setBounds(0, 0, 600, 100);
		bt_rewards.setBounds(0, 100, 600, 300);
		bt_event.setBounds(0, 400, 300, 150);
		bt_myPage.setBounds(300, 400, 300, 150);
		bt_card.setBounds(0, 550, 300, 200);
		bt_orders.setBounds(300, 550, 300, 200);

		p_main.setLayout(new BorderLayout());
		p_page.add(p_center, BorderLayout.CENTER);
		p_main.add(p_page);

		p_north.add(can_logo);
		p_north.add(la_north);

		p_north.setLayout(null);
		can_logo.setBounds(0, 10, 600, 50);
		la_north.setBounds(0, 55, 600, 25);

		add(p_main);
		p_map.add(bt_home);
		p_map.add(bt_map,BorderLayout.EAST);
		
		add(p_map, BorderLayout.NORTH);
		add(p_main, BorderLayout.CENTER);
		
		// 색
		bt_home.setBackground(Color.BLACK);
		p_north.setBackground(Color.BLACK);
		bt_rewards.setBackground(Color.BLACK);
		bt_orders.setBackground(Color.BLACK);
		bt_event.setBackground(Color.BLACK);
		bt_myPage.setBackground(Color.BLACK);
		bt_card.setBackground(Color.BLACK);
		bt_map.setBackground(Color.BLACK);

		bt_home.setForeground(Color.WHITE);
		la_north.setForeground(Color.WHITE);
		bt_rewards.setForeground(Color.WHITE);
		bt_orders.setForeground(Color.WHITE);
		bt_event.setForeground(Color.WHITE);
		bt_myPage.setForeground(Color.WHITE);
		bt_card.setForeground(Color.WHITE);
		bt_map.setForeground(Color.white);
		
		bt_map.setPreferredSize(new Dimension(45, 25));
		// 이미지내 텍스트 위치

		bt_rewards.setHorizontalTextPosition(SwingConstants.CENTER);
		bt_rewards.setVerticalTextPosition(SwingConstants.TOP);
		bt_card.setHorizontalTextPosition(SwingConstants.CENTER);
		bt_card.setVerticalTextPosition(SwingConstants.TOP);
		bt_orders.setHorizontalTextPosition(SwingConstants.CENTER);
		bt_orders.setVerticalTextPosition(SwingConstants.TOP);

		// 버튼선택시 생기는 외곽선 사용 안함
		bt_home.setFocusPainted(false);
		bt_rewards.setFocusPainted(false);
		bt_orders.setFocusPainted(false);
		bt_event.setFocusPainted(false);
		bt_myPage.setFocusPainted(false);
		bt_card.setFocusPainted(false);

		// 리스너 연결
		bt_orders.addActionListener(this);
		bt_event.addActionListener(this);
		bt_myPage.addActionListener(this);
		bt_card.addActionListener(this);
		bt_home.addActionListener(this);
		bt_map.addActionListener(this);
		// 각종 데이터 다 가져오기(상품, 회원)
		getData();

		setPreferredSize(new Dimension(300 * 2, 400 * 2));

		// 모든 페이지 일단 다 만들기~!
		init();
	}

	public void getData() {
		con = manager.getConnection();
		getMember();
		getProduct();
	}

	public void getMember() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from member where member_login_id='" + login_id + "'";

		System.out.println(sql);

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			rs.next();

			member = new Member();
			member.setMember_id(rs.getInt(1));
			member.setMember_login_id(rs.getString(2));
			member.setMember_login_pw(rs.getString(3));
			member.setMember_name(rs.getString(4));
			member.setMember_nickname(rs.getString(5));
			member.setMember_gender(rs.getString(6));
			member.setMember_phone(rs.getString(7));
			member.setMember_birth(rs.getString(8));
			member.setMember_coupon(rs.getString(9));

			System.out.println(member.getMember_nickname());

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
	}

	public void getProduct() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from product";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product dto = new Product();
				dto.setProduct_id(rs.getInt(1));
				dto.setProduct_category_id(rs.getInt(2));
				dto.setProduct_name(rs.getString(3));
				dto.setProduct_price(rs.getInt(4));

				product_list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_orders) {
			setPage(1);
		} else if (obj == bt_event) {
			setPage(2);
		} else if (obj == bt_myPage) {
			setPage(3);
		} else if (obj == bt_card) {
			setPage(4);
		} else if (obj == bt_home) {
			setPage(0);
		} else if(obj== bt_map){
			setPage(5);
		}
	}

	public void init() {
		orders = new ClientOrders(this);
		BoardMain board = new BoardMain();
		ClientEdit clientEdit = new ClientEdit(this);
		CardListMain card = new CardListMain(this);
		ShowMap map = new ShowMap();

		pageList[0] = p_center;
		pageList[1] = orders;
		pageList[2] = board;
		pageList[3] = clientEdit;
		pageList[4] = card;
		pageList[5] = map;
		for (int i = 1; i < 6; i++) {
			// 넣기
			p_page.add(pageList[i]);
			pageList[i].setVisible(false);
		}

	}

	public void setPage(int num) {
		for (int i = 0; i < pageList.length; i++) {
			pageList[i].setVisible(false);
		}
		pageList[num].setVisible(true);

	}

}
