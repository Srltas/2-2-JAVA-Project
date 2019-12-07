package clientGameSystem;

import java.util.HashSet;
import clientRequestApi.API2;
public class GameScoreCounter {

	static HashSet<String> wordList = new HashSet<String>();

	public static int score = 0;
	static API2 apiControl = new API2();
	

	public static boolean scoreControl(String word) {
		if (addList(word)) {
			score += 1;
			return true;
		} else {
			score -= 1;
			return false;
		}
	}

	public static boolean addList(String word) {
		if (!wordSearchResult(word)) {
			return false;
		} else {
			wordList.add(word);
			return true;
		}
	}

	public static boolean wordSearchResult(String word) {
		if(search(word)) {
			return false;
		} else {
			if(apiControl.CallAPI(word) == 1) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static boolean search(String word) {
		return (wordList.contains(word));
	}
}
