package LoginUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class LoginUIServer {
	public static void main(String[] args) 
	{
		LoginUIServer server = new LoginUIServer();
		try
		{
			server.ss = new ServerSocket(9876);
			System.out.println("Server > Server Socket is created!");
			
			while(true)
			{
				Socket socket = server.ss.accept();
				ConnectedClient c = new ConnectedClient(socket);
				server.clients.add(c);
				c.start();
			}
		}
		catch(SocketException e)
		{
			
		}
		catch(Exception e)
		{
			
		}
	}
	
	ServerSocket ss = null;
	static ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
}

class ConnectedClient extends Thread
{
	Socket socket;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;
	
	ConnectedClient(Socket _s)
	{
		socket = _s;
	}
	
	public void run()
	{
		try
		{
			System.out.println("Server> " + this.socket.toString() + "에서의 접속이 연결되었습니다.");
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			dataOutStream.writeUTF("Welcome to this Server.");
			while(true)
			{
				String msg = dataInStream.readUTF();	//문자 받기
				
				String number = msg.substring(0,1);
				System.out.println(number);
				String messageBody = msg.substring(1);
				System.out.println(messageBody);
				if(number.equals("0")) {
					System.out.println("로그인 정보입니다.");
					String id = messageBody.substring(0,msg.lastIndexOf(",")-1);
					String pw = messageBody.substring(msg.lastIndexOf(","));
					System.out.println(id);
					System.out.println(pw);
				}else if(number.equals("1")) {
					System.out.println("단어 정보입니다.");
				}else if(number.equals("2")) {
					System.out.println("채팅 정보입니다.");
				}
				
				//모든 클라이언트에게 문자 전송하기
				/*for(ConnectedClient client : LoginUIServer.clients) {
					client.dataOutStream.writeUTF(msg);
				}*/
				
				System.out.println("Server> " + this.socket.toString() + ": " + msg);
			}
		}
		catch(IOException e) {}
	}
}
