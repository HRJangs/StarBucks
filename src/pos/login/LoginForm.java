//로그인 화면을 담당할 클래스 정의
package pos.login;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ClientMain;
import db.DBManager;
import order.list.CallMain;
import order.list.OrdersListMain;
import order.main.OrderMain;
import pos.AdminPage;

public class LoginForm extends JPanel implements ActionListener ,FocusListener{
	PosWindow posWindow;
	JPanel container;
	JPanel p_north;
	JPanel p_center;
	JPanel p_south; // 남쪽에 버튼이 들어갈 예정
	JLabel la_north, la_center1, la_center2;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login;
	Canvas can_center;
	Canvas can_north;
	BufferedImage image = null;
	BufferedImage image2 = null;
	public LoginForm(PosWindow posWindow) {
		this.posWindow = posWindow;
		container = new JPanel();

		// north
		p_north = new JPanel();
		p_north.setBackground(Color.white);
		la_north = new JLabel("직원 로그인");
		la_north.setForeground(Color.black);

		// center
		p_center = new JPanel();
		p_center.setBackground(Color.WHITE);
		//la_center1 = new JLabel("안녕하세요. 스타벅스입니다.", JLabel.CENTER);
		//la_center1.setForeground(new Color(156, 136, 86));
		//la_center1.setFont(new Font("돋움", Font.BOLD, 20));

		//la_center2 = new JLabel("<html>직원아이디로 로그인 해주세요<html>",
			//	JLabel.CENTER);
		//la_center2.setFont(new Font("돋움", Font.PLAIN, 13));
		
		// 이미지 url 얻어오기
		try {
			URL url = new URL("http://211.238.142.120:9090/data/pos_login.jpg");
			image = ImageIO.read(url);
			URL url2 = new URL("http://211.238.142.120:9090/data/pos_logo.PNG");
			image2 = ImageIO.read(url2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		can_north = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage((Image) image2, 0, 0,400, 50, this);

			}
		};
		can_center = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage((Image) image, 0, 0,400, 300, this);

			}
		};

		// can_center.setPreferredSize(new Dimension(135, 135));

		t_id = new JTextField("ID입력", 20);
		t_id.setPreferredSize(new Dimension(380, 35));
		t_pw = new JPasswordField("PW입력", 20);
		t_pw.setEchoChar((char) 0);
		t_pw.setPreferredSize(new Dimension(380, 35));

		// south
		p_south = new JPanel();
		p_south.setBackground(Color.WHITE);

		bt_login = new JButton("로그인하기");
		bt_login.setForeground(Color.WHITE);
		bt_login.setBackground(new Color(123, 109, 100));
		
	

		// add
		p_north.add(can_north);
		
		p_center.add(can_center);
		//p_center.add(la_center1);
		//p_center.add(la_center2);
		p_center.add(t_id);
		p_center.add(t_pw);

		p_south.add(bt_login);
	

		add(container);

		// 각 component 위치지정
		container.setLayout(new BorderLayout());
		container.add(p_north, BorderLayout.NORTH);
		container.add(p_center, BorderLayout.CENTER);
		container.add(p_south, BorderLayout.SOUTH);

		p_north.setPreferredSize(new Dimension(400, 55));
		p_center.setPreferredSize(new Dimension(400, 465));
		p_south.setPreferredSize(new Dimension(400, 130));

		p_north.setLayout(null);
		la_north.setBounds(10, 0, 100, 55);

		p_center.setLayout(null);
		can_center.setBounds(0, 0, 400, 300);
		can_north.setBounds(0, 0, 400, 50);
		//la_center1.setBounds(0, 200, 400, 30);
		//la_center2.setBounds(0, 240, 400, 50);
		t_id.setBounds(10, 340, 380, 40);
		t_pw.setBounds(10, 390, 380, 40);

		p_south.setLayout(null);
		bt_login.setBounds(10, 0, 380, 40);
	

		// 버튼 연결
		bt_login.addActionListener(this);
	

		setPreferredSize(new Dimension(400, 600));

		// 이벤트 연결

		t_id.addFocusListener(this);
		t_pw.addFocusListener(this);
		t_pw.setEchoChar((char) 0);
	}

	public void joinCheck() {
		posWindow.setPage(1);
	}

	// 1.아이디가 존재하는 아이디인지 확인하기
	// db에 모든 아이디 값을 가져와서 지금 입력된 아이디와 비교

	// 2.존재하면 해당 아이디와 비번텍스트필드에 있는거랑 db의 값이 같은지 확인

	// 존재하면 해당 아이디값에 비밀번호를 확인해서 아이디와 비번이 둘다 일치하는지 확인하고 넘기기
	// 3.맞으면 넘기고 아니면 틀렸다고 오류메세지

	public void loginCheck() {
		if (t_id.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요");
			t_id.requestFocus();
			return;
		}

		char[] ch = t_pw.getPassword();
		String pass = new String(ch);
		if (pass.length() == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 정확히 입력해주세요");
			t_pw.requestFocus();
			return;
		}

		DBManager manager = DBManager.getInstance();
		Connection con = manager.getConnection();
		PreparedStatement pstmt;
		ResultSet rs;
		boolean emp_Login = false;
		boolean id_error = false;
		ArrayList<String> emp = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		String sql1 = "select emp_login_id from emp where emp_job not in ('manager')";
		
		try {
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp.add(rs.getString("emp_login_id"));
			}
			sql1 = "select emp_login_id from emp";
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("emp_login_id"));
			}
			System.out.println(list.size());
			String id = t_id.getText();

			for (int i = 0; i < emp.size(); i++) {
				if (emp.get(i).equalsIgnoreCase(id)) {
					emp_Login = true;
				}
			}
			System.out.println();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equalsIgnoreCase(id)) {
					id_error = true;
				}
			}
			if(id_error){
				String sql2 = "select emp_login_pw from emp where emp_login_id='" + id + "'";
				
				pstmt = con.prepareStatement(sql2);
				rs = pstmt.executeQuery();
					
				char[] pw_array = t_pw.getPassword();
				String pw = new String(pw_array);
				rs.next();
				String db_pw = rs.getString("emp_login_pw");
				if (db_pw.equals(pw)) {
					JOptionPane.showMessageDialog(this, "로그인 완료! " + id + "님 환영합니다.");
					posWindow.id = id;
					// 클라이언트 메인 키기
					if(!emp_Login){
						posWindow.page[1].add(new AdminPage(posWindow));
						posWindow.setPage(1);
					}else if(emp_Login){
						posWindow.page[2].add(new OrderMain(posWindow));
						posWindow.setPage(2);
						
						CallMain callMain = new CallMain();
						OrdersListMain orderlistMain = new OrdersListMain();
						orderlistMain.setCallMain(callMain);
					}
				} else {
				JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다.");
				}
			}else{
				JOptionPane.showMessageDialog(this, "로그인정보를 확인해주세요");
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// String id = t_id.getText();
	// String pw = new String(t_pw.getPassword());// char[]캐릭터 배열로반환
	// if (id.equals("batman") && pw.equals("1234")) {
	// JOptionPane.showMessageDialog(this, "로그인 완료");
	//
	//
	// } else {
	// JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");
	// }

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_login) {
			loginCheck();
		} 
	}
	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == t_id) {
			if (t_id.getText().equals("ID입력")) {
				t_id.setText("");
				t_id.setForeground(Color.BLACK);
			}
		} else if (obj == t_pw) {
			t_pw.setEchoChar('*');
			if (t_pw.getText().equals("PW입력")) {
				t_pw.setText("");
				t_pw.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == t_id) {
			if (t_id.getText().isEmpty()) {
				t_id.setForeground(Color.GRAY);
				t_id.setText("ID입력");
			}
		} else if (obj == t_pw) {
			if (t_pw.getText().isEmpty()) {
				t_pw.setForeground(Color.GRAY);
				t_pw.setEchoChar((char) 0);
				t_pw.setText("PW입력");
			}
		}
	}
	
	
}
