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
		//���⼭ ����� �Ѵ�. �����ϰ� ���ϰ� �� ���⼭ �Ѷ�~~!
		System.out.println(reservationMain.year+"��"+(reservationMain.month+1)+"��"+date+"��"+time+"������?");
		
		status = reservationMain.reservationStatus[date-1][time-10];
		int now = time - 10;
		isPossibleNextTime = 0;
		isPossoblePrevTime = 0;
		flag[0] = false;
		flag[1] = false;
		
		/*
		 * next�� prev�� ���� 10�̸�
		 * �� �ð��� ���� �ð��� �ȴ�.
		 * next�� 10�̸� 17�ø� �ѱ�� �Ǿ� ���� �� ���� ���Ƶд�.
		 * prev�� 10�̸� 10�� �����̱� ������ ���� �� ���� �Ѵ�. 
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
		
		if(flag[0] == true && flag[1] == false) {//next�� ��
			move("next");
		} else if(flag[0] == false && flag[1] == true) {//prev�� ��
			move("prev");
		} else {//�� ���� �ð��� �ƴ� ��� next�� prev��
			move("both");
		}
		
		
	
	}
	
	public void move(String s) {
		if(s.equals("next")) {
			if(status == 0) {
				/*
				 * ������ �ȵǾ������Ƿ� Reserving.java�� ����
				 * ���� ������ �ð� ���� ��ȸ�ؼ� ������
				 * ??���?? ���� �ð��� status�� 2�̸� �ִ� 1�ð��� ���� �ɼ� �ְ� choice�� 1�� ��Ÿ���� �Ѵ�.
				 * ReservationProtocol�� insert���� ������
				 */
				
				if(reservationMain.currentMonth[date - 1][0] >= 2) {
					JOptionPane.showMessageDialog(reservationMain, "�Ϸ翡 �ִ� 2�ð� ���� �����մϴ�!");
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
				 * �� �����̹Ƿ� MyReservation.java�� ����
				 * ���� 2�ð��� ����������, �ִ� 2�ð��� ��Ÿ���� 
				 * ���� 1�ð��� �����ߴµ�, ���� �ð��� ������ �ȵǾ������� 2�ð��� ��Ÿ�� �� �ְ�
				 * �����ð��� ������ �Ǿ������� 1�ð��� ���õ� �� �ְ��Ѵ�. 
				 * ���� ��ư ������ ReservationProtocol�� update���� ������
				 * ���� ��ư ������ ReservationProtocol�� delete���� ������
				 */
				if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 1) {
					new MyReservation(reservationMain, date, time, 1, "otherreserve");
				} else if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 0) {//���� �ȵǾ�����
					new MyReservation(reservationMain, date, time, 2, "noreserve");
				} else if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 2) {//�� ������.
					new MyReservation(reservationMain, date, time, 2, "myreserve");
				}
			} else if(status == 1) {
				JOptionPane.showMessageDialog(reservationMain, "�̹� ���� ������ �ð��Դϴ�!");
			}
		} else if(s.equals("prev")) {
			if(status == 0) {
				if(reservationMain.currentMonth[date - 1][0] >= 2) {
					JOptionPane.showMessageDialog(reservationMain, "�Ϸ翡 �ִ� 2�ð� ���� �����մϴ�!");
				}
				else {
					new Reserving(reservationMain, date, time, 1);	
				}
				
			} else if(status == 2) {
				if(reservationMain.reservationStatus[date-1][isPossoblePrevTime] != 2) {
					new MyReservation(reservationMain, date, time, 1, "otherreserve");
				} else if(reservationMain.reservationStatus[date-1][isPossoblePrevTime] == 2) {
					new MyReservation(reservationMain, date, time, 2, "myreserve_prev");//���⿡�� ���࿡ ���� 1�ð����� �����ϸ� delete�� �������Ѵ�.
				}
			} else if(status == 1) {
				JOptionPane.showMessageDialog(reservationMain, "�̹� ���� ������ �ð��Դϴ�!");
			}
			
		} else if(s.equals("both")) {
			int nextStatus = reservationMain.reservationStatus[date-1][isPossibleNextTime];
			int prevStatus = reservationMain.reservationStatus[date-1][isPossoblePrevTime];
			
			if(status == 1) {
				JOptionPane.showMessageDialog(reservationMain, "�̹� ���� ������ �ð��Դϴ�!");
			} else if(status == 0) {
				if(reservationMain.currentMonth[date - 1][0] >= 2) {
					JOptionPane.showMessageDialog(reservationMain, "�Ϸ翡 �ִ� 2�ð� ���� �����մϴ�!");
				}
				else {
					if(prevStatus == 2) {
						if(nextStatus != 2) {
							new Reserving(reservationMain, date, time, 2);
						}
						else {
							JOptionPane.showMessageDialog(reservationMain, "�ȵſ�!");
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
						JOptionPane.showMessageDialog(reservationMain, "�ȵſ�!");
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
