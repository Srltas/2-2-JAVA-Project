package oracleDBpack;

import java.sql.DriverManager;
import java.sql.SQLException;


public class Oracle {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "KIM";
		String password = "bluesky";
		try {
			Class.forName(driver);
			System.out.println("jdbc driver");
			DriverManager.getConnection(url, user, password);
			System.out.println("oracle connect success");
		} catch (ClassNotFoundException e) {
			System.out.println("CNF ex");
		} catch (SQLException ex) {
			System.out.println("SQL ex");
		}
	}
}
