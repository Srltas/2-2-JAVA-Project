package LoginUI;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerClient {
	Socket socket;
	
	public ServerClient(Socket socket) {
		this.socket = socket;
		receive();
	}
	
	/*public void sqlControl() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				
			}
		}
	}*/
	
	//클라이언트로부터 메시지 전달 받는 메소드
	public void receive() {
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						InputStream in = socket.getInputStream();
						byte[] buffer = new byte[512];
						
						int length = in.read(buffer);
						if(length == -1) {
							throw new IOException();
						}
						System.out.println("[메세지 수신 성공]" + socket.getRemoteSocketAddress() + ":" +
						Thread.currentThread().getName());
						String message = new String(buffer,0,length,"UTF-8");
						System.out.println(message);
						/*for(ServerClient client : Server.clients) {
							client.send(message);
						}*/
					}
				}catch(Exception e) {
					try {
						System.out.println("[메세지 수신 오류]" + socket.getRemoteSocketAddress() + ":"
								+ Thread.currentThread().getName());
						Server.clients.remove(ServerClient.this);
						socket.close();
					} catch(Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		};
		Server.threadPool.submit(thread);	//threadPool에 스레드 등록
	}
}
