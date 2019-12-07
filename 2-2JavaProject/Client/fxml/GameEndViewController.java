package fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameEndViewController implements Initializable {
	@FXML
	private Label lblRank1;
	@FXML
	private Label lblRank2;
	@FXML
	private Label lblRank3;
	@FXML
	private Label lblRank4;
	@FXML
	private Label lblScore1;
	@FXML
	private Label lblScore2;
	@FXML
	private Label lblScore3;
	@FXML
	private Label lblScore4;
	@FXML
	private Button btnMain;
	@FXML
	private Button btnExit;
	@FXML
	private Text txtPlayerName1;
	@FXML
	private Text txtPlayerName2;
	@FXML
	private Text txtPlayerName3;
	@FXML
	private Text txtPlayerName4;
	@FXML
	private Text txtWordCount1;
	@FXML
	private Text txtWordCount2;
	@FXML
	private Text txtWordCount3;
	@FXML
	private Text txtWordCount4;

	String msg;
	String[] message;
	int arrayRank[] = new int[4];
	int arrayScore[] = new int[4];
	int arryaPoint[] = new int[4];
	public String[] playerName = new String[4];
	public int[] playerScore = new int[4];

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setResult();
		setUi();
	}

	public void setResult() {

		msg = MessageListener.msg;
		message = msg.split(",");

		if (message[0].equals("resultGame")) {
			playerName[0] = message[1];
			playerName[1] = message[3];
			playerName[2] = message[5];
			playerName[3] = message[7];
			playerScore[0] = Integer.parseInt(message[2]);
			playerScore[1] = Integer.parseInt(message[4]);
			playerScore[2] = Integer.parseInt(message[6]);
			playerScore[3] = Integer.parseInt(message[8]);
		}
		sorting();
	}

	public void sorting() {
		int i;
		int j;
		int temp;
		String tmp;
		for (i = 0; i < 4; i++) {
			for (j = i + 1; j < 4; j++) {
				if (playerScore[i] < playerScore[j]) {
					temp = playerScore[i];
					playerScore[i] = playerScore[j];
					playerScore[j] = temp;

					tmp = playerName[i];
					playerName[i] = playerName[j];
					playerName[j] = tmp;
				}
			}
		}
		sortScore();
	}

	public void sortScore() {
		int rank = 1;
		int count = 1;
		int set = 0;
		int[] arrayScore = new int[5];

		arrayScore[0] = playerScore[0];
		arrayScore[1] = playerScore[1];
		arrayScore[2] = playerScore[2];
		arrayScore[3] = playerScore[3];
		arrayScore[4] = -100;

		for (int j = 0; j < 4; j++) {
			if (arrayScore[j] == arrayScore[j + 1]) {
				count++;
				continue;
			}
			for (int k = 0; k < count; k++) {
				arrayRank[set] = rank;
				set++;
			}
			rank += count;
			count = 1;
		}
		setScore();
	}

	public void setScore() {
		for (int i = 0; i < 4; i++) {
			if (arrayRank[i] == 1)
				arryaPoint[i] = 3;
			else if (arrayRank[i] == 2)
				arryaPoint[i] = 2;
			else if (arrayRank[i] == 3)
				arryaPoint[i] = 1;
			else if (arrayRank[i] == 4)
				arryaPoint[i] = 0;
		}
	}

	public void setUi() {
		txtPlayerName1.setText(playerName[0]);
		txtPlayerName2.setText(playerName[1]);
		txtPlayerName3.setText(playerName[2]);
		txtPlayerName4.setText(playerName[3]);
		lblRank1.setText(Integer.toString(arrayRank[0]));
		lblRank2.setText(Integer.toString(arrayRank[1]));
		lblRank3.setText(Integer.toString(arrayRank[2]));
		lblRank4.setText(Integer.toString(arrayRank[3]));
		lblScore1.setText(Integer.toString(arryaPoint[0]));
		lblScore1.setText(Integer.toString(arryaPoint[1]));
		lblScore1.setText(Integer.toString(arryaPoint[2]));
		lblScore1.setText(Integer.toString(arryaPoint[3]));
		txtWordCount1.setText(Integer.toString(playerScore[0]));
		txtWordCount2.setText(Integer.toString(playerScore[1]));
		txtWordCount3.setText(Integer.toString(playerScore[2]));
		txtWordCount4.setText(Integer.toString(playerScore[3]));
	}

	public void enterMain() throws IOException {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoom.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnMain.getScene().getWindow();
		primaryStage.setScene(scene);
	}

	public void eixt() throws IOException {
		if (InGameViewController.checkCount) {
			Client.client.send("exitGameRoom," + StartViewController.account.getUserName() + ","
					+ StartViewController.account.getId());
		} else {
			Client.client.send("exitGame," + StartViewController.account.getId());
		}
		System.exit(0);
	}
}
