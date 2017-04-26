package reservation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

public class ReservationMouseAdapter extends MouseAdapter{
	ReservationMain reservationMain;
	int date;
	int time;
	
	int status;
	int prevStatus;
	int nextStatus;
	
	public ReservationMouseAdapter(ReservationMain reservationMain, int date, int time) {
		this.reservationMain =reservationMain;
		this.date=date;
		this.time=time;
	}
	
	public void mouseClicked(MouseEvent e) {
		//여기서 열어야 한다. 수정하고 뭐하고 다 여기서 켜라~~!
		System.out.println(reservationMain.year+"년"+(reservationMain.month+1)+"월"+date+"일"+time+"눌렀군?");
		int now = time - 10;
		
		status = reservationMain.reservationStatus[date-1][time-10];
		
		if((now - 1) < 0) {
			prevStatus = 0;
		} else {
			prevStatus = reservationMain.reservationStatus[date-1][(time-10) - 1];
		}
		
		if((now + 1) > 7) {
			nextStatus = 0;
		}
		else {
			nextStatus = reservationMain.reservationStatus[date-1][(time-10) + 1];
		}
		System.out.println("prev : " + prevStatus + ", next : " + nextStatus);
		move();
	}
	
	public void move() {
		if(status == 0) {
			if(reservationMain.currentMonth[date - 1] == 1) {
				
				new Reserving(reservationMain, date, time, 1);
			} else if(reservationMain.currentMonth[date - 1] == 0) {
				new Reserving(reservationMain, date, time, 2);
			} else {
				JOptionPane.showMessageDialog(reservationMain, "최대 예약시간은 2시간입니다.");
			}
		} else if(status == 2) {
			if(reservationMain.currentMonth[date - 1] == 1) {
				new MyReservation(reservationMain, date, time, 2, "insertNext");
			} 
			else if(reservationMain.currentMonth[date - 1] == 2) {
				if(prevStatus == 2) {
					new MyReservation(reservationMain, date, time, 1, "prev");
				} else if(nextStatus == 2) {
					new MyReservation(reservationMain, date, time, 1, "next");
				} else {
					new MyReservation(reservationMain, date, time, 1, "only");
				}
				
			}
		} else if(status == 1) {
			JOptionPane.showMessageDialog(reservationMain, "이미 예약이 마감되었습니다.");
		}
	}
}
