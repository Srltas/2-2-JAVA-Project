package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {
	// public 계정 계정조회 (String id)
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userName = "LEE";
	String password = "redsun";

	public Account getAccountById(String id) {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet resultSet = null;

		Account account = new Account();
		String sql = "SELECT USER_ID, USER_PW FROM USERINFO WHERE USER_ID = '" + id + "'";
		System.out.println(sql);
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, password);

			pst = connection.prepareStatement(sql);
			// pst.setString(1, id);

			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				account.setId(resultSet.getString("USER_ID"));
				account.setPassword(resultSet.getString("USER_PW"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return account;
	}

	public Account getIdByPhoneNumber(String phoneNumber) {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet resultSet = null;
		Account account = new Account();

		String sql = "SELECT USER_ID FROM USERINFO WHERE USER_PHONE = '" + phoneNumber + "'";
		System.out.println(sql);

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, password);

			pst = connection.prepareStatement(sql);

			resultSet = pst.executeQuery();

			if (resultSet.next()) {
				account.setId(resultSet.getString("USER_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				pst.close();
				connection.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return account;
	}

	public Account changePasswordById(String id, String password) {
		// 데이터 베이스 접속에 필요한 Connection, PreparedStatement, ResultSet 선언
		Connection connection = null;
		PreparedStatement pst = null;
		Account account = new Account();

		// String sql = "UPDATE USERINFO SET USER_PW=(변경할 PW) WHERE=(변경할 ID)"
		String sql = "UPDATE USERINFO SET USER_PW = '" + password + "' WHERE USER_ID = '" + id + "'";
		
		System.out.println(sql);
		
		// 데이터 베이스에 접속하여 resultSet.next(); id와 pw를 가져옴
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, this.password);

			pst = connection.prepareStatement(sql);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return account;
	}
}
