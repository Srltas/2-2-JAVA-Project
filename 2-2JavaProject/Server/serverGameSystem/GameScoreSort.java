package serverGameSystem;

public class GameScoreSort {
	public static String[] playerName = new String[4];
	public static int[] playerScore = new int[4];
	public static int index = 0;
	public static boolean check = true;

	public static void sorting() {
		System.out.println("sorting enter");
		check = false;
		int i;
		int j;
		int temp;
		String tmp;
		int min;
		
		for (i = 0; i<4;i++) {
			min=i;
			for (j = i+1;j<=4;j++) {
				if(playerScore[min]<playerScore[j]) {
					min = j;
				}
			}
			temp = playerScore[min];
			playerScore[min] = playerScore[j];
			playerScore[j] = temp;
			
			tmp = playerName[min];
			playerName[min] = playerName[j];
			playerName[j] = tmp;
		}
	}
}
