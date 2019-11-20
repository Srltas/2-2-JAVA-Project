package serverLogin;

public class IdFindService {
	public boolean FindId(String phoneNumber) {
		if (phoneNumber != null) {
			Account account = new AccountMapper().getIdByPhoneNumber(phoneNumber);
			if (account.getId() != null) {
				System.out.println(account.getId());
				return true;
			}
		}
		return false;
	}
}//
