package json;

import dto.Reservation;

public class ReservationProtocol {
	StringBuffer sb = new StringBuffer();
	
	public ReservationProtocol(Reservation reservation, String type) {
		sb.append("{");
		sb.append("\"requestType\":\"reservation\",");
		sb.append("\"type\":\"" + type + "\",");
		sb.append("\"reservation_room_num\" : " + reservation.getReservation_room_num() + ",");
		sb.append("\"reservation_member_login_id\" : \" "+ reservation.getReservation_member_login_id() +"\",");
		sb.append("\"reservation_time_unit\" : "+ reservation.getReservation_time_unit() + ",");
		sb.append("\"reservation_start_time\" : " + reservation.getReservation_start_time() + ",");
		sb.append("\"reservation_year\" : "+ reservation.getReservation_year() +", ");
		sb.append("\"reservation_month\" : "+ reservation.getReservation_month() +", ");
		sb.append("\"reservation_date\" : "+ reservation.getReservation_date() +"");
		sb.append("}");
	}
	
	public String getProtocol() {
		return sb.toString();
	}

}
