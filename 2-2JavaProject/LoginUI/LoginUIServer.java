package LoginUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import Login.LoginService;

public class LoginUIServer {
	public static void main(String[] args) {
		LoginUIServer server = new LoginUIServer();
		try {
			server.ss = new ServerSocket(9876);
			System.out.println("Server > Server Socket is created!");

			while (true) {
				Socket socket = server.ss.accept();
				ConnectedClient c = new ConnectedClient(socket);
				server.clients.add(c);
				c.start();
			}
		} catch (SocketException e) {

		} catch (Exception e) {

		}
	}

	ServerSocket ss = null;
	static ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
}

class ConnectedClient extends Thread {
	Socket socket;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;

	ConnectedClient(Socket _s) {
		socket = _s;
	}

	public void run() {
		try {

			System.out.println("Server> " + this.socket.toString() + "에서 접속이 연결되었습니다.");

			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);

			dataOutStream.writeUTF("Welcome to this Server.");
			while (true) {

				String msg = dataInStream.readUTF();
				System.out.println("Server> " + this.socket.toString() + ": " + msg);
				String number = msg.substring(0, 1);
				System.out.println(number);
				String messageBody = msg.substring(1);
				System.out.println(messageBody);

				if (number.equals("0")) {

					// 여기
					String id = messageBody.substring(0, msg.lastIndexOf(",") - 1);
					String password = messageBody.substring(msg.lastIndexOf(","));

					LoginService login = new LoginService();

					if (login.login(id, password)) {
						System.out.println("login success");
					} else {
						System.out.println("fail");

					}

					System.out.println(id);
					System.out.println(password);
				} else if (number.equals("1")) {
					System.out.println("단어 정보입니다.");
				} else if (number.equals("2")) {
					System.out.println("채팅 정보입니다.");
				}
				//
				/*
				 * for(ConnectedClient client : LoginUIServer.clients) {
				 * client.dataOutStream.writeUTF(msg); }
				 */
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}