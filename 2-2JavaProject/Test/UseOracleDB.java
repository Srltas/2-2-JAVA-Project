package Test;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UseOracleDB {
	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "LEE";
		String password = "redsun";
		
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		Statement st = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,id,password);
			
			/*
			int num = 5;
			String a = "G";
			String b = "N";
			
			String Quarry = "insert into R values(?,?,?)";
			pst = conn.prepareStatement(Quarry);
			
			pst.setInt(1, num);
			pst.setString(2, a);
			pst.setString(3, b);	
			*/
			
			String qq = "SELECT * FROM R";
			
			pst2 = conn.prepareStatement(qq);
					
			rs = pst.executeQuery();
			//pst.executeUpdate();
			
			while (rs.next()) {
				System.out.println(rs.getInt("A")+", "+rs.getString("B")+", "+rs.getString("C"));
			}
		} catch (ClassNotFoundException classNot) {
			classNot.printStackTrace();
		} catch (SQLException sqlNot) {
			sqlNot.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
