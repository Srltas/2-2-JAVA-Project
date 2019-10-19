package SocketWithDBMS;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectServer {
	public static void main(String[] args) {
		DatabaseConnectServer dbcs = new DatabaseConnectServer();
		try {
			dbcs.createServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createServer() throws Exception {
		Socket connect = null;
		ServerSocket server = new ServerSocket(9999);
		System.out.println("server ready");
		connect = server.accept();
		
		receiveSQL(connect);
		
		server.close();
	}
	
	public void receiveSQL(Socket connect) throws InterruptedException, IOException, ClassNotFoundException, SQLException {
		String driver, url, id, password;
		InputStream inData = connect.getInputStream();
		InputStreamReader inDataRead = new InputStreamReader(inData);
		BufferedReader br = new BufferedReader(inDataRead);

		driver = br.readLine();
		System.out.println("driver : " + driver);

		url = br.readLine();
		System.out.println("url : " + url);

		id = br.readLine();
		System.out.println("id : " + id);

		password = br.readLine();
		System.out.println("password : " + password);
		sendSQLToDBMS(driver,url,id,password, connect);
	}
	
	public void sendSQLToDBMS(String driver, String url, String id, String password, Socket connect) throws InterruptedException, IOException, ClassNotFoundException, SQLException {
		OutputStream outData = connect.getOutputStream();
		OutputStreamWriter outDataWrite = new OutputStreamWriter(outData);
		BufferedWriter bw = new BufferedWriter(outDataWrite);
		PrintWriter out = new PrintWriter(bw);
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Class.forName(driver);
		con = DriverManager.getConnection(url, id, password);

		String checker = "SELECT * FROM R";

		pst = con.prepareStatement(checker);
		rs = pst.executeQuery();
		
		String pakageArray = null;
		
		while (rs.next()) {
			pakageArray = rs.getInt("A") + ", " + rs.getString("B") + ", " + rs.getString("C");
			System.out.println(pakageArray);
			out.println(pakageArray);
			out.flush();
			Thread.sleep(1000);
			pakageArray = null;
		}
		
		String over = null;
		out.println(over);
		out.flush();
	}
}
