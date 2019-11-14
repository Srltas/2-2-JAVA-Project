package LoginUI;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	Socket socket;
	public static String name;
	//private String message;
	
	/*Client(String message){
		this.message = message;
	}*/
	
	//클라이언트 프로그램 동작 메소드
	public void startClient(String IP,int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP,port);
				} catch(Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[서버 접속 실패]");
					}
				}
			}
		};
		thread.start();
	}
	
	//클라이언트 프로그램을 종료 메소드
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//서버로 메시지를 전송하는 메소드
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					DataOutputStream dataOutSteam = new DataOutputStream(out);
					dataOutSteam.writeUTF(message);
				} catch(Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
}
