package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dto.Product;

public class ClientOrders extends JPanel implements ActionListener{
	ClientMain main;
	JPanel p_north, p_center, p_south;
	JButton bt_orders_delete, bt_orders_send;
	Vector<Product> orders_list = new Vector<Product>();
	JScrollPane scroll;
	JLabel la_sum;
	
	
	
	public ClientOrders(ClientMain main) {
		this.main=main;
		
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		scroll = new JScrollPane(p_north);
		
		scroll.setPreferredSize(new Dimension(300*2, 300));
		p_center.setPreferredSize(new Dimension(300*2,150*2));
		p_south.setPreferredSize(new Dimension(300*2,50*2));
		
		bt_orders_delete = new JButton("삭제하기");
		bt_orders_send = new JButton("주문하기");
		
		la_sum = new JLabel("합계 금액");
		
		p_south.add(la_sum);
		p_south.add(bt_orders_delete);
		p_south.add(bt_orders_send);
		
		//제품 목록 올려두기
		init();
				
		add(scroll, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);		
		
		//버튼에 리스너 연결
		bt_orders_delete.addActionListener(this);
		bt_orders_send.addActionListener(this);
		
		setPreferredSize(new Dimension(300*2,400*2));
		setVisible(true);
	}
	
	//접속과 동시에 가져온 제품 정보를 페널로 다 띄우기
	public void init(){
		for(int i=0;i<main.product_list.size();i++){
			ClientMenuPanel cmp = new ClientMenuPanel(this, main.product_list.get(i));
			p_north.add(cmp);
		}	
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("누른건 캐치");
		Object obj = e.getSource();
		
		//주문하기를 눌렀을경우, 
		if(obj == bt_orders_send){
			//라벨에 더해진 것들의 product를 가져와야한다.
			ClientThread thread = new ClientThread(main, orders_list);
			thread.start();
			
		}else if (obj == bt_orders_delete){
			System.out.println("삭제");
			orders_list.removeAllElements();
			la_sum.setText("합계금액");
			p_center.removeAll();
			p_center.updateUI();
		}		
	}
}
