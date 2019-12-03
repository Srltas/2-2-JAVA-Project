package serverLogin;

import java.util.HashSet;
import java.util.Set;

public class DeniedOverlapLoginService {
	private static Set<String> onlineUserId = new HashSet<String>();
	
	public void logInSuccess(String userId) {
		//로그인 성공시 onlineUserId에 값 입력
	}
	
	public void isOverlap(String userId) {
		//중복 로그인인지 확인하는 메소드
	}
	
	public void logOutSuccess(String userId) {
		//로그아웃시 onlineUserId에서 id 제거
	}
	
	public void serverOff() {
		//해쉬셋을 비워버림
	}
}