package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Login.LoginService;

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
					
					//확인용
					if(id.equals("user") && password.equals("pass"))
						dataOutStream.writeUTF("true");
					/*if (login.login(id, password))
						dataOutStream.writeUTF("true");*/

					System.out.println(id);
					System.out.println(password);
				} else if (number.equals("1")) {
					System.out.println("단어 정보입니다.");
				} else if (number.equals("2")) {
					System.out.println("채팅 정보입니다.");
				}

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