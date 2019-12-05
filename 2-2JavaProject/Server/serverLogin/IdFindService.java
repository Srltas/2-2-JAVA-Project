package serverLogin;

public class IdFindService {
	public boolean FindId(String phoneNumber) {
		if (phoneNumber != null) {
			Account account = new AccountMapper().getIdByPhoneNumber(phoneNumber);
			
			String text = account.getId();
			char[] ttxt = text.toCharArray();
			for (int i = (text.length()/2); i<text.length(); i++) {
				ttxt[i] = '*';
			}
			String txt = String.valueOf(ttxt);
			account.setId(txt);
			
			if (account.getId() != null) {
				System.out.println(account.getId());
				return true;
			}
		}
		return false;
	}
}//
