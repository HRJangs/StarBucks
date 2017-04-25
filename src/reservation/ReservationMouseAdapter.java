package reservation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class ReservationMouseAdapter extends MouseAdapter{
	ReservationMain reservationMain;
	int date;
	int time;
	
	public ReservationMouseAdapter(ReservationMain reservationMain, int date, int time) {
		this.reservationMain =reservationMain;
		this.date=date;
		this.time=time;
	}
	
	public void mouseClicked(MouseEvent e) {
		//여기서 열어야 한다. 수정하고 뭐하고 다 여기서 켜라~~!
		System.out.println(reservationMain.year+"년"+(reservationMain.month+1)+"월"+date+"일"+time+"눌렀군?");
		
		int status = reservationMain.reservationStatus[date-1][time-10];
		int isPossibleNextTime = (time - 10) + 1;
		
		if(status == 0) {
			/*
			 * 예약이 안되어있으므로 Reserving.java로 연결
			 * 예약 가능한 시간 단위 조회해서 보내기
			 * ??어떻게?? 다음 시간의 status가 2이면 최대 1시간이 선택 될수 있게 choice에 1만 나타나게 한다.
			 * ReservationProtocol에 insert쿼리 보내기
			 */
			if(reservationMain.reservationStatus[date-1][isPossibleNextTime] != 0) {
				new Reserving(reservationMain, date, time, 1);
			} 
			else {
				new Reserving(reservationMain, date, time, 2);
			}
			
		} else if(status == 2) {
			/*
			 * 내 예약이므로 MyReservation.java로 연결
			 * 내가 2시간을 예약했으면, 최대 2시간이 나타나게 
			 * 내가 1시간을 예약했는데, 다음 시간에 예약이 안되어있으면 2시간이 나타날 수 있게
			 * 다음시간에 예약이 되어있으면 1시간만 선택될 수 있게한다. 
			 * 수정 버튼 누르면 ReservationProtocol에 update쿼리 보내기
			 * 삭제 버튼 누르면 ReservationProtocol에 delete쿼리 보내기
			 */
			if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 1) {
				new MyReservation(reservationMain, date, time, 1, "otherreserve");
			} else if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 0) {//예약 안되어있음
				new MyReservation(reservationMain, date, time, 2, "noreserve");
			} else if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 2) {//내 예약임.
				new MyReservation(reservationMain, date, time, 2, "myreserve");
			}
		} else if(status == 1) {
			JOptionPane.showMessageDialog(reservationMain, "이미 예약 마감된 시간입니다!");
		}
	}
}
