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
		for (i = 0; i<4;i++) {
			for (j = i+1;j<4;j++) {
				if(playerScore[i]<playerScore[j]) {
					temp = playerScore[i];
					playerScore[i] = playerScore[j];
					playerScore[j] = temp;
					
					tmp = playerName[i];
					playerName[i] = playerName[j];
					playerName[j] = tmp;
				}
			}
		}
	}
}
