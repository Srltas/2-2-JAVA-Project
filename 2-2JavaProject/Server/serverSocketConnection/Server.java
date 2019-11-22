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
	static int waitRoomCount = 0;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startServer(server.ip,server.port);
	}
	
	//서버 프로그램 실행 메소드
	public void startServer(String ip, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(ip, port));
			System.out.println("Server > Server Socket is created!");
			
			while(true) {
				try {
					Socket socket = serverSocket.accept();
					ConnectedClient connectedClient = new ConnectedClient(socket);
					clients.add(connectedClient);
					connectedClient.start();
				} catch(Exception e) {
					if(!serverSocket.isClosed()) {
						stopServer();
					}
					break;
				}
			}
		} catch (Exception e) {
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
			e.printStackTrace();
		}
	}
}