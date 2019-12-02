package clientSocketConnection;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	
	public static Client client = new Client();
	int port = 9876;
	String ip = "210.119.33.40";
	MessageListener msgListener;
	
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream;
	
	public static int waitingRoomCount;

	// 클라이언트 프로그램 실행 메소드
	public void startClient() {
		try {
			mySocket = new Socket(ip, port);
			if (mySocket != null) {
				System.out.println("Client> 서로 연결되었습니다.");
				msgListener = new MessageListener(mySocket);
				msgListener.start();
			} else {
				System.out.println("Server has problem");
			}
			
		} catch (Exception e) {	}
	}

	// 로그인 정보를 보내는 메소드
	public void send(String msg) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("sendLogin in Login UI client : " + msg);
					outStream = mySocket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);

					dataOutStream.writeUTF(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}