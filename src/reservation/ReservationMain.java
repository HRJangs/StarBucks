package reservation;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBManager;
import dto.Member;

public class ReservationMain extends JFrame implements ActionListener, ItemListener{
	JPanel p_north, p_center, p_cal, p_south;
	JPanel p_south_east, p_south_center;
	Choice choice;
	String room="1번 스터디룸";
	JButton bt_prev, bt_next, bt_show;
	JLabel la;
	
	boolean flag;
	URL[] url_small = new URL[3];
	URL nowURL;
	String selectRoom;
	
	
	DBManager manager = DBManager.getInstance();
	Connection con=manager.getConnection();
	
	//회원 정보
	Member dto;
	
	//방관련
	int roomNum=1;
	
	//달력 관련
	Calendar cal;
	int year;
	int month;
	int date;
	
	//예약현황 배열
	int[][] reservationStatus={
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0}
	};
	
	//본인의 예약 현황 배열
	int[][] currentMonth = {
			{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0},
			{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, 
			{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, 
			{0, 0}
			}; 
	
	public ReservationMain(Member dto) {
		this.dto=dto;
		p_north = new JPanel();
		p_north.setLayout(new BorderLayout());
		
		p_center = new JPanel();
		p_cal = new JPanel();
		p_south = new JPanel();
		p_south_east = new JPanel();
		p_south_center = new JPanel();
		
		p_south.setLayout(new BorderLayout());
		p_south.setPreferredSize(new Dimension(600, 700));
		//위쪽패널
		choice = new Choice();
		bt_show = new JButton("이미지 보기");
		bt_prev = new JButton("<");
		la = new JLabel("");
		la.setFont(new Font("고딕", Font.BOLD, 20));
		bt_next = new JButton(">");
		
		choice.setPreferredSize(new Dimension(100, 50));
		p_cal.setPreferredSize(new Dimension(100, 50));
		p_south_east.setPreferredSize(new Dimension(150, 600));
		
		p_south.setVisible(false);
		
		p_south.add(p_south_east, BorderLayout.EAST);
		p_south.add(p_south_center);
		
		p_cal.add(bt_prev);
		p_cal.add(la);
		p_cal.add(bt_next);
			
		/*choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object obj = e.getItem();
				String str = obj.toString();
				//스트링에서 숫자만 추출하기
				ReservationMain.this.roomNum=Integer.parseInt(str.replaceAll("[^0-9]",""));
				p_center.removeAll();
				attachPanel();
			}
		});*/
		choice.add("스터디룸 선택");
		choice.add("1번 스터디룸");
		choice.add("2번 스터디룸");
		
		//choice.select(1);
		
		p_north.add(choice, BorderLayout.WEST);
		p_north.add(p_cal, BorderLayout.CENTER);
		p_north.add(bt_show, BorderLayout.EAST);
		p_north.add(p_south, BorderLayout.SOUTH);
		
		add(p_north, BorderLayout.NORTH);
		
		//센터에 달력 패널 붙이기
		init();
		
		add(p_center);
		p_north.setPreferredSize(new Dimension(600, 50));
		
		choice.addItemListener(this);
		//버튼 리스너 붙이기
		bt_show.addActionListener(this);
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		
		setSize(300*2, 400*2);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	//달력 갖다 붙이기
	public void init(){
		getCalendar();
		la.setText(year+"년 "+(month+1)+"월");
		attachPanel();
	}
	
	//현재 일자 가져오기!
	public void getCalendar(){
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
	}
	
	//쿼리문 날리기
		public void query(){
			//rs일때만 0으로 만들면 안되니깐~~!! 여기서 초기화를 한번 해줘라~!
			for(int j=0; j<31;j++){
				currentMonth[j][0] = 0;
				currentMonth[j][1] = 0;
				
				for(int i=0;i<8;i++){
					reservationStatus[j][i]=0; //아니면 0으로 다시 돌려놔야되~
				}
			}		
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from reservation where reservation_month="+(month+1)+" and reservation_room_num="+roomNum;
			try {
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					for(int j=0;j<31;j++){
						if(rs.getInt("reservation_date")==j+1){
							for(int i=0;i<8;i++){								
								if(rs.getInt("reservation_start_time") == i+10){
									if(rs.getString("reservation_member_login_id").equalsIgnoreCase(dto.getMember_login_id())){
										reservationStatus[j][i]=2; //내가 한 예약이면,
									}else {
										reservationStatus[j][i]=1; //다른 사람이 한 예약이면,
									}
									
								}
							}
						}
					}
				}
				
				/*여기에 하루마다 최대 예약 시간은 2시간이다.*/
				for(int i = 0; i < reservationStatus.length; i++) {
					for (int j = 0; j < reservationStatus[i].length; j++) {
						if(reservationStatus[i][j] == 2) {
							currentMonth[i][0]++;
							
						/*	if(j == 0) {
								for(int a = 2; a < reservationStatus[i].length; a++) {
									if(reservationStatus[i][a] == 2){
										
									}
								}
							} else if(j < reservationStatus.length - 2) {
								
							}*/
							
						}
					}
					System.out.println(i + " : " + currentMonth[i]);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(pstmt!=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
	public void attachPanel(){
		query();
		cal.set(year, month, 1);
		int first = cal.get(Calendar.DAY_OF_WEEK);
		
		cal.set(year, month+1, 0);
		int last = cal.get(Calendar.DATE);
		
		
		int num=1;
		for(int i=0; i<42; i++){
			if(i>=first-1&&num<=last){
				ResCalendar resCalendar = new ResCalendar(this, num);
				p_center.add(resCalendar);
				num++;
				
			} else {
				EmptyCalendar emptyCalendar = new EmptyCalendar();
				p_center.add(emptyCalendar);
			}			
		}
		p_center.updateUI();
	
	}
	
	public void showImage() {
		/*flag가 true이면 이미지가 보여지고 있는 상태 
		 * false이면 이미지가 안보여지고 있는 상태*/
		if(choice.getSelectedIndex() != 1 && choice.getSelectedIndex() != 2) {
			JOptionPane.showMessageDialog(this, "방을 선택해 주세요!");
		}
		else {
			if(flag) {
				flag = false;
				
				p_south.updateUI();
				p_south.setVisible(false);
				p_north.setPreferredSize(new Dimension(600, 50));
				
			}
			else {
				flag = true;
				
				p_south.updateUI();
				p_south.setVisible(true);
				p_north.setPreferredSize(new Dimension(600, 750));
			}
 		}
		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_prev){
			month--;
			if(month<0){
				month=11;
				year--;
			}
		} else if(obj == bt_next){
			month++;
			if(month>11){
				month=0;
				year++;
			}
		} else if(obj == bt_show) {
			showImage();
		} 
		la.setText(year+"년 "+(month+1)+"월");
		la.updateUI();
		p_center.removeAll();
		attachPanel();
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(choice.getSelectedIndex() != 0) {
			selectRoom = choice.getSelectedItem();
			//smallList.removeAll(smallList);
			p_south_east.removeAll();
			p_south_center.removeAll();
			try {
				if(selectRoom.equals("1번 스터디룸")) {
					for(int i = 0; i < url_small.length; i++) {
						url_small[i] = new URL("http://localhost:9090/data/reserve1-"+ (i + 1)+".jpg");
						System.out.println(url_small[i].toString());
						
						CreateSmallCan small = new CreateSmallCan(url_small[i], this);
						p_south_east.add(small);
						System.out.println("CreateSmallCan 생성 후" + url_small[i].toString());
						//smallList.add(small);
					}
					
				} else if(selectRoom.equals("2번 스터디룸")) {
					for(int i = 0; i < url_small.length; i++) {
						url_small[i] = new URL("http://localhost:9090/data/reserve2-"+ (i + 1)+".jpg");
						System.out.println(url_small[i].toString());
						
						CreateSmallCan small = new CreateSmallCan(url_small[i], this);
						p_south_east.add(small);
						//smallList.add(small);
					}
				}
				
				Object obj = e.getItem();
				String str = obj.toString();
				//스트링에서 숫자만 추출하기
				ReservationMain.this.roomNum=Integer.parseInt(str.replaceAll("[^0-9]",""));
				p_center.removeAll();
				attachPanel();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new ReservationMain(new Member());

	}

}
