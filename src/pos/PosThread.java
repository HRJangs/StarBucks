package pos;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PosThread extends Thread{
	
	String msg;
	//���� ���� ����
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
		
		//���� �ű�
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
		//�Ұ� ����~ �׳� �ѹ��� �����ֱ� �ϸ� �Ǵ°Ŷ� ��� �����嵵 �ƴϾ�Ǵµ� �׳� �������~! ���� Ŭ���� ������~!
	}
	
	public void send(){
		try {
			byte[] b = new byte[1024];
			dos.writeUTF(fileName); //���������� �ϴ� ����~!
			System.out.println("������"+fileName);
			
			
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
