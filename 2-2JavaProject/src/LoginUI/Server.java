package LoginUI;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static ExecutorService threadPool;
	public static Vector<ServerClient> clients = new Vector<ServerClient>();
	
	ServerSocket serverSocket;
	
	//서버를 구동시켜서 클라이언트의 연결을 기다리는 메소드
	public void startServer(String IP, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IP,port));
			
		}catch(Exception e) {
			e.printStackTrace();
			if(!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}
		
		//클라이언트가 접속할 때 까지 기다리는 스레드(익명객체)
		Runnable thread = new Runnable() {
			 @Override
			 public void run() {
				 while(true) {
					 try {
						 Socket socket = serverSocket.accept();
						 clients.add(new ServerClient(socket));
						 System.out.println();
						 System.out.println("[클라이언트 접속]" + socket.getRemoteSocketAddress() + ":" 
						 + Thread.currentThread().getName());
					 } catch(Exception e) {
						 if(!serverSocket.isClosed()) {
							 stopServer();
						 }
						 break;
					 }
				 }
			 }
		};
		threadPool = Executors.newCachedThreadPool();
		threadPool.submit(thread);
	}
	
	//서버의 작동을 중지시키는 메소드
	public void stopServer() {
		try {
			//현재 작동 중인 모든 소켓 닫기
			Iterator<ServerClient> iterator = clients.iterator();
			while(iterator.hasNext()) {
				ServerClient client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//서버 소켓 객체 닫기
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.isShutdown();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Server server = new Server();
		server.startServer("127.0.0.1", 9876);
	}
}
