package pos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import board.BoardCount;
import board.BoardMain;
import board.BoardTableModel;
import dto.Board;
import pos.login.PosWindow;

public class BoardPanel extends JPanel implements ActionListener{
	JPanel p_main;
	JTable table;
	JScrollPane scroll;
	JButton bt_back,bt_insert;
	Vector<Board> board_list = new Vector<Board>();
	PosWindow posWindow;
	
	public BoardPanel(PosWindow posWindow) {
		this.posWindow = posWindow;
		p_main = new JPanel();
		setTable();
		bt_insert =  new JButton("게시글 쓰기");
		scroll.setPreferredSize(new Dimension(800-20, 500-20));
		p_main.setPreferredSize(new Dimension(800-20, 500-20));
		p_main.add(scroll);
		add(p_main);		
		add(bt_insert,BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(300*2+200, 600));
		setVisible(true);
		setBackground(Color.WHITE);
		
		bt_insert.addActionListener(this);
	}
	
	public void setTable(){
		BoardTableModel model = new BoardTableModel(board_list);
		
		table = new JTable(model);
		scroll = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//조회수 올리기 해야한다.DB에
				int row =table.getSelectedRow();
				board_list.get(row).setBoard_count(board_list.get(row).getBoard_count()+1);
				showContent(row);
				new BoardCount(board_list.get(row));
			}
		});
	}
	public void updateTable(){
		BoardTableModel model = new BoardTableModel(board_list);
		table.setModel(model);
	}
	
	public void showContent(int row){
		p_main.removeAll();
		Font font = new Font("돋움", Font.BOLD, 15);
		Font font_title = new Font("돋움", Font.BOLD, 35);
		JPanel p_title = new JPanel();
		JPanel p_info = new JPanel();
		JLabel la_title_value = new JLabel(board_list.get(row).getBoard_title());
		JLabel la_writer = new JLabel("작성자: ");
		JLabel la_writer_value = new JLabel(board_list.get(row).getBoard_emp_name()+" |");
		JLabel la_count = new JLabel("조회수: ");
		JLabel la_count_value = new JLabel(Integer.toString(board_list.get(row).getBoard_count()));
		p_title.setPreferredSize(new Dimension(600, 40));
		la_title_value.setFont(font_title);
		la_writer.setFont(font);
		la_writer_value.setFont(font);
		la_count.setFont(font);
		la_count_value.setFont(font);
		p_title.add(la_title_value);
		p_info.add(la_writer);
		p_info.add(la_writer_value);
		p_info.add(la_count);
		p_info.add(la_count_value);
	
		String content = board_list.get(row).getBoard_contents();
		JTextArea area = new JTextArea(30,50);
		area.setText(content);
		JScrollPane scroll = new JScrollPane(area);
		
		bt_back = new JButton("뒤로");
		bt_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardPanel.this.setPreferredSize(new Dimension(300*2+100, 700));
				BoardPanel.this.p_main.removeAll();
				//테이블을 새로 그리고 해야한다.
				BoardPanel.this.setTable();
				BoardPanel.this.p_main.add(BoardPanel.this.scroll);
				BoardPanel.this.add(p_main);
				BoardPanel.this.p_main.updateUI();
			}
		});
		
		p_main.add(p_title);
		p_main.add(p_info);
		p_main.add(scroll);
		p_main.add(bt_back);
		p_main.updateUI();
	}

	public void actionPerformed(ActionEvent e) {
		new AddBoard(posWindow, this);
	}
}
