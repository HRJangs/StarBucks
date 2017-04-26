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
		//���⼭ ����� �Ѵ�. �����ϰ� ���ϰ� �� ���⼭ �Ѷ�~~!
		System.out.println(reservationMain.year+"��"+(reservationMain.month+1)+"��"+date+"��"+time+"������?");
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
				JOptionPane.showMessageDialog(reservationMain, "�ִ� ����ð��� 2�ð��Դϴ�.");
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
			JOptionPane.showMessageDialog(reservationMain, "�̹� ������ �����Ǿ����ϴ�.");
		}
	}
}
