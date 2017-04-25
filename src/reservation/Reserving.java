package reservation;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dto.Reservation;
import json.ReservationProtocol;

public class Reserving extends JFrame implements ActionListener{
	JPanel p_center, p_south;
	JLabel label;
	Choice ch_time;
	JButton bt_back, bt_finish;
	
	int selectTime;
	Vector<Reservation> resList = new Vector<Reservation>();
	
	ReservationMain reservationMain;
	int date;
	int time;
	int maxUnit;
	
	public Reserving(ReservationMain reservationMain, int date, int time, int maxUnit) {
		this.reservationMain = reservationMain;
		this.date = date;
		this.time = time;
		this.maxUnit = maxUnit;
		
		p_center = new JPanel();
		p_south = new JPanel();
		label = new JLabel("예약할 시간 단위");
		ch_time = new Choice();
		bt_back = new JButton("뒤로");
		bt_finish = new JButton("예약 완료");
		
		ch_time.add("총 예약시간");
		for(int i = 0; i < maxUnit; i++) {
			ch_time.add((i + 1) + "시간");
		}
		
		p_center.add(label);
		p_center.add(ch_time);
		
		p_south.add(bt_back);
		p_south.add(bt_finish);
		
		bt_back.addActionListener(this);
		bt_finish.addActionListener(this);
		ch_time.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("선택된 시간은? " + ch_time.getSelectedItem() + ", 인덱스 : " + ch_time.getSelectedIndex());
				selectTime = ch_time.getSelectedIndex();
				System.out.println(selectTime);
			}
		});
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(200, 100);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void insertReservation() {
		for(int i = 0; i < selectTime; i++) {
			Reservation reservation = new Reservation();
			
			reservation.setReservation_room_num(reservationMain.roomNum);
			reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
			reservation.setReservation_year(reservationMain.year);
			reservation.setReservation_month(reservationMain.month + 1);
			reservation.setReservation_date(date);
			reservation.setReservation_start_time(time + i);
			reservation.setReservation_time_unit(1);
			
			resList.add(reservation);
			
		}
		
		ReservationThread thread = new ReservationThread(resList, "insert");
		thread.start();
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == bt_back) {
			this.dispose();
		} else if(obj == bt_finish) {
			//insert프로토콜보내기
			if(selectTime != 0) {
				insertReservation();
			} 
			else {
				JOptionPane.showMessageDialog(this, "시간을 선택해주세요!");
			}
			
		}
	}

}
