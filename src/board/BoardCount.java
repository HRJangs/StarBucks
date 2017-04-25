package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBManager;
import dto.Board;

public class BoardCount {
	Board board;
	DBManager manager= DBManager.getInstance();
	Connection con;
	PreparedStatement pstmt;
	
	public BoardCount(Board board) {
		this.board=board;
		con = manager.getConnection();
		try {
			pstmt=con.prepareStatement("update board set board_count = board_count +1 where board_id=?" );
			pstmt.setInt(1, board.getBoard_id());
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
