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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import board.BoardCount;
import board.BoardTableModel;
import dto.Board;
import pos.login.PosWindow;

public class BoardPanel extends JPanel implements ActionListener{
	JPanel p_main,p_down;
	JTable table;
	JScrollPane scroll;
	JButton bt_back,bt_add;
	Vector<Board> board_list = new Vector<Board>();
	PosWindow posWindow;
	BoardTableModel model;
	public BoardPanel(PosWindow posWindow) {
		this.posWindow = posWindow;
		p_main = new JPanel();
		p_down = new JPanel();
		bt_add= new JButton("공지사항 등록");
		setTable();
		//setLayout(new BorderLayout());
		p_main.setPreferredSize(new Dimension(750 - 20, 600 - 50));
		p_main.add(scroll);
		add(p_main);
		bt_add.addActionListener(this);
		p_down.add(bt_add);
		add(p_down,BorderLayout.SOUTH);
		setPreferredSize(new Dimension(300 * 2+150, 600-20));
		setVisible(true);
	}

	public void setTable() {
		model = new BoardTableModel(board_list);
		table = new JTable(model);

		table.setGridColor(new Color(238, 238, 238)); // 격자색을 정한다.
		table.setRowHeight(50); // 행간을 조절한다.
		table.getColumn("글번호").setPreferredWidth(50);
		table.getColumn("제목").setPreferredWidth(250);
		table.getColumn("작성자").setPreferredWidth(100);
		table.getColumn("등록시간").setPreferredWidth(150);
		table.getColumn("조회수").setPreferredWidth(50);

		table.setIntercellSpacing(new Dimension(10, 5));// 셀간격

		// 가운데정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를
																		// 생성
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을
															// CENTER로

		TableColumnModel tcm = table.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴
		tcm.getColumn(0).setCellRenderer(dtcr);
		tcm.getColumn(2).setCellRenderer(dtcr);
		tcm.getColumn(3).setCellRenderer(dtcr);
		tcm.getColumn(4).setCellRenderer(dtcr);

		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 조회수 올리기 해야한다.DB에
				int row = table.getSelectedRow();
				board_list.get(row).setBoard_count(board_list.get(row).getBoard_count() + 1);
				showContent(row);
				new BoardCount(board_list.get(row));
			}
		});
		
		//스크롤도 세팅해주기
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600 - 20, 600-50));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}

	public void showContent(int row) {
		p_main.removeAll();
		p_down.removeAll();
		Font font = new Font("돋움", Font.BOLD, 15);
		Font font_title = new Font("돋움", Font.BOLD, 35);
		JPanel p_title = new JPanel();
		JPanel p_info = new JPanel();
		JLabel la_title_value = new JLabel(board_list.get(row).getBoard_title());
		JLabel la_writer = new JLabel("작성자: ");
		JLabel la_writer_value = new JLabel(board_list.get(row).getBoard_emp_name() + " |");
		JLabel la_count = new JLabel("조회수: ");
		JLabel la_count_value = new JLabel(Integer.toString(board_list.get(row).getBoard_count()));
		p_title.setPreferredSize(new Dimension(700, 40));
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
		JTextArea area = new JTextArea(23, 60);
		area.setText(content);
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area);

		bt_back = new JButton("뒤로");
		bt_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardPanel.this.p_main.removeAll();
				// 테이블을 새로 그리고 해야한다.
				BoardPanel.this.setTable();
				BoardPanel.this.p_main.add(BoardPanel.this.scroll); 
				BoardPanel.this.p_main.updateUI();
				BoardPanel.this.p_down.add(BoardPanel.this.bt_add); 
				BoardPanel.this.p_down.updateUI();
				
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
