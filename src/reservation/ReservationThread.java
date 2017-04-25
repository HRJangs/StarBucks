package reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import dto.Reservation;
import json.ReservationProtocol;

public class ReservationThread extends Thread{
	Vector<Reservation> resList;
	String type;
	
	Socket socket;
	int port = 7777;
	//String host = "127.0.0.1";
	String host = "211.238.142.120";
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public ReservationThread(Vector<Reservation> resList, String type) {
		this.resList = resList;
		this.type = type;
		
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
			
			if(data.equals("")){
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		for(int i = 0; i < resList.size(); i++) {
			ReservationProtocol protocol = new ReservationProtocol(resList.get(i), type);
			String msg = protocol.getProtocol();
			
			try {
				buffw.write(msg+"\n");
				buffw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
