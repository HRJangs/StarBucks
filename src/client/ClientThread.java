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
	
	//���� ���� ����
		Socket socket;
		int port = 7777;
		String host = "211.238.142.120";
		BufferedReader buffr;
		BufferedWriter buffw;
	
	public ClientThread(ClientMain main, Vector<Product> product, OrdersPay pmain) {
		this.main = main;
		this.product = product;
		this.pmain = pmain;
		
		//���� �ű�
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
			
			if(data.equals("�ֹ��Ϸ�")){
				main.orders.p_center.removeAll();
				pmain.ordermain.orders_list.removeAllElements();
				pmain.ordermain.la_sum.setText("");
				pmain.ordermain.la_count.setText("");
				pmain.ordermain.p_center.removeAll();
				pmain.ordermain.p_center.updateUI();
				pmain.ordermain.la_null = new JLabel("��� �޴��� �����ϴ�.");
				pmain.ordermain.la_null.setFont(new Font("����", Font.BOLD, 20));
				pmain.ordermain.p_center.add(pmain.ordermain.la_null);

				for (int i = 0; i < pmain.ordermain.panel_list.size(); i++) {
					pmain.ordermain.panel_list.get(i).p_count = 1;
				}
				pmain.dispose();
				//JLabel la = new JLabel("�ֹ��Ϸ�! â���� Ȯ�����ּ���");
				//la.setFont(new Font("����", Font.BOLD, 30));
				//main.orders.p_center.add(la);
				JOptionPane.showMessageDialog(main, "�ֹ� �Ϸ�Ǿ����ϴ�.");
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
			
			//�� �ش系���� ���� �� �ִ� json �������� �����
			OrdersProtocol op = new OrdersProtocol(product_id, id);
			StringBuffer sb = op.getProtocol();
			msg = sb.toString();
			
			try {
				buffw.write(msg+"\n");
				buffw.flush();
				System.out.println("�ֹ� �ֱ�");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
