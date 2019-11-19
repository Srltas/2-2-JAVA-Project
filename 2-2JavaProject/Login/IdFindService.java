package Login;

public class IdFindService {
	public boolean FindId(String phoneNumber) {
		if (phoneNumber != null) {
			Account account = new AccountMapper().getIdByPhoneNumber(phoneNumber);
			if (account.getId() != null) {
				char[] secureId = account.getId().toCharArray();
				for (int index = (secureId.length)/2; index < secureId.length;index++) {
					secureId[index] = '*';
				}
				secureId.toString();
				System.out.println(secureId);
				return true;
			}
		}
		return false;
	}
}//
