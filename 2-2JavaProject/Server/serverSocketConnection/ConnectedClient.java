package serverSocketConnection;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

class ConnectedClient extends Thread {
	static ArrayList<Users> clients = new ArrayList<Users>();
	Socket socket;

	public static void print(String message) {
		System.out.println("###################################");
		System.out.println("[" + message + "]");
		System.out.println("###################################");
	}

	public void register(Socket client) throws Exception {
		clients.add(new Users(client));
		print("Enter Player : " + client.toString());
	}

	public void leaveAll() throws Exception {
		if (clients != null) {
			for (Users user : clients) {
				user.close();
			}
		}
	}

	public void leaveUser() throws Exception {
		
	}

	public void messageAll(String message) throws Exception {
		if (clients != null) {
			for (Users user : clients) {
				user.writer(message);
			}
		}
	}

	public void message(Socket client, String message) throws Exception {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		out.write(message);
	}

	public void run() {
		try {
			while (true) {
				
			}
		} catch (Exception e) {
			System.out.println("[" + this.socket.toString() + "가 연결을 종료했습니다.]");
		}
	}
}