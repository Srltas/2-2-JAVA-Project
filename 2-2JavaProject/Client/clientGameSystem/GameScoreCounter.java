package clientGameSystem;

import java.util.HashSet;
import java.util.Iterator;

public class GameScoreCounter {

	HashSet<String> wordList = new HashSet<String>();
	Iterator<String> wordListSearch;

	int score = 0;

	public void scoreControl(String word) {
		if (addList(word)) {
			score += 1;
		} else {
			score -= 1;
		}
	}

	public boolean addList(String word) {
		if (search(word)) {
			return false;
		} else {
			wordList.add(word);
			return true;
		}
	}

	public boolean search(String word) {
		return (wordList.contains(word));
	}

}
