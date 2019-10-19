package SocketWithDBMS;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientProgram {
	public static void main(String[] args) {
		ClientProgram client = new ClientProgram();
		try {
			client.sendSQLInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//throws Exception = 예외 임시 처리용, 나중에 구분지어서 해결할 예정.
	public void sendSQLInfo() throws IOException, InterruptedException {
		System.out.println("try to connect");
		Socket connect = new Socket("192.168.219.190", 9999);
		System.out.println("Connect complete");

		OutputStream outputData = connect.getOutputStream();
		OutputStreamWriter outWriter = new OutputStreamWriter(outputData);
		BufferedWriter bw = new BufferedWriter(outWriter);
		PrintWriter out = new PrintWriter(bw);
		String dataString1, dataString2, dataString3, dataString4;

		Thread.sleep(1000);

		dataString1 = "oracle.jdbc.driver.OracleDriver";
		dataString2 = "jdbc:oracle:thin:@localhost:1521:xe";
		dataString3 = "LEE";
		dataString4 = "redsun";

		System.out.println("send to server which DBMS query");
		
		out.println(dataString1);
		out.flush();
		System.out.println("send1");

		out.println(dataString2);
		out.flush();
		System.out.println("send2");

		out.println(dataString3);
		out.flush();
		System.out.println("send3");

		out.println(dataString4);
		out.flush();
		System.out.println("send4");

		receiveDataBaseInfo(connect);
		
		connect.close();
	}
	//throws Exception = 예외 임시 처리용, 나중에 구분지어서 해결할 예정.
	public void receiveDataBaseInfo(Socket connect) throws IOException {
		InputStream inputData = connect.getInputStream();
		InputStreamReader inputReader = new InputStreamReader(inputData);
		BufferedReader br = new BufferedReader(inputReader);

		String receive = br.readLine();

		while (receive != null) {
			System.out.println(receive);
			try {
				receive = br.readLine();
			} catch (SocketException socket) {
				System.out.println("end");
				System.exit(0);
			}
		}
	}
}
