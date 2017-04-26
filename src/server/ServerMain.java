package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	ServerSocket server;
	int port = 7777;

	public ServerMain() {
		try {
			server = new ServerSocket(port);
			System.out.println("서버생성");

			while(true) {
				Socket socket = server.accept();
				System.out.println("접속자 캐치");
				ServerThread thread = new ServerThread(socket);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerMain();

	}

}
