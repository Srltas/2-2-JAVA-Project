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
import serverLogin.AccountMapper;

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

			dataOutStream.writeUTF("Welcome to this Server");

			serverLogin.Account account = null;
			while (true) {

				String msg = dataInStream.readUTF();
				System.out.println("[" + this.socket.toString() + ": " + msg + "]");
				String[] message = msg.split(",");

				if (message[0].equals("Login")) {
					// 로그인
					String id = message[1];
					String password = message[2];

					System.out.println(DeniedOverlapLoginService.printer());

					if (!DeniedOverlapLoginService.search(id)) {
						id = null;
					}

					account = login.login(id, password);
					if (id != null && account != null) {
						DeniedOverlapLoginService.add(id);
						System.out.println("login success");
						dataOutStream.writeUTF("Login success," + account.getRankPoint() + "," + account.getUserName());
					}

					System.out.println(id);
					System.out.println(password);
				} else if (message[0].equals("signUp")) {
					// 회원가입
					System.out.println("catch by create");

					CreateAccountService createAccount = new CreateAccountService();
					if (createAccount.createAccount(message[1], message[2], message[3], message[4], message[5])) {
						dataOutStream.writeUTF("account create success");
					} else {
						dataOutStream.writeUTF("account create failed");
					}
				} else if (message[0].equals("findID")) {
					// 아이디 찾기
					System.out.println("catch by findID");

					IdFindService idFindService = new IdFindService();
					serverLogin.Account idFindAccount = idFindService.FindId(message[1]);
					if ((idFindAccount != null)) {
						dataOutStream.writeUTF("findIDsuccess," + idFindAccount.getId());
					} else {
						// id없음
					}
				} else if (message[0].equals("changePW")) {
					// 비밀번호 변경
					ChangePasswordService changePasswordService = new ChangePasswordService();

					System.out.println("catch by change PW");

					if (changePasswordService.changePassword(message[1], message[2], message[3])) {
						System.out.println("change PW Success");
						dataOutStream.writeUTF("changePWSuccess");
					}
				} else if (message[0].equals("enterGameRoom")) {
					// GameRoom에 입장하는 클라이언트
					int enterPlayerNumber;

					for (enterPlayerNumber = 0; enterPlayerNumber < Server.playerList.length; enterPlayerNumber++) {
						if (Server.playerList[enterPlayerNumber].equals("")) {
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
							client.dataOutStream
									.writeUTF("enterGameRoom," + Integer.toString(i + 1) + "," + Server.playerList[i]);
						}
					}
					Server.gameRoomCount++;
					System.out.println("[방 인원 수 : " + Server.gameRoomCount + "]");
					// 4명이면 게임 시작!
					if (Server.gameRoomCount == 4) {
						Thread.sleep(3000);
						for (ConnectedClient client : Server.clients) {
							client.dataOutStream.writeUTF("readyGame");
						}
						message[0] = null; // 다시 안들어 오는 처리
					}
				} else if (message[0].equals("startGame")) {
					int number = (int) (Math.random() * Server.wordList.length); // 랜덤숫자 뽑기
					dataOutStream.writeUTF("startWord," + Server.wordList[number]); // 랜덤단어 주기

				} else if (message[0].equals("endGame")) {
					// 게임종료
					dataOutStream.writeUTF("endGame,");
					Server.endPlayerList[Server.index] = message[1];
					Server.endPlayerScoreList[Server.index] = Integer.parseInt(message[2]);
					Server.index++;
				} else if (message[0].equals("onResultButton")) {
					dataOutStream.writeUTF("onResultButton,");

				} else if (message[0].equals("resultGame")) {
					Server.gameRoomCount--;
					Server.index = 0;
					for(int i = 0; i < 4; i++) {
						if(Server.playerList[i].equals(message[1])) {
							Server.playerList[i] = "";
							break;
						}
					}
					dataOutStream.writeUTF("resultGame," + Server.endPlayerList[0] + "," + Server.endPlayerScoreList[0]
							+ "," + Server.endPlayerList[1] + "," + Server.endPlayerScoreList[1] + ","
							+ Server.endPlayerList[2] + "," + Server.endPlayerScoreList[2] + ","
							+ Server.endPlayerList[3] + "," + Server.endPlayerScoreList[3]);
				} else if (message[0].equals("sendMyScore")) {
					// 플레이어가 획득한 점수 계산
					String PlayerName = message[1]; // 플레이어 이름
					int PlayerScore = Integer.parseInt(message[2]);// 플레이어 획득 점수
					AccountMapper mapper = new AccountMapper();

					if (mapper.commitGameScore(PlayerName, PlayerScore)) {
						System.out.println("Score change success");
					} else {
						System.out.println("Score change false");
					}
					int oldScore = account.getRankPoint();
					account.setRankPoint(oldScore + PlayerScore);

					dataOutStream.writeUTF("Login success," + account.getRankPoint() + "," + account.getUserName());
				} else if (message[0].equals("chat")) {
					// 채팅
					System.out.println(message[1]);
					for (ConnectedClient client : Server.clients) {
						client.dataOutStream.writeUTF("chat," + message[1]);
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
					// 로그아웃
					if (DeniedOverlapLoginService.remove(message[2])) {
						System.out.println("log out success");
					} else {
						System.out.println("log out failed");
					}
				} else if (message[0].equals("exitGame")) {
					// 클라이언트 종료
					System.out.println(message[1]);
					// 로그아웃
					if (DeniedOverlapLoginService.remove(message[1])) {
						System.out.println("log out success");
					} else {
						System.out.println("log out failed");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Server.clients.remove(this);
			System.out.println("[" + this.socket.toString() + "가 연결을 종료했습니다.]");
		}

	}
}