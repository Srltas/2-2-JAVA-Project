package LoginUI;

import java.io.OutputStream;
import java.net.Socket;

public class Client {
	Socket socket;
	public static String name;
	private String message;
	
	Client(String message){
		this.message = message;
	}
	
	//Ŭ���̾�Ʈ ���α׷� ���� �޼ҵ�
	public void startClient(String IP,int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP,port);
					send();
				} catch(Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[���� ���� ����]");
					}
				}
			}
		};
		thread.start();
	}
	
	//Ŭ���̾�Ʈ ���α׷��� ���� �޼ҵ�
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//������ �޽����� �����ϴ� �޼ҵ�
	public void send() {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				} catch(Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
}
