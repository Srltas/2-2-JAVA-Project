package serverGameSystem;

public class GameScoreSort {
	public static String[] playerName = new String[5];
	public static int[] playerScore = new int[5];
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
		
		for (i = 0; i<5;i++) {
			System.out.println("for - i");
			min=i;
			for (j = i+1;j<5;j++) {
				System.out.println("for - j");
				if(playerScore[min]<playerScore[j]) {
					System.out.println("for - min = j");
					min = j;
				}
			}
			temp = playerScore[min];
			playerScore[min] = playerScore[j];
			playerScore[j] = temp;
			System.out.println(playerScore[j] + "<->" + playerScore[min]);
			tmp = playerName[min];
			playerName[min] = playerName[j];
			playerName[j] = tmp;
			System.out.println(playerName[j] + "<->" + playerName[min]);
		}
	}
}
