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
	String playerName1;
	String playerName2;
	String playerName3;
	String playerName4;
	String wordCount1;
	String wordCount2;
	String wordCount3;
	String wordCount4;
	int arrayRank[] = new int[4];
	int arrayScore[] = new int[4];
	int index = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setResult();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setUi();
	}

	public void setResult() {
		
		msg = MessageListener.msg;
		message = msg.split(",");
		
		if(message[0].equals("resultGame")) {
			playerName1 = message[1];
			wordCount1 = message[2];
			playerName2 = message[3];
			wordCount2 = message[4];
			playerName3 = message[5];
			wordCount3 = message[6];
			playerName4 = message[7];
			wordCount4 = message[8];
		}
		sortScore();
	}
	
	public void sortScore() {
		int rank = 1;
		int count = 1;
		int set = 0;
		int[] arrayScore = new int[5];
		
		arrayScore[0] = Integer.parseInt(wordCount1);
		arrayScore[1] = Integer.parseInt(wordCount2);
		arrayScore[2] = Integer.parseInt(wordCount3);
		arrayScore[3] = Integer.parseInt(wordCount4);
		arrayScore[4] = -1;
		
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
			if(arrayRank[i] == 1)
				arrayScore[i] = 3;
			else if(arrayRank[i] == 2)
				arrayScore[i] = 2;
			else if(arrayRank[i] == 3)
				arrayScore[i] = 1;
			else if(arrayRank[i] == 4)
				arrayScore[i] = 0;
		}
	}
	
	public void setUi(){
		txtPlayerName1.setText(playerName1);
		txtPlayerName2.setText(playerName2);
		txtPlayerName3.setText(playerName3);
		txtPlayerName4.setText(playerName4);
		lblRank1.setText(Integer.toString(arrayRank[0]));
		lblRank2.setText(Integer.toString(arrayRank[1]));
		lblRank3.setText(Integer.toString(arrayRank[2]));
		lblRank4.setText(Integer.toString(arrayRank[3]));
		lblScore1.setText(Integer.toString(arrayScore[0]));
		lblScore1.setText(Integer.toString(arrayScore[1]));
		lblScore1.setText(Integer.toString(arrayScore[2]));
		lblScore1.setText(Integer.toString(arrayScore[3]));
		txtWordCount1.setText(wordCount1);
		txtWordCount2.setText(wordCount2);
		txtWordCount3.setText(wordCount3);
		txtWordCount4.setText(wordCount4);
	}
	
	public void enterMain() throws IOException {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoom.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnMain.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	
	public void eixt() throws IOException {
		if (InGameViewController.checkCount) {
			Client.client.send("exitGameRoom," + StartViewController.account.getUserName() + "," +StartViewController.account.getId());
		} else {
			Client.client.send("exitGame," + StartViewController.account.getId());
		}
		System.exit(0);
	}
}
