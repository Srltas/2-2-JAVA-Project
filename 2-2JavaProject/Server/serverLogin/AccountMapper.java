package serverLogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userName = "LOGINMANAGER";
	String password = "q1w2e3r4";

	public Account getAccountById(String id) {
		Connection connection = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet resultSet = null;

		Account account = new Account();
		String sql = "SELECT USER_ID, USER_PW, USER_NAME, RANKPOINT FROM USERINFO WHERE USER_ID = '" + id
				+ "'";
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

				if (pst2 != null) {
					pst2.close();
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

			String text = null;

			if (resultSet.next()) {
				text = resultSet.getString("USER_ID");
			}
			
			if(text == null) {
				System.out.println("no id find");
				return null;
			}
			
			char[] ttxt = text.toCharArray();
			for (int i = (text.length() / 2); i < text.length(); i++) {
				ttxt[i] = '*';
			}
			account.setId(String.valueOf(ttxt));

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

		String sql = "INSERT INTO USERINFO VALUES('" + id + "','" + password + "','" + phoneNumber + "','" + nickName
				+ "', '0')";

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
	
	public boolean commitGameScore(String id, int score) {
		
		Connection connection = null;
		PreparedStatement pst = null;

		String sql = "UPDATE USERINFO SET RANKPOINT = RANKPOINT + " + score + " WHERE USER_NAME = '" + id + "'";

		
		System.out.println(sql);

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, this.password);

			pst = connection.prepareStatement(sql);
			pst.executeUpdate();

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
