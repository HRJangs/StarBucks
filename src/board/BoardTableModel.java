package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import db.DBManager;
import dto.Board;

public class BoardTableModel extends AbstractTableModel{
	DBManager manager = DBManager.getInstance();
	Connection con;
	Vector<Board> board_list;
	String[] columnName = {"글번호", "제목", "작성자", "등록시간", "조회수"};
	Vector<Vector> list = new Vector<Vector>();
	
	public BoardTableModel(Vector<Board> board_list) {
		this.board_list=board_list;
		con = manager.getConnection();
		getList();
	}
	
	public void getList(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String str = "select board_id, board_emp_id, board_time, board_title, board_contents, board_count, emp_name from board, emp where emp.emp_id=board.board_emp_id";
		try {
			pstmt = con.prepareStatement(str);
			rs = pstmt.executeQuery();
			while(rs.next()){
				//vector에 담자
				Vector vec = new Vector();
				vec.add(rs.getString("board_id"));				
				vec.add(rs.getString("board_title"));
				vec.add(rs.getString("emp_name"));
				vec.add(rs.getString("board_time"));
				vec.add(rs.getString("board_count"));
				list.add(vec);
				
				//이제 dto에 담자!
				Board content = new Board();
				content.setBoard_id(rs.getInt("board_id"));
				content.setBoard_emp_id(rs.getInt("board_emp_id"));
				content.setBoard_time(rs.getTimestamp("board_time"));
				content.setBoard_title(rs.getString("board_title"));
				content.setBoard_contents(rs.getString("board_contents"));
				content.setBoard_count(rs.getInt("board_count"));
				content.setBoard_emp_name(rs.getString("emp_name"));
				board_list.add(content);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	public String getColumnName(int col) {
		return columnName[col];
	}


	public int getColumnCount() {
		return columnName.length;
	}


	public int getRowCount() {
		return list.size();
	}


	public Object getValueAt(int row, int col) {
		return list.get(row).get(col);
	}
	 

}
