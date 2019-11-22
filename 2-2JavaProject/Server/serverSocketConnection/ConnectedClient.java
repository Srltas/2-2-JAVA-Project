package serverSocketConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import clientLoginData.Account;
import serverLogin.LoginService;
import serverLogin.CreateAccountService;

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

			System.out.println("[" + this.socket.toString() + "에서 접속이 연결되었습니다.]");

			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);

			dataOutStream.writeUTF("[Welcome to this Server]");
			while (true) {

				String msg = dataInStream.readUTF();
				System.out.println("[" + this.socket.toString() + ": " + msg + "]");
				String number = msg.substring(0, 1);
				String messageBody = msg.substring(1);
				System.out.println("[number : " + number + "]");
				System.out.println("[messageBody : " + messageBody + "]");

				if (number.equals("0")) {
					String[] accountData = messageBody.split(",");

					String id = accountData[0];
					String password = accountData[1];

					LoginService login = new LoginService();

					if (login.login(accountData[0], accountData[1])) {
						System.out.println("login success");
						dataOutStream.writeUTF("Login success," + new Account().getRankPoint());
					}

					System.out.println(id);
					System.out.println(password);
				} else if (number.equals("1")) {
					// 회원가입
					System.out.println("[catch by create]");
					String[] accountData = messageBody.split(",");

					CreateAccountService createAccount = new CreateAccountService();
					if (createAccount.createAccount(accountData[0], accountData[1],
							/* accountData[2], */ accountData[2], accountData[3])) {
						dataOutStream.writeUTF("account create success");
					} else {
						dataOutStream.writeUTF("account create failed");
					}
				} else if (number.equals("2")) {
					// waitRoom에 입장하는 클라이언트 순서 판단
					if (Server.waitRoomCount < 4) {
						Server.waitRoomCount++;
						System.out.println("[방 인원 수 : " + Server.waitRoomCount + "]");
						for (ConnectedClient client : Server.clients) {
							client.dataOutStream.writeUTF("2" + Integer.toString(Server.waitRoomCount));
						}
					} else {
						Server.waitRoomCount = 1;
						System.out.println("[방 인원 수 : " + Server.waitRoomCount + "]");
						for (ConnectedClient client : Server.clients) {
							client.dataOutStream.writeUTF("2" + Integer.toString(Server.waitRoomCount));
						}
					}
				} else if (number.equals("4")) {
					System.out.println("채팅 정보입니다.");
				}

				/*
				 * for(ConnectedClient client : LoginUIServer.clients) { if(this.equals(client))
				 * continue; client.dataOutStream.writeUTF(msg); }
				 */
			}
		} catch (Exception e) {
			System.out.println("[" + this.socket.toString() + "가 연결을 종료했습니다.]");
		}
	}
}