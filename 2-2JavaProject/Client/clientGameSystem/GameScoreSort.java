package clientGameSystem;

import fxml.GameEndViewController;

public class GameScoreSort {

	public void sorting() {
		System.out.println("sorting enter");
		int i;
		int j;
		int temp;
		String tmp;
		int min;
		
		for (i = 0; i<5;i++) {
			System.out.println("for - i");
			min=i;
			for (j = i+1;j<5;j++) {
				System.out.println("for - j");
				if(GameEndViewController.playerScore[min]<GameEndViewController.playerScore[j]) {
					System.out.println("for - min = j");
					min = j;
				}
			}
			temp = GameEndViewController.playerScore[min];
			GameEndViewController.playerScore[min] = GameEndViewController.playerScore[j];
			GameEndViewController.playerScore[j] = temp;
			System.out.println(GameEndViewController.playerScore[j] + "<->" + GameEndViewController.playerScore[min]);
			tmp = GameEndViewController.playerName[min];
			GameEndViewController.playerName[min] = GameEndViewController.playerName[j];
			GameEndViewController.playerName[j] = tmp;
			System.out.println(GameEndViewController.playerName[j] + "<->" + GameEndViewController.playerName[min]);
		}
	}
}
