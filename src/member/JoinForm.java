package member;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import db.DBManager;
import dto.Member;

public class JoinForm extends JPanel implements ActionListener, FocusListener {

	MemberWindow memberWindow;

	JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p_can, p_can2;
	JLabel log, log2, gender_la, phone_la, birth_la, birth_la1;

	JLabel label[] = new JLabel[11]; // Label 배열로 선언

	JTextField id_t, nickname_t, name_t, phone_t2, phone_t3;
	JPasswordField pw_t, pw_t2;
	JButton id_bt, bt_trans, bt_cancel;
	ButtonGroup gender;
	JRadioButton female, male;
	JComboBox<String> cb_phone, ch_year, ch_mon, ch_day;

	Canvas can_top, can_nick;
	BufferedImage image = null;
	BufferedImage image2 = null;

	Member member;

	DBManager manager = DBManager.getInstance();
	Connection con;
	PreparedStatement pstmt; // 객체당 하나
	ResultSet rs;

	int check = 0;

	public JoinForm(MemberWindow memberWindow) {
		this.memberWindow = memberWindow;

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		p_can = new JPanel();
		p_can2 = new JPanel();

		Border border = BorderFactory.createLineBorder(Color.GRAY);

		p6.setBorder(border);
		p7.setBorder(border);
		p8.setBorder(border);

		// title
		p1.setBackground(Color.BLACK);
		log = new JLabel("회원가입");
		log.setForeground(Color.WHITE);
		p1.setLayout(null);
		log.setBounds(10, 0, 100, 55);

		p1.add(log);
		add(p1);

		p1.setBorder(border);

		// p_can
		p_can.setBackground(Color.WHITE);
		log2 = new JLabel("회원정보를 입력해 주세요.", JLabel.CENTER);
		// 이미지 url 얻어오기
		try {
			URL url = new URL("http://211.238.142.120:9090/data/icon_find_sally.png");
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		can_top = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage((Image) image, 0, 5, 140, 130, this);

			}
		};
		can_top.setPreferredSize(new Dimension(140, 130));

		p_can.add(can_top);
		p_can.add(log2);
		add(p_can);

		p_can.setLayout(null);
		can_top.setBounds(135, 10, 400, 130);
		log2.setBounds(0, 150, 400, 17);

		// ID
		id_t = new JTextField("아이디를 입력하세요.", 15);
		p2.setLayout(null);
		id_t.setBounds(0, 0, 210, 40);
		p2.add(id_t);
		id_bt = new JButton("아이디 중복 확인");
		id_bt.setBounds(230, 0, 150, 40);
		p2.add(id_bt);
		add(p2);
		p2.setBackground(Color.WHITE);

		// Password
		pw_t = new JPasswordField("비밀번호를 입력하세요.", 30);
		pw_t2 = new JPasswordField("비밀번호 재확인", 30);
		pw_t.setBounds(0, 0, 180, 40);
		pw_t2.setBounds(200, 0, 180, 40);

		p3.setLayout(null);
		p3.add(pw_t);
		p3.add(pw_t2);
		add(p3);
		p3.setBackground(Color.WHITE);

		// 이름
		name_t = new JTextField("이름을 입력하세요.", 15);
		p4.add(name_t);
		add(p4);
		name_t.setBounds(0, 0, 380, 40);
		p4.setLayout(null);

		// p_can2
		p_can2.setBackground(Color.WHITE);
		// 이미지 url 얻어오기
		try {
			URL url = new URL("http://211.238.142.120" + ":9090/data/nick.png");
			image2 = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		can_nick = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage((Image) image2, 0, 5, 400, 190, this);

			}
		};
		can_nick.setPreferredSize(new Dimension(400, 200));

		p_can2.add(can_nick);
		add(p_can2);

		p_can2.setLayout(null);
		can_nick.setBounds(10, 0, 380, 200);

		// 닉네임
		nickname_t = new JTextField("닉네임을 입력하세요.", 15);
		p5.add(nickname_t);
		add(p5);
		nickname_t.setBounds(0, 0, 380, 40);
		p5.setLayout(null);

		// 성별
		p6.setBackground(Color.WHITE);
		gender_la = new JLabel("성별 : ");
		p6.add(gender_la);
		gender = new ButtonGroup();
		female = new JRadioButton("여자", true);
		male = new JRadioButton("남자", true);
		gender.add(female);
		gender.add(male);
		p6.add(female);
		p6.add(male);
		add(p6);
		p6.setLayout(null);
		gender_la.setBounds(10, 0, 80, 40);
		female.setBounds(90, 5, 100, 30);
		male.setBounds(200, 5, 80, 30);
		female.setBackground(Color.WHITE);
		male.setBackground(Color.WHITE);

		// 연락처
		p7.setBackground(Color.WHITE);
		phone_la = new JLabel("연락처");
		p7.add(phone_la);
		cb_phone = new JComboBox<>();

		cb_phone.addItem("010");
		cb_phone.addItem("011");
		cb_phone.addItem("019");
		cb_phone.addItem("016");
		phone_t2 = new JTextField("", 5);
		phone_t3 = new JTextField("", 5);

		cb_phone.setBounds(90, 5, 100, 30);
		phone_t2.setBounds(200, 5, 80, 30);
		phone_t3.setBounds(290, 5, 80, 30);

		p7.add(cb_phone);
		p7.add(phone_t2);
		p7.add(phone_t3);
		add(p7);
		p7.setLayout(null);
		phone_la.setBounds(10, 0, 50, 40);

		// 생년월일
		p8.setBackground(Color.WHITE);
		ch_year = new JComboBox<>();
		ch_mon = new JComboBox<>();
		ch_day = new JComboBox<>();

		ch_year.addItem("year");
		ch_mon.addItem("mon");
		ch_day.addItem("day");

		for (int i = 0; i < 79; i++) {
			String year = Integer.toString(1937 + i);
			ch_year.addItem(year);
		}
		for (int i = 0; i < 12; i++) {
			String mon;
			if (i < 9) {
				mon = "0" + (i + 1);
			} else {
				mon = Integer.toString(1 + i);
			}
			ch_mon.addItem(mon);
		}
		for (int i = 0; i < 31; i++) {
			String day;
			if (i < 9) {
				day = "0" + (i + 1);
			} else {
				day = Integer.toString(1 + i);
			}
			ch_day.addItem(day);
		}

		ch_year.setBounds(90, 5, 100, 30);
		ch_mon.setBounds(200, 5, 80, 30);
		ch_day.setBounds(290, 5, 80, 30);

		p8.add(ch_year);
		p8.add(ch_mon);
		p8.add(ch_day);

		birth_la = new JLabel("생년월일");
		// d_t.addMouseListener(new MyMouseListener());
		p8.add(birth_la);
		add(p8);
		p8.setLayout(null);
		birth_la.setBounds(10, 0, 80, 40);

		// 전송, 취소버튼
		p9.setBackground(Color.WHITE);
		bt_trans = new JButton("전송");
		bt_trans.setForeground(Color.WHITE);
		bt_trans.setBackground(new Color(0, 102, 51));
		bt_cancel = new JButton("취소");
		bt_cancel.setForeground(Color.WHITE);
		bt_cancel.setBackground(new Color(0, 102, 51));
		p9.add(bt_trans);
		p9.add(bt_cancel);
		add(p9);

		bt_trans.addActionListener(this);
		bt_cancel.addActionListener(this);
		id_bt.addActionListener(this);

		setLayout(null);
		p1.setBounds(0, 0, 400, 55);
		p_can.setBounds(0, 55, 400, 170);
		p2.setBounds(10, 250, 380, 40);
		p3.setBounds(10, 300, 380, 40);
		p4.setBounds(10, 350, 380, 40);
		p_can2.setBounds(0, 400, 400, 190);
		p5.setBounds(10, 600, 380, 40);
		p6.setBounds(10, 650, 380, 40);
		p7.setBounds(10, 700, 380, 40);
		p8.setBounds(10, 750, 380, 40);
		p9.setBounds(10, 800, 380, 40);

		setPreferredSize(new Dimension(400, 850));
		setBackground(Color.WHITE);
		setVisible(false); // 최초에 등장안함

		init();

		con = manager.getConnection();

		// 이벤트 연결
		id_t.addFocusListener(this);
		pw_t.addFocusListener(this);
		pw_t2.addFocusListener(this);
		name_t.addFocusListener(this);
		nickname_t.addFocusListener(this);

		pw_t.setEchoChar((char) 0);
		pw_t2.setEchoChar((char) 0);

	}

	public void trans() {
		member = new Member();
		member.setMember_login_id(id_t.getText());
		member.setMember_login_pw(pw_t.getText());
		member.setMember_name(name_t.getText());
		member.setMember_nickname(nickname_t.getText());

		if (female.isSelected()) {
			member.setMember_gender(female.getText());
		} else if (male.isSelected()) {
			member.setMember_gender(male.getText());

		}
		member.setMember_phone(cb_phone.getSelectedItem() + "-" + phone_t2.getText() + "-" + phone_t3.getText());
		member.setMember_birth(
				ch_year.getSelectedItem() + "/" + ch_mon.getSelectedItem() + "/" + ch_day.getSelectedItem());

		String sql = "insert into member (member_login_id, member_login_pw, member_name, member_nickname, member_gender , member_phone, member_birth, member_coupon) values(?,?,?,?,?,?,?,?)";

		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMember_login_id());
			pstmt.setString(2, member.getMember_login_pw());
			pstmt.setString(3, member.getMember_name());
			pstmt.setString(4, member.getMember_nickname());
			pstmt.setString(5, member.getMember_gender());
			pstmt.setString(6, member.getMember_phone());
			pstmt.setString(7, member.getMember_birth());
			pstmt.setString(8, member.getMember_coupon());

			int result = pstmt.executeUpdate();

			if (result != 0) {
				System.out.println("성공");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void cancel() {
		id_t.setText("");
		pw_t.setText("");
		name_t.setText("");
		nickname_t.setText("");
		cb_phone.setSelectedIndex(0);
		phone_t2.setText("");
		phone_t3.setText("");
		ch_year.setSelectedIndex(0);
		ch_mon.setSelectedIndex(0);
		ch_mon.setSelectedIndex(0);

	}

	public void id_over_ck() {
		boolean flag = true; // db에 중복된 아이디가 있을때
		boolean flag2 = true; // id_t에 값일 있을때

		ArrayList<String> list = new ArrayList<String>();
		String sql = "select member_login_id from member";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			int cnt = 0;
			while (rs.next()) {
				list.add(rs.getString(1));
				cnt++;
				System.out.println(cnt);
				System.out.println(list.get(cnt - 1));
			}

			String id = id_t.getText();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
				if (list.get(i).equals(id)) { // 중복된 아이디가 있을때
					flag = false;
					check = 2;
				} else if (id.equals("아이디를 입력하세요.") || id.equals("")) {
					// 아이디를 입력하지 않았을때
					flag2 = false;
				}
			}
			if (flag == false && flag2 == true) { // 아이디를 입력했는데 중복된 아이디가 있을때
				JOptionPane.showMessageDialog(this, "중복된 아이디가 존재 합니다.");
			} else if (flag == true && flag2 == true) {// 중복된 아이디가 없고 아이디도 입력했을때
				check = 1;
				JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
			} else if (flag2 == false) { // 아이디 입력하지 않고 중복체크버튼 눌렀을때
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean join_valCheck() {
		boolean re = true;
		if (id_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 아이디를 입력해주세요.");
			re = false;
		} else if (check == 0 || check == 2) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 아이디를 중복체크해주세요.");
			re = false;
		} else if (id_t.getText().equals("아이디를 입력하세요.")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 아이디를 입력해주세요.");
			re = false;
		} else if (pw_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 비밀번호를 입력해주세요.");
			re = false;
		} else if (pw_t.getText().equals("비밀번호를 입력하세요.")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 비밀번호를 입력해주세요.");
			re = false;
		} else if (!pw_t2.getText().equals(pw_t.getText())) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 비밀번호를 확인해주세요. ");
			re = false;
		} else if (pw_t2.getText().equals("비밀번호 재확인")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 비밀번호를 확인해주세요. ");
			re = false;
		} else if (name_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 이름을 입력해주세요.");
			re = false;
		} else if (name_t.getText().equals("이름을 입력하세요.")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 이름을 입력해주세요.");
			re = false;
		} else if (nickname_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 닉네임을 입력해주세요.");
			re = false;
		} else if (nickname_t.getText().equals("닉네임을 입력하세요.")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 닉네임을 입력해주세요.");
			re = false;
		} else if (phone_t2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 연락처를 입력해주세요.");
			re = false;
		} else if (phone_t3.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 연락처를 입력해주세요.");
			re = false;
		} else if (ch_year.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 생년월일을 입력해주세요.");
			re = false;
		} else if (ch_mon.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 생년월일을 입력해주세요.");
			re = false;
		} else if (ch_day.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "☆필수☆ 생년월일을 입력해주세요.");
			re = false;
		}

		return re;
	}

	// DB연동 Connection 얻어다 놓기
	private void init() {
		con = manager.getConnection();

	}

	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == id_t) {
			if (id_t.getText().isEmpty()) {
				id_t.setForeground(Color.GRAY);
				id_t.setText("아이디를 입력하세요.");
			}
		} else if (obj == pw_t) {
			if (pw_t.getText().isEmpty()) {
				pw_t.setForeground(Color.GRAY);
				pw_t.setEchoChar((char) 0);
				pw_t.setText("비밀번호를 입력하세요.");
			}
		} else if (obj == pw_t2) {
			if (pw_t2.getText().isEmpty()) {
				pw_t2.setForeground(Color.GRAY);
				pw_t2.setEchoChar((char) 0);
				pw_t2.setText("비밀번호 재확인");
			}
		} else if (obj == name_t) {
			if (name_t.getText().isEmpty()) {
				name_t.setForeground(Color.GRAY);
				name_t.setText("이름을 입력하세요.");
			}
		} else if (obj == nickname_t) {
			if (nickname_t.getText().isEmpty()) {
				nickname_t.setForeground(Color.GRAY);
				nickname_t.setText("닉네임을 입력하세요.");
			}
		}
	}

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == bt_trans) {
			if (join_valCheck())
				trans();
		} else if (obj == bt_cancel) {
			cancel();
			memberWindow.setPage(0);
		} else if (obj == id_bt) {
			id_over_ck();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == id_t) {
			if (id_t.getText().equals("아이디를 입력하세요.")) {
				id_t.setText("");
				id_t.setForeground(Color.BLACK);
			}
		} else if (obj == pw_t) {
			pw_t.setEchoChar('*');
			if (pw_t.getText().equals("비밀번호를 입력하세요.")) {
				pw_t.setText("");
				pw_t.setForeground(Color.BLACK);
			}
		} else if (obj == pw_t2) {
			pw_t2.setEchoChar('*');
			if (pw_t2.getText().equals("비밀번호 재확인")) {
				pw_t2.setText("");
				pw_t2.setForeground(Color.BLACK);
			}
		} else if (obj == name_t) {
			if (name_t.getText().equals("이름을 입력하세요.")) {
				name_t.setText("");
				name_t.setForeground(Color.BLACK);
			}
		} else if (obj == nickname_t) {
			if (nickname_t.getText().equals("닉네임을 입력하세요.")) {
				nickname_t.setText("");
				nickname_t.setForeground(Color.BLACK);
			}
		}
	}

}
