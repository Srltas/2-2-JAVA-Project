package serverSocketConnection;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int SERVER_PORT = 9876;
	private ConnectedClient manager = new ConnectedClient();

	public static void main(String[] args) {
		new Server().startServer();
	}

	// 서버 프로그램 실행 메소드)
	public void startServer() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			ConnectedClient.print("Start Server!");
			

			while (true) {

				Socket socket = serverSocket.accept();
				
				this.manager.register(socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stopServer(serverSocket);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectedClient.print("Close Server");
		}
	}

	private void stopServer(ServerSocket server) throws Exception {
		if (manager != null) {
			manager.leaveAll();
		}

		if (server != null) {
			server.close();
		}
	}
}