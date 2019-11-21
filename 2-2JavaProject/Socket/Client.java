package Socket;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static Client client;
	Socket mySocket;
	MessageListener msgListener;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	public static int waitingRoomCount;

	// 클라이언트 프로그램 실행 메소드
	public void startClient() {
		Client client = new Client();
		this.client = client;
		try {
			client.mySocket = new Socket("127.0.0.1", 9876);
			System.out.println("Client> 서로 연결되었습니다.");
			msgListener = new MessageListener(client.mySocket);
			msgListener.start();
		} catch (Exception e) {	}
	}

	//정보를 보내는 메소드
	public void send(String msg) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					//클라이언트가 보내는 메세지 확인용
					//System.out.println("sendLogin in Login UI client : " + msg);

					outStream = client.mySocket.getOutputStream();
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