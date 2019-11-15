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
			System.out.println("jdbc drvier �ε� ����");
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("����Ŭ ���� ����");
			
			int inEMPNO;//�����ȣ
			String inEMPNAME;//����̸�
			String inTITLE;//��å
			int inMANAGER;//���
			int inSALARY;//�޿�
			int inDNO;//�μ�
			
			Scanner scanner = new Scanner(System.in);
			Scanner scanner2 = new Scanner(System.in);
			
			System.out.print("����̸� >> ");
			inEMPNAME = scanner.nextLine();
			System.out.println(inEMPNAME);
			
			System.out.print("��å >> ");
			inTITLE = scanner2.nextLine();
			System.out.println(inTITLE);
			
			System.out.print("�����ȣ >> ");
			inEMPNO = scanner.nextInt();
			System.out.println(inEMPNO);
			
			System.out.print("�Ŵ��� >> ");
			inMANAGER = scanner.nextInt();
			System.out.println(inMANAGER);
			
			System.out.print("�޿� >> ");
			inSALARY = scanner.nextInt();
			System.out.println(inSALARY);
			
			System.out.print("�μ� >> ");
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
				System.out.println("���� ����");
			}else {
				System.out.println("���� ����");
			}
		} catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("jdbc driver �ε� ����");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
					System.out.println("����Ŭ ���� ����");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			/*
			if(conn != null) {
				try {
					pstmt.close();
					System.out.println("����Ŭ ���� ����");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			*/
		}
	}
}
