package serverLogin;

public class IdFindService {
	public Account FindId(String phoneNumber) {
		if (phoneNumber != null) {
			Account account = new AccountMapper().getIdByPhoneNumber(phoneNumber);
			try {
				String text = account.getId();
				char[] ttxt = text.toCharArray();
				for (int i = (text.length()/2); i<text.length(); i++) {
					ttxt[i] = '*';
				}
				String txt = String.valueOf(ttxt);
				account.setId(txt);
			} catch(Exception e) {
				return null;
			}
			if (account.getId() != null) {
				System.out.println(account.getId());
				return account;
			}
		}
		return null;
	}
}