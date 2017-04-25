package dto;

import java.sql.Timestamp;

public class Board {
	private int board_id;
	private int board_emp_id;
	private Timestamp board_time;
	private String board_title;
	private String board_contents;
	private int board_count;
	private String board_emp_name;
	
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public int getBoard_emp_id() {
		return board_emp_id;
	}
	public void setBoard_emp_id(int board_emp_id) {
		this.board_emp_id = board_emp_id;
	}
	public Timestamp getBoard_time() {
		return board_time;
	}
	public void setBoard_time(Timestamp board_time) {
		this.board_time = board_time;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_contents() {
		return board_contents;
	}
	public void setBoard_contents(String board_contents) {
		this.board_contents = board_contents;
	}
	public int getBoard_count() {
		return board_count;
	}
	public void setBoard_count(int board_count) {
		this.board_count = board_count;
	}
	public String getBoard_emp_name() {
		return board_emp_name;
	}
	public void setBoard_emp_name(String board_emp_name) {
		this.board_emp_name = board_emp_name;
	}
}
