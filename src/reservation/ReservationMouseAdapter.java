package reservation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class ReservationMouseAdapter extends MouseAdapter{
	ReservationMain reservationMain;
	int date;
	int time;
	
	int status;
	int isPossibleNextTime;
	int isPossoblePrevTime;
	boolean[] flag = new boolean[2];
	
	public ReservationMouseAdapter(ReservationMain reservationMain, int date, int time) {
		this.reservationMain =reservationMain;
		this.date=date;
		this.time=time;
	}
	
	public void mouseClicked(MouseEvent e) {
		//여기서 열어야 한다. 수정하고 뭐하고 다 여기서 켜라~~!
		System.out.println(reservationMain.year+"년"+(reservationMain.month+1)+"월"+date+"일"+time+"눌렀군?");
		
		status = reservationMain.reservationStatus[date-1][time-10];
		int now = time - 10;
		isPossibleNextTime = 0;
		isPossoblePrevTime = 0;
		flag[0] = false;
		flag[1] = false;
		
		/*
		 * next와 prev의 값이 10이면
		 * 그 시간은 없는 시간이 된다.
		 * next가 10이면 17시를 넘기게 되어 비교할 수 없게 막아둔다.
		 * prev가 10이면 10시 이전이기 때문에 비교할 수 없게 한다. 
		*/
		if(now == 0) {
			isPossibleNextTime = (time - 10) + 1;
			isPossoblePrevTime = 10;
		} else if(now == 7) {
			isPossibleNextTime = 10;
			isPossoblePrevTime = (time - 10) - 1;
		} else {
			isPossibleNextTime = (time - 10) + 1;
			isPossoblePrevTime = (time - 10) - 1;
		}
		
		if(isPossibleNextTime == 10) {
			flag[0] = false;
			flag[1] = true;
		} else if(isPossoblePrevTime == 10) {
			flag[0] = true;
			flag[1] = false;
		}
		
		if(flag[0] == true && flag[1] == false) {//next만 비교
			move("next");
		} else if(flag[0] == false && flag[1] == true) {//prev만 비교
			move("prev");
		} else {//양 끝의 시간이 아닐 경우 next와 prev비교
			move("both");
		}
		
		
	
	}
	
	public void move(String s) {
		if(s.equals("next")) {
			if(status == 0) {
				/*
				 * 예약이 안되어있으므로 Reserving.java로 연결
				 * 예약 가능한 시간 단위 조회해서 보내기
				 * ??어떻게?? 다음 시간의 status가 2이면 최대 1시간이 선택 될수 있게 choice에 1만 나타나게 한다.
				 * ReservationProtocol에 insert쿼리 보내기
				 */
				
				if(reservationMain.currentMonth[date - 1][0] >= 2) {
					JOptionPane.showMessageDialog(reservationMain, "하루에 최대 2시간 예약 가능합니다!");
				}
				else {
					if(reservationMain.reservationStatus[date-1][isPossibleNextTime] != 0) {
						new Reserving(reservationMain, date, time, 1);
					} 
					else {
						new Reserving(reservationMain, date, time, 2);
					}
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
		} else if(s.equals("prev")) {
			if(status == 0) {
				if(reservationMain.currentMonth[date - 1][0] >= 2) {
					JOptionPane.showMessageDialog(reservationMain, "하루에 최대 2시간 예약 가능합니다!");
				}
				else {
					new Reserving(reservationMain, date, time, 1);	
				}
				
			} else if(status == 2) {
				if(reservationMain.reservationStatus[date-1][isPossoblePrevTime] != 2) {
					new MyReservation(reservationMain, date, time, 1, "otherreserve");
				} else if(reservationMain.reservationStatus[date-1][isPossoblePrevTime] == 2) {
					new MyReservation(reservationMain, date, time, 2, "myreserve_prev");//여기에서 만약에 내가 1시간으로 수정하면 delete문 날려야한다.
				}
			} else if(status == 1) {
				JOptionPane.showMessageDialog(reservationMain, "이미 예약 마감된 시간입니다!");
			}
			
		} else if(s.equals("both")) {
			int nextStatus = reservationMain.reservationStatus[date-1][isPossibleNextTime];
			int prevStatus = reservationMain.reservationStatus[date-1][isPossoblePrevTime];
			
			if(status == 1) {
				JOptionPane.showMessageDialog(reservationMain, "이미 예약 마감된 시간입니다!");
			} else if(status == 0) {
				if(reservationMain.currentMonth[date - 1][0] >= 2) {
					JOptionPane.showMessageDialog(reservationMain, "하루에 최대 2시간 예약 가능합니다!");
				}
				else {
					if(prevStatus == 2) {
						if(nextStatus != 2) {
							new Reserving(reservationMain, date, time, 2);
						}
						else {
							JOptionPane.showMessageDialog(reservationMain, "안돼요!");
						}
					} else {
						if(nextStatus == 1) {
							new Reserving(reservationMain, date, time, 1);
						}
						else {
							new Reserving(reservationMain, date, time, 2);
						}
					}
				}
			} else if(status == 2) {
				if(prevStatus == 2) {
					if(nextStatus != 2) {
						new MyReservation(reservationMain, date, time, 2, "myreserve_both");
					}
					else {
						JOptionPane.showMessageDialog(reservationMain, "안돼요!");
					}
				} else {
					if(nextStatus == 0) {
						new MyReservation(reservationMain, date, time, 2, "noreserve");
					} else if(nextStatus == 2) {
						new MyReservation(reservationMain, date, time, 2, "myreserve");
					} else {
						new MyReservation(reservationMain, date, time, 1, "otherreserve");
					}
				}
			}
		}
			
	}
}
