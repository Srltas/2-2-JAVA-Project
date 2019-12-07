package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.MessageListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setResult();
	}

	public void setResult() {
		String playerName1;
		String playerName2;
		String playerName3;
		String playerName4;
		String wordCount1;
		String wordCount2;
		String wordCount3;
		String wordCount4;
		
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
	}
}
