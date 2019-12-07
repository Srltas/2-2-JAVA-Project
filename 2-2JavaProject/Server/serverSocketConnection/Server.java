package serverSocketConnection;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server {
	ServerSocket serverSocket = null;
	static ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
	String ip = "210.119.33.40";
	int port = 9876;
	static int gameRoomCount = 0;

	static String[] playerList = {"","","",""};
	static final String[] wordList = {"사자","바나나","자바","커피","강아지","컴퓨터","신발","치약","호랑이","칠판","판금"};
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startServer(server.ip,server.port);
	}
	
	//서버 프로그램 실행 메소드
	public void startServer(String ip, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(ip, port));
			System.out.println("[서버소켓이 실행되었습니다!]");
			
			while(true) {
				try {
					Socket socket = serverSocket.accept();
					
					ConnectedClient connectedClient = new ConnectedClient(socket);
					clients.add(connectedClient);
					connectedClient.start();
				} catch(Exception e) {
					if(!serverSocket.isClosed()) {
						System.out.println("[서버와 클라이언트 연결에 실패했습니다.]");
						stopServer();
					}
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("[서버 실행에 실패했습니다.]");
			e.printStackTrace();
			if (!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}
	}
	
	//서버 프로그램 종료 메소드
	public void stopServer() {
		try {
			//현재 작동 중인 모든 소켓 닫기
			Iterator<ConnectedClient> interator = clients.iterator();
			while(interator.hasNext()) {
				ConnectedClient client = interator.next();
				client.socket.close();
				interator.remove();
			}
			//서버 소켓 객체 닫기
			if(serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch(Exception e) {
			System.out.println("[서버 종료실패]");
		}
	}
}