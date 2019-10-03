package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OracleTest {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "C##KIM";
		String password = "bluesky";
		try {
			Class.forName(driver);
			System.out.println("jdbc drvier 로딩 성공");
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("오라클 연결 성공");
			
			int inEMPNO;//사원번호
			String inEMPNAME;//사원이름
			String inTITLE;//직책
			int inMANAGER;//상사
			int inSALARY;//급여
			int inDNO;//부서
			
			Scanner scanner = new Scanner(System.in);
			Scanner scanner2 = new Scanner(System.in);
			
			System.out.print("사원이름 >> ");
			inEMPNAME = scanner.nextLine();
			System.out.println(inEMPNAME);
			
			System.out.print("직책 >> ");
			inTITLE = scanner2.nextLine();
			System.out.println(inTITLE);
			
			System.out.print("사원번호 >> ");
			inEMPNO = scanner.nextInt();
			System.out.println(inEMPNO);
			
			System.out.print("매니저 >> ");
			inMANAGER = scanner.nextInt();
			System.out.println(inMANAGER);
			
			System.out.print("급여 >> ");
			inSALARY = scanner.nextInt();
			System.out.println(inSALARY);
			
			System.out.print("부서 >> ");
			inDNO = scanner.nextInt();
			System.out.println(inDNO);
			
			String sql= "INSERT INTO EMPLOYEE(EMPNO,EMPNAME,TITLE,MANAGER,SALARY,DNO) VALUES(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inEMPNO);
			pstmt.setString(2, inEMPNAME);
			pstmt.setString(3, inTITLE);
			pstmt.setInt(4,inMANAGER);
			pstmt.setInt(5, inSALARY);
			pstmt.setInt(6, inDNO);
			
			int count = pstmt.executeUpdate();
			if(count == 0) {
				System.out.println("삽입 실패");
			}else {
				System.out.println("삽입 성공");
			}
		} catch(ClassNotFoundException e){
			System.out.println("jdbc driver 로딩 실패");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
					System.out.println("오라클 연결 종료");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			/*
			if(conn != null) {
				try {
					pstmt.close();
					System.out.println("오라클 연결 종료");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			*/
		}
	}
}
