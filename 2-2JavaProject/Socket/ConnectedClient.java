package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Login.LoginService;
import Login.CreateAccountService;

class ConnectedClient extends Thread {
	Socket socket;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;

	ConnectedClient(Socket _s) {
		socket = _s;
	}

	public static String toString(String[] stringArray) {
		if (stringArray == null) {
			return "null";
		}

		if (stringArray.length == 0) {
			return "";
		}

		StringBuilder stringb = new StringBuilder();
		for (int i = 0; i <= stringArray.length; i++) {
			stringb.append(String.valueOf(stringArray[i]));
		}

		return stringb.toString();
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

					// 확인용
					/*
					 * if (id.equals("user") && password.equals("pass"))
					 * dataOutStream.writeUTF("true");
					 */

					if (login.login(id, password))
						dataOutStream.writeUTF("true");

					System.out.println(id);
					System.out.println(password);
				} else if (number.equals("1")) {
					// 회원가입
					System.out.println("catch by create");
					String[] accountData = messageBody.split(",");

					String id = accountData[0];
					String password = accountData[1];
					String password1 = accountData[2];
					String userName = accountData[3];
					String phoneNumber = accountData[4];

					/*
					 * String id = messageBody.substring(0, msg.lastIndexOf(",") - 1); String
					 * password = messageBody.substring(msg.lastIndexOf(","));
					 */

					CreateAccountService createAccount = new CreateAccountService();
					if (createAccount.createAccount(id, password, password1, userName, phoneNumber)) {

					}

					// 여기다가 회원가입 메소드 넣으면 될 듯?
				} else if (number.equals("2")) {
					//waitRoom에 입장하는 클라이언트 순서 판단
					Server.waitRoomCount++;
					for(ConnectedClient client : Server.clients) {
						client.dataOutStream.writeUTF("2"+Integer.toString(Server.waitRoomCount));
					}
				} else if (number.equals("4")) {
					System.out.println("채팅 정보입니다.");
				}

				/*
				 * for(ConnectedClient client : LoginUIServer.clients) {
				 * if(this.equals(client)) continue;
				 * client.dataOutStream.writeUTF(msg); }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}