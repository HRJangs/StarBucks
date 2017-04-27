package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerThread2 extends Thread {
	Socket socket;
	DataInputStream dis;
	FileOutputStream fos;
	InputStream is;
	boolean flag=true;

	public ServerThread2(Socket socket) {
		this.socket = socket;
		try {
			is =socket.getInputStream();
			dis = new DataInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		try {
			byte[] b = new byte[1024];
			String fileName= dis.readUTF();
			System.out.println(fileName);
			fos = new FileOutputStream("C:/myserver/data/"+fileName);
			
			while(true){
				int data=is.read(b);
				if(data==-1)break;
				fos.write(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	finally {
			flag=false;
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(dis!=null){
				try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}


	// 여기서 이제 서버가 처리해줄 껄 다 작성해줘야한다.
	public void run() {
		while(flag){
			listen();
		}

	}

}
