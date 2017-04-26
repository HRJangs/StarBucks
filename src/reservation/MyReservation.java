package reservation;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dto.Reservation;

public class MyReservation extends JFrame implements ActionListener{
	JPanel p_center, p_south;
	JLabel label;
	Choice ch_time;
	JButton bt_back, bt_edit, bt_delete;
	
	int selectTime;
	Vector<Reservation> resList = new Vector<Reservation>();
	
	ReservationMain reservationMain;
	int date;
	int time;
	int maxUnit;
	String type;
	
	public MyReservation(ReservationMain reservationMain, int date, int time, int maxUnit, String type) {
		this.reservationMain = reservationMain;
		this.date = date;
		this.time = time;
		this.maxUnit = maxUnit;
		this.type = type;
		
		p_center = new JPanel();
		p_south = new JPanel();
		label = new JLabel("������ �ð� ����");
		ch_time = new Choice();
		bt_back = new JButton("�ڷ�");
		bt_edit = new JButton("���� ����");
		bt_delete = new JButton("���� ����");
		
		ch_time.add("�� ����ð�");
		for(int i = 0; i < maxUnit; i++) {
			ch_time.add((i + 1) + "�ð�");
		}
		
		p_center.add(label);
		p_center.add(ch_time);
		
		p_south.add(bt_back);
		p_south.add(bt_edit);
		p_south.add(bt_delete);
		
		bt_back.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_delete.addActionListener(this);
		
		ch_time.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("���õ� �ð���? " + ch_time.getSelectedItem() + ", �ε��� : " + ch_time.getSelectedIndex());
				selectTime = ch_time.getSelectedIndex();
				System.out.println(selectTime);
			}
		});
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(300, 200);
		setVisible(true);
		setLocationRelativeTo(reservationMain);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void updateReservation() {
		resList.removeAll(resList);
		
		ReservationThread thread;
		System.out.println("type : " + type);
		if(type.equals("insertNext")) {
			if(selectTime == 1) {
				System.out.println("���� ���� ����.");
			} else if(selectTime == 2) {
				for(int i = 0; i < selectTime; i++) {
					Reservation reservation = new Reservation();
					reservation.setReservation_room_num(reservationMain.roomNum);
					reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
					reservation.setReservation_year(reservationMain.year);
					reservation.setReservation_month(reservationMain.month + 1);
					reservation.setReservation_date(date);	
					reservation.setReservation_time_unit(1);
					reservation.setReservation_start_time(time + i);
					
					resList.add(reservation);
				}
				
				thread = new ReservationThread(resList, "insert", this, reservationMain);
				thread.start();
			}
			
		} else if(type.equals("prev") || type.equals("next")) {
			if(selectTime == 1) {
				Reservation reservation = new Reservation();
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);	
				reservation.setReservation_time_unit(1);
				reservation.setReservation_start_time(time);
				
				resList.add(reservation);
				
				thread = new ReservationThread(resList, "delete", this, reservationMain);
				thread.start();

			}
			else {
				System.out.println("���� ���� ����.");
			}
		} else if(type.equals("only")) {
			System.out.println("������� ����.");
		}

	}
	
	public void deleteReservation() {
		resList.removeAll(resList);
		
		ReservationThread thread;

		if(type.equals("insertNext") || type.equals("only")) {
				Reservation reservation = new Reservation();
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);	
				reservation.setReservation_time_unit(1);
				reservation.setReservation_start_time(time);
					
				resList.add(reservation);
				
				
			
		} else if(type.equals("prev")) {
			for(int i = 0; i < 2; i++) {
				Reservation reservation = new Reservation();
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);	
				reservation.setReservation_time_unit(1);
				reservation.setReservation_start_time(time - i);
				
				resList.add(reservation);
			}
		} else if(type.equals("next")) {
			for(int i = 0; i < 2; i++) {
				Reservation reservation = new Reservation();
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);	
				reservation.setReservation_time_unit(1);
				reservation.setReservation_start_time(time + i);
				
				resList.add(reservation);
			}
		}
		
		thread = new ReservationThread(resList, "delete", this, reservationMain);
		thread.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == bt_back) {
			this.dispose();
		} else if(obj == bt_edit) {
			//update�������ݺ�����
			if(selectTime != 0) {
				updateReservation();
			} 
			else {
				JOptionPane.showMessageDialog(this, "�ð��� �������ּ���!");
			}
			
		} else if(obj == bt_delete) {
			//delete�������ݺ�����
			deleteReservation();
		}
	}
}
