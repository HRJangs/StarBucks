package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBManager;
import dto.Member;

public class EditController {
	DBManager manager;
	Connection con;
	String id;
	Member member;
	public EditController(String id) {
		manager = DBManager.getInstance();
		con =manager.getConnection();
		this.id =id;
	}
	public Member getMemberInstance(){
		return member;
	}
	
	public void getMember(){
		PreparedStatement pstmt  =null;
		ResultSet rs =null;
		String sql ="select * from member where member_login_id='"+id+"'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			member= new Member();
			while(rs.next()){
				member.setMember_id(rs.getInt(1));
				member.setMember_login_id(rs.getString(2));
				member.setMember_login_pw(rs.getString(3));
				member.setMember_name(rs.getString(4));
				member.setMember_nickname(rs.getString(5));
				member.setMember_gender(rs.getString(6));
				member.setMember_phone(rs.getString(7));
				member.setMember_birth(rs.getString(8));
				member.setMember_coupon(rs.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public void editMember(){
		
	}
}
