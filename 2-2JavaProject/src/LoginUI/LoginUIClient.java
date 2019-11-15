package LoginUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LoginUIClient {
	public static LoginUIClient client; // 전역변수 client
	Socket mySocket = null;
	MessageListener msgListener = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;

	public void startClient() {
		LoginUIClient client = new LoginUIClient();
		this.client = client;
		try {
			client.mySocket = new Socket("127.0.0.1", 9876);
			System.out.println("Client> 서로 연결되었습니다.");
			msgListener = new MessageListener(client.mySocket);
			msgListener.start();

		} catch (Exception e) {

		}
	}

	public void sendLogin(String msg) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println(msg);
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

class MessageListener extends Thread {
	Socket socket;
	InputStream inStream;
	DataInputStream dataInStream;
	static String msg;

	MessageListener(Socket _s) {
		socket = _s;
	}

	public void run() {
		try {
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			while (true) {
				msg = dataInStream.readUTF();
				System.out.println("Client> Server sent: " + msg);
			}
		} catch (Exception e) {	}
	}
}
