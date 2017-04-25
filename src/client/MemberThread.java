package client;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dto.Card;
import dto.Member;
import json.CardProtocol;
import json.MemberProtocol;

public class MemberThread extends Thread{
	Member member;
	ClientEdit main;
	
	Socket socket;
	int port = 7777;
	//String host = "127.0.0.1";
	String host = "211.238.142.117";
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public MemberThread(Member member, ClientEdit main) {
		this.member = member;
		this.main = main;
		
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
		while(true) {
			listen();
		}
	}
	
	public void listen() {
		try {
			String data = buffr.readLine();
			
			if(data.equals("회원수정완료")){
				JOptionPane.showMessageDialog(main, "수정 완료!");
				main.clientMain.setPage(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		MemberProtocol protocol = new MemberProtocol(member);
		String msg = protocol.getProtocol();
		
		try {
			buffw.write(msg+"\n");
			buffw.flush();
			System.out.println(msg + " 회원 정보 수정");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
