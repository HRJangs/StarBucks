package pos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.DBManager;
import pos.login.PosWindow;

public class AddBoard extends JFrame implements ActionListener{
	JPanel p_north,p_south,p_content;
	JTextField t_title,t_writer;
	JTextArea t_content;
	JButton bt_regist,bt_back;
	JScrollPane scroll;
	DBManager manager;
	Connection con;
	PosWindow posWindow;
	BoardPanel panel;
	public AddBoard(PosWindow posWindow,BoardPanel panel) {
		this.panel=panel;
		this.posWindow=posWindow;
		manager = DBManager.getInstance();
		con = manager.getConnection();
		t_content = new JTextArea(25,30);
		t_title = new JTextField(20);
		t_writer = new JTextField(5);
		bt_back = new JButton("뒤로");
		bt_regist = new JButton("등록");
		p_north =new JPanel();
		p_south = new JPanel();
		p_content = new JPanel();
		scroll = new JScrollPane(t_content);
		p_content.add(scroll);
		p_north.add(t_writer);
		p_north.add(t_title);
		add(p_north,BorderLayout.NORTH);
		add(p_content);
		p_south.add(bt_regist);
		p_south.add(bt_back);
		add(p_south,BorderLayout.SOUTH);
		t_writer.setText(posWindow.id);
		t_writer.setEditable(false);
		
		bt_back.addActionListener(this);
		bt_regist.addActionListener(this);
		
		setVisible(true);
		setSize(400,600);
		setLocationRelativeTo(null);
	}
	public void add(){
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try {
			String sql = "select emp_id from emp where emp_login_id='"+posWindow.id+"'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String emp_id="";
			while(rs.next()){
				emp_id=rs.getString(1);
			}
			StringBuffer sb= new StringBuffer();
			sb.append("insert into board(board_emp_id,board_time,board_title,board_contents)");
			sb.append("values("+emp_id+",CURRENT_TIMESTAMP,'"+t_title.getText()+"','"+t_content.getText()+"')");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.execute();
			System.out.println(sb.toString());
			JOptionPane.showMessageDialog(this, "게시글 등록 완료");
			this.dispose();
			panel.setTable();
			panel.table.updateUI();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_back){
			this.dispose();
		}else if(obj==bt_regist){
			add();
		}
	}
}