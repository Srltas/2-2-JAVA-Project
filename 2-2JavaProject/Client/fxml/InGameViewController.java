package fxml;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class InGameViewController implements Initializable {
	@FXML
	private ImageView imgOut1;
	@FXML
	private ImageView imgOut2;
	@FXML
	private ImageView imgOut3;
	@FXML
	private ImageView imgOut4;
	@FXML
	private ImageView imgUser1;
	@FXML
	private ImageView imgUser2;
	@FXML
	private ImageView imgUser3;
	@FXML 
	private ImageView imgUser4;
	@FXML
	private Label lblWordWarning;
	@FXML
	private Button btnChat;
	@FXML
	private Button btnWord;
	@FXML
	private TextArea txtAreaChat;
	@FXML
	private TextField txtFieldChat;
	@FXML
	private TextField txtFielWord;
	@FXML
	private Text txtWord;
	@FXML
	private Text txtTurnUserName;
	@FXML
	private Text txtUser1Name;
	@FXML
	private Text txtUser2Name;
	@FXML
	private Text txtUser3Name;
	@FXML
	private Text txtUser4Name;
	@FXML
	private Label lblTime;

	InputStream inStream;
	DataInputStream dataInStream;

	private Timeline timeline;
	private static final Integer STARTTIME = 15;
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME * 100);

	boolean loop = true;
	String msg;
	String[] message;
	public static boolean checkCount = false;	//게임방에 들어오면 true
	int gameTurn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Loop();
		checkCount = true;
	}

	public void time() {
		lblTime.textProperty().bind(timeSeconds.divide(100).asString());
		if (timeline != null) {
			timeline.stop();
		}
		timeSeconds.set((STARTTIME + 1) * 100);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));
		timeline.playFromStart();
	}

	public void Loop() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (loop) {
					msg = MessageListener.msg;
					message = msg.split(",");

					if (message[0].equals("enterGameRoom")) {
						if (message[1].equals("1"))
							enterPlayer1(message[2]);
						else if (message[1].equals("2"))
							enterPlayer2(message[2]);
						else if (message[1].equals("3"))
							enterPlayer3(message[2]);
						else if (message[1].equals("4")) {
							enterPlayer4(message[2]);
						}
					} else if (message[0].equals("chat")) {
						String chatMessage = message[1] + "\n";
						Platform.runLater(() -> {
							txtAreaChat.appendText(chatMessage);
						});
						MessageListener.msg = " ,";
					} else if (message[0].equals("exitGameRoom")) {
						if (message[1].equals("1"))
							exitPlayer1();
						else if (message[1].equals("2"))
							exitPlayer2();
						else if (message[1].equals("3"))
							exitPlayer3();
						else if (message[1].equals("4"))
							exitPlayer4();
					} else if (message[0].equals("readyGame")) {
						txtWord.setText("Ready!!");
						Platform.runLater(() -> {
							time();
							Client.client.send("startGame,");
						});
						MessageListener.msg = " ,";
					} else if (message[0].equals("startWord")) {
						txtWord.setText(message[1]);
					} else if (message[0].equals(" ")) {
						//대기
					}
				}
			}
		};
		thread.start();
	}

	public void sendWord() {

		String nextWord = txtFielWord.getText();
		String word = txtWord.getText();
		txtFielWord.setText(""); // txtFielWord 비우기

		if (nextWord.length() > 1 && nextWord.length() < 6) {
			if (gameTurn != 0) {
				char[] arrayWord = word.toCharArray(); // 앞에 단어 문자열 배열변환
				char[] arrayNextWord = nextWord.toCharArray(); // 이을 단어 문자열 배열변환
				if (arrayWord[arrayWord.length - 1] == arrayNextWord[0]) {
					Client.client.send("word," + nextWord);
				} else {
					lblWordWarning.setText("다시 입력하세요.");
				}
			} else {
				Client.client.send("word," + nextWord);
			}
		} else {
			lblWordWarning.setText("다시 입력하세요.");
		}

	}

	public void sendMessage() {
		Client.client.send("chat," + StartViewController.account.getUserName() + " : " + txtFieldChat.getText());
		txtFieldChat.setText("");
	}

	public void enterPlayer1(String name) {
		imgUser1.setOpacity(1);
		txtUser1Name.setText(name);
	}

	public void enterPlayer2(String name) {
		imgUser2.setOpacity(1);
		txtUser2Name.setText(name);
	}

	public void enterPlayer3(String name) {
		imgUser3.setOpacity(1);
		txtUser3Name.setText(name);
	}

	public void enterPlayer4(String name) {
		imgUser4.setOpacity(1);
		txtUser4Name.setText(name);
	}

	public void exitPlayer1() {
		imgUser1.setOpacity(0);
		txtUser1Name.setText("");
	}

	public void exitPlayer2() {
		imgUser2.setOpacity(0);
		txtUser2Name.setText("");
	}

	public void exitPlayer3() {
		imgUser3.setOpacity(0);
		txtUser3Name.setText("");
	}

	public void exitPlayer4() {
		imgUser4.setOpacity(0);
		txtUser4Name.setText("");
	}
}