package serverSocketConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import serverLogin.LoginService;
import serverLogin.CreateAccountService;
import serverLogin.DeniedOverlapLoginService;
import serverLogin.IdFindService;
import serverLogin.ChangePasswordService;

class ConnectedClient extends Thread {
	Socket socket;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;
	int playerNumber = 0;
	String playerName;

	LoginService login = new LoginService();

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

					if (DeniedOverlapLoginService.dols.isOverlap(id)) {
						id = null;
					}
					serverLogin.Account account = login.login(id, password);
					if (id != null && account!=null) {
						System.out.println("login success");
						DeniedOverlapLoginService.dols.logInSuccess(id);
						dataOutStream.writeUTF("Login success,"+account.getRankPoint()+","+account.getUserName());
					}
					
					System.out.println(id);
					System.out.println(password);
				} else if (message[0].equals("signUp")) {
					// 회원가입
					System.out.println("[catch by create]");

					CreateAccountService createAccount = new CreateAccountService();
					if (createAccount.createAccount(message[1], message[2], message[3], message[4], message[5])) {
						dataOutStream.writeUTF("account create success");
					} else {
						dataOutStream.writeUTF("account create failed");
					}
				} else if (message[0].equals("findID")) {
					// 아이디 찾기
					System.out.println("[catch by findID]");
					
					IdFindService idFindService = new IdFindService();
					serverLogin.Account idFindAccount = idFindService.FindId(message[1]);
					if ((idFindAccount != null)) {
						dataOutStream.writeUTF("success : " + "," + idFindAccount.getId());
					}
					
				} else if (message[0].equals("findPW")) {
					// 비밀번호 변경
					ChangePasswordService changePasswordService = new ChangePasswordService();
					
					System.out.println("catch by change PW");
					
					if(changePasswordService.changePassword(message[1],message[2],message[3])) {
						System.out.println("change password success");
						dataOutStream.writeUTF("password change success");
					}
				} else if (message[0].equals("enterGameRoom")) {
					// GameRoom에 입장하는 클라이언트
					int enterPlayerNumber;
					playerName = message[1]; // 각각 cc에 해당 유저닉네임 저장

					if (Server.gameRoomCount < 4) {
						for(enterPlayerNumber = 0; enterPlayerNumber < Server.playerList.length; enterPlayerNumber++) {
							if(Server.playerList[enterPlayerNumber].equals("")) {
								Server.playerList[enterPlayerNumber] = message[1];
								break;
							}
						}
						for (ConnectedClient client : Server.clients) {
							for (int i = 0; i < Server.playerList.length; i++) {
								if (Server.playerList[i].equals("")) {
									continue;
								}
								Thread.sleep(500);
								client.dataOutStream.writeUTF(
										"enterGameRoom," + Integer.toString(i + 1) + "," + Server.playerList[i]);
							}
						}
						Server.gameRoomCount++;
						System.out.println("[방 인원 수 : " + Server.gameRoomCount + "]");
						// 4명이면 게임 시작!
						if (Server.gameRoomCount == 4) {
							for (ConnectedClient client : Server.clients) {
								client.dataOutStream.writeUTF("startGame");
							}
						}
					} else {
						playerNumber = 0;
						Server.playerList[playerNumber] = message[1];
						for (ConnectedClient client : Server.clients) {
							for (int i = 0; i < Server.playerList.length; i++) {
								if (Server.playerList[i].equals(""))
									continue;
								Thread.sleep(500);
								client.dataOutStream.writeUTF(
										"enterGameRoom," + Integer.toString(i + 1) + "," + Server.playerList[i]);
							}
						}
						Server.gameRoomCount = 1;
						System.out.println("[방 인원 수 : " + Server.gameRoomCount + "]");
					}
				} else if (message[0].equals("exitGameRoom")) {
					// GameRoom에서 퇴장하는 클라이언트
					int exitPlayerNumber;
					for (exitPlayerNumber = 0; exitPlayerNumber < Server.playerList.length; exitPlayerNumber++) {
						if (Server.playerList[exitPlayerNumber].equals(message[1]) == true) {
							Server.playerList[exitPlayerNumber] = "";
							break;
						}
					}
					Server.gameRoomCount--;
					System.out.println("[방 인원 수 : " + Server.gameRoomCount + "]");
					for (ConnectedClient client : Server.clients) {
						client.dataOutStream.writeUTF("exitGameRoom," + Integer.toString(exitPlayerNumber + 1));

					}
				} else if (message[0].equals("myTurn")) { // 각 턴에 해당하는 유저 지정
					if (Server.playerList[Server.gameTurn % 4].equals(this.playerName)) {
						dataOutStream.writeUTF("myTurn," + Integer.toString(Server.gameTurn));
						Server.gameTurn++;
					}
				} else if (message[0].equals("myTurnOff")) { // 턴 종료
					if (Server.playerList[Server.gameTurnOff % 4].equals(this.playerName)) {
						dataOutStream.writeUTF("myTurnOff,");
						Server.gameTurnOff++;
					}
				} else if(message[0].equals("word")) { //단어 유효 검사
					
				} else if (message[0].equals("chat")) { //채팅

					System.out.println(message[1]);
					for (ConnectedClient client : Server.clients) {
						client.dataOutStream.writeUTF("chat," + message[1]);
					}
				}
			}
		} catch (Exception e) {
			Server.clients.remove(this);
			System.out.println("[" + this.socket.toString() + "가 연결을 종료했습니다.]");
		}
	}
}