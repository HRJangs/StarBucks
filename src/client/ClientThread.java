package client;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dto.Product;
import json.OrdersProtocol;

public class ClientThread extends Thread{
	ClientMain main;
	Vector<Product> product;
	String msg;
	OrdersPay pmain;
	
	//소켓 접속 관련
		Socket socket;
		int port = 7777;
		String host = "211.238.142.120";
		BufferedReader buffr;
		BufferedWriter buffw;
	
	public ClientThread(ClientMain main, Vector<Product> product, OrdersPay pmain) {
		this.main = main;
		this.product = product;
		this.pmain = pmain;
		
		//소켓 꼽기
		try {
			socket = new Socket(host, port);
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		send();
	}
	
	public void run() {
		while(true){
			listen();
		}
	}
	
	public void listen(){
		try {
			String data = buffr.readLine();
			
			if(data.equals("주문완료")){
				main.orders.p_center.removeAll();
				pmain.ordermain.orders_list.removeAllElements();
				pmain.ordermain.la_sum.setText("");
				pmain.ordermain.la_count.setText("");
				pmain.ordermain.p_center.removeAll();
				pmain.ordermain.p_center.updateUI();
				pmain.ordermain.la_null = new JLabel("담긴 메뉴가 없습니다.");
				pmain.ordermain.la_null.setFont(new Font("돋움", Font.BOLD, 20));
				pmain.ordermain.p_center.add(pmain.ordermain.la_null);

				for (int i = 0; i < pmain.ordermain.panel_list.size(); i++) {
					pmain.ordermain.panel_list.get(i).p_count = 1;
				}
				pmain.dispose();
				//JLabel la = new JLabel("주문완료! 창구를 확인해주세요");
				//la.setFont(new Font("돋움", Font.BOLD, 30));
				//main.orders.p_center.add(la);
				JOptionPane.showMessageDialog(main, "주문 완료되었습니다.");
				main.orders.p_center.updateUI();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void send(){
		for(int i=0; i<product.size();i++){
			int product_id = product.get(i).getProduct_id();
	
			int id=main.member.getMember_id();
			
			//위 해당내용을 보낼 수 있는 json 프로토콜 만들기
			OrdersProtocol op = new OrdersProtocol(product_id, id);
			StringBuffer sb = op.getProtocol();
			msg = sb.toString();
			
			try {
				buffw.write(msg+"\n");
				buffw.flush();
				System.out.println("주문 넣기");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
