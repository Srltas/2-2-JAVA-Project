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
	
	//������ �������Ѽ� Ŭ���̾�Ʈ�� ������ ��ٸ��� �޼ҵ�
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
		
		//Ŭ���̾�Ʈ�� ������ �� ���� ��ٸ��� ������(�͸�ü)
		Runnable thread = new Runnable() {
			 @Override
			 public void run() {
				 while(true) {
					 try {
						 Socket socket = serverSocket.accept();
						 clients.add(new ServerClient(socket));
						 System.out.println();
						 System.out.println("[Ŭ���̾�Ʈ ����]" + socket.getRemoteSocketAddress() + ":" 
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
	
	//������ �۵��� ������Ű�� �޼ҵ�
	public void stopServer() {
		try {
			//���� �۵� ���� ��� ���� �ݱ�
			Iterator<ServerClient> iterator = clients.iterator();
			while(iterator.hasNext()) {
				ServerClient client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//���� ���� ��ü �ݱ�
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
