package Login;

public class LoginTest {
	public static void main(String[] args) {
		LoginService login = new LoginService();
		IdFindService idFine = new IdFindService();
		ChangePasswordService changePassword = new ChangePasswordService();
		CreateAccountService createAccount = new CreateAccountService();
		
		System.out.println(login.login("account2", "account2pw"));
		System.out.println(idFine.FindId("0002"));
		System.out.println(changePassword.changePassword("account1", "1000", "1000"));
		System.out.println(createAccount.createAccount("newAccount","newAccountpw", "newAccountpw","supermario","10101"));
	}
}//