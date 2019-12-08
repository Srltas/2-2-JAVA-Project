package clientGameSystem;

import java.util.HashSet;
import clientRequestApi.API2;
public class GameScoreCounter {

	static HashSet<String> wordList = new HashSet<String>();

	public static int score = 0;
	static API2 apiControl = new API2();
	

	public static int scoreControl(String word) {
		if (addList(word)) {
			score += 1;
			System.out.println("score : +1");
			return 1;
		} else {
			score -= 1;
			System.out.println("score : -1");
			return 0;
		}
	}

	public static boolean addList(String word) {
		if (wordSearchResult(word)==false) {
			System.out.println("add : false");
			return false;
		} else {
			wordList.add(word);
			System.out.println("add : true");
			return true;
		}
	}

	public static boolean wordSearchResult(String word) {
		if(search(word)) {
			System.out.println("contains : true");
			return false;
		} else {
			System.out.println("contains : false");
			if(apiControl.CallAPI(word) == 1) {
				System.out.println("API : true");
				return true;
			} else {
				System.out.println("API : false");
				return false;
			}
		}
	}
	
	public static void endGameTrigger() {
		wordList.removeAll(wordList);
		score = 0;
		System.out.println("reset complete");
	}
	
	public static boolean search(String word) {
		return (wordList.contains(word));
	}
}
