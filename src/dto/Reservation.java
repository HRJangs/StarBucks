package dto;

import java.sql.Timestamp;

public class Reservation {
	private int reservation_id;
	private int reservation_room_num;
	private Timestamp reservation_current_time;
	private String reservation_member_login_id;
	private int reservation_time_unit;
	private int reservation_start_time;
	private int reservation_year;
	private int reservation_month;
	private int reservation_date;
	
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	public int getReservation_room_num() {
		return reservation_room_num;
	}
	public void setReservation_room_num(int reservation_room_num) {
		this.reservation_room_num = reservation_room_num;
	}
	public Timestamp getReservation_current_time() {
		return reservation_current_time;
	}
	public void setReservation_current_time(Timestamp reservation_current_time) {
		this.reservation_current_time = reservation_current_time;
	}
	public String getReservation_member_login_id() {
		return reservation_member_login_id;
	}
	public void setReservation_member_login_id(String reservation_member_login_id) {
		this.reservation_member_login_id = reservation_member_login_id;
	}
	public int getReservation_time_unit() {
		return reservation_time_unit;
	}
	public void setReservation_time_unit(int reservation_time_unit) {
		this.reservation_time_unit = reservation_time_unit;
	}
	public int getReservation_start_time() {
		return reservation_start_time;
	}
	public void setReservation_start_time(int reservation_start_time) {
		this.reservation_start_time = reservation_start_time;
	}
	public int getReservation_year() {
		return reservation_year;
	}
	public void setReservation_year(int reservation_year) {
		this.reservation_year = reservation_year;
	}
	public int getReservation_month() {
		return reservation_month;
	}
	public void setReservation_month(int reservation_month) {
		this.reservation_month = reservation_month;
	}
	public int getReservation_date() {
		return reservation_date;
	}
	public void setReservation_date(int reservation_date) {
		this.reservation_date = reservation_date;
	}
	
	

}