package serverLogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userName = "LEE";
	String password = "redsun";

	public Account getAccountById(String id) {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet resultSet = null;

		Account account = new Account();
		String sql = "SELECT USER_ID, USER_PW, USER_NAME, RANKPOINT FROM USERINFO WHERE USER_ID = '" + id + "'";
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
				account.setUserName(resultSet.getString("USER_NAME"));
				account.setRankPoint(resultSet.getInt("RANKPOINT"));
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

	public void changePasswordById(String id, String password) {
		Connection connection = null;
		PreparedStatement pst = null;

		String sql = "UPDATE USERINFO SET USER_PW = '" + password + "' WHERE USER_ID = '" + id + "'";

		System.out.println(sql);

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
	}

	public boolean createAccount(String id, String password, String nickName, String phoneNumber) {
		Connection connection = null;
		PreparedStatement pst = null;

		String sql = "INSERT INTO USERINFO VALUES('" + id + "','" + password + "','" + phoneNumber + "','"+ nickName +"', '0')";

		System.out.println(sql);

		try {
			
			Class.forName(driver);
			
			connection = DriverManager.getConnection(url, userName, this.password);
			
			pst = connection.prepareStatement(sql);
			
			pst.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
