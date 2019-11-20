package Socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class MessageListener extends Thread {
	public static String msg;
	Socket socket;
	InputStream inStream;
	DataInputStream dataInStream;
	BufferedReader br = null;

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
		} catch (Exception e) {
		}
	}
}
