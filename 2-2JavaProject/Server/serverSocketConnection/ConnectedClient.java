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
				String[] message = msg.split(",");

				if (message[0].equals("Login")) {
					// 로그인
					String id = message[1];
					String password = message[2];

					LoginService login = new LoginService();

					if (login.login(message[1], message[2])) {
						System.out.println("login success");
						dataOutStream.writeUTF("Login success," + new Account().getRankPoint());
					}

					System.out.println(id);
					System.out.println(password);
				} else if (message[0].equals("signUp")) {
					// 회원가입
					System.out.println("[catch by create]");

					CreateAccountService createAccount = new CreateAccountService();
					if (createAccount.createAccount(message[1], message[2], /* accountData[2], */ message[3],
							message[4])) {
						dataOutStream.writeUTF("account create success");
					} else {
						dataOutStream.writeUTF("account create failed");
					}
				} else if (message[0].equals("findID")) {
					// 아이디 찾기
				} else if (message[0].equals("findPW")) {
					// 비밀번호 변경
				} else if (message[0].equals("enterWaitRoom")) {
					// waitRoom에 입장하는 클라이언트
					if (Server.waitRoomCount < 4) {
						Server.waitRoomCount++;
						System.out.println("[방 인원 수 : " + Server.waitRoomCount + "]");
						for (ConnectedClient client : Server.clients) {
							client.dataOutStream.writeUTF("enterWaitRoom," + Integer.toString(Server.waitRoomCount));
						}
					} else {
						Server.waitRoomCount = 1;
						System.out.println("[방 인원 수 : " + Server.waitRoomCount + "]");
						for (ConnectedClient client : Server.clients) {
							client.dataOutStream.writeUTF("enterWaitRoom," + Integer.toString(Server.waitRoomCount));
						}
					}
				} else if (message[0].equals("exitWaitRoom")) {
					// waitRoom에서 퇴장하는 클라이언트
					Server.waitRoomCount--;
					System.out.println("[방 인원 수 : " + Server.waitRoomCount + "]");
					for (ConnectedClient client : Server.clients) {
						client.dataOutStream.writeUTF("enterWaitRoom," + Integer.toString(Server.waitRoomCount));
					}
				} else if(message[0].equals("startGame")) {
					if(Server.readyPlayerCount != 0 && Server.readyPlayerCount == Server.waitRoomCount) {
						for (ConnectedClient client : Server.clients) {
							client.dataOutStream.writeUTF("startGame," + Integer.toString(Server.waitRoomCount));
						}	
					}
				} else if(message[0].equals("readyPlayer")) {
					Server.readyPlayerCount++;
					System.out.println("[준비된 플레이어 수 : " + Server.readyPlayerCount + "]");
				} else if(message[0].equals("unReadyPlayer")) {
					Server.readyPlayerCount--;
					System.out.println("[준비된 플레이어 수 : " + Server.readyPlayerCount + "]");
				}

				/*
				 * for(ConnectedClient client : LoginUIServer.clients) { if(this.equals(client))
				 * continue; client.dataOutStream.writeUTF(msg); }
				 */
			}
		} catch (Exception e) {
			Server.clients.remove(this);
			System.out.println("[" + this.socket.toString() + "가 연결을 종료했습니다.]");
		}
	}
}