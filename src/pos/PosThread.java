package pos;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PosThread extends Thread{
	
	String msg;
	//소켓 접속 관련
	Socket socket;
	int port = 7778;
	String host = "211.238.142.118";
	DataOutputStream dos;
	FileInputStream fis;
	String fileName;
	OutputStream os;
	
	public PosThread(FileInputStream fis, String fileName) {
		this.fis = fis;
		this.fileName=fileName;
		
		//소켓 꼽기
		try {
			socket = new Socket(host, port);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		send();
	}
	
	public void run() {
		//할게 없다~ 그냥 한번씩 보내주기 하면 되는거라서 사실 쓰레드도 아니어도되는데 그냥 만들었엉~! 독립 클래스 정도다~!
	}
	
	public void send(){
		try {
			byte[] b = new byte[1024];
			dos.writeUTF(fileName); //파일제목을 일단 보내~!
			System.out.println("보낸거"+fileName);
			
			
			while(true){
				int data=fis.read(b);
				if(data==-1)break;
				os.write(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(dos!=null){
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}
	
}
