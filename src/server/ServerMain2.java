package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain2 {
	ServerSocket server;
	int port = 7778;

	public ServerMain2() {
		try {
			server = new ServerSocket(port);
			System.out.println("POS�� ��������");

			while(true) {
				Socket socket = server.accept();
				System.out.println("pos ������ ĳġ");
				ServerThread2 thread = new ServerThread2(socket);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerMain2();

	}

}
