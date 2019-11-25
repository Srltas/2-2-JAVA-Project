package serverSocketConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Users {
	private Socket self;
	private BufferedWriter out;
	private BufferedReader in;
	String message;
	
	public Users(Socket client) throws Exception{
		this.self = client;
		this.out = new BufferedWriter(new OutputStreamWriter(self.getOutputStream()));
		this.in = new BufferedReader(new InputStreamReader(self.getInputStream()));
		reader();
	}
	
	public void reader()throws Exception{
		Thread thread = new Thread() {
			public void run() {
				try {
					message = in.readLine();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	public void writer(String message) throws Exception{
		out.write(message);
		out.flush();
	}
	
	public void close() throws Exception{
		if(out != null) {
			out.close();
		}
		if(self != null) {
			self.close();
		}
	}
	
	public String getMessage() {
		return this.message + ","+ self.toString();
	}
}
