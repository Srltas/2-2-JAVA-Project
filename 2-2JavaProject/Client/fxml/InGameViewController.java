package fxml;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import clientGameSystem.GameEndTimer;
import clientGameSystem.GameReadyTimer;
import clientGameSystem.GameTimer;
import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import clientGameSystem.GameScoreCounter;


public class InGameViewController implements Initializable {
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
	private Button btnResult;
	@FXML
	private TextArea txtAreaChat;
	@FXML
	private TextField txtFieldChat;
	@FXML
	private TextField txtFieldWord;
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
	private static final Integer READYTIME = 10;
	private static final Integer GAMETIME = 60;
	private static final Integer ENDTIME = 3;
	private IntegerProperty endTimeSeconds = new SimpleIntegerProperty(ENDTIME * 100);
	private IntegerProperty readyTimeSeconds = new SimpleIntegerProperty(READYTIME * 100);
	private IntegerProperty gameTimeSeconds = new SimpleIntegerProperty(GAMETIME * 100);

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

	
	public void endTime() {
		lblTime.textProperty().bind(endTimeSeconds.divide(100).asString());
		if (timeline != null) {
			timeline.stop();
		}
		readyTimeSeconds.set((ENDTIME + 1) * 100);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(ENDTIME + 1), new KeyValue(endTimeSeconds, 0)));
		timeline.playFromStart();
	}
	
	public void readyTime() {
		lblTime.textProperty().bind(readyTimeSeconds.divide(100).asString());
		if (timeline != null) {
			timeline.stop();
		}
		readyTimeSeconds.set((READYTIME + 1) * 100);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(READYTIME + 1), new KeyValue(readyTimeSeconds, 0)));
		timeline.playFromStart();
	}
	
	public void gameTime() {
		lblTime.textProperty().bind(gameTimeSeconds.divide(100).asString());
		if (timeline != null) {
			timeline.stop();
		}
		gameTimeSeconds.set((GAMETIME + 1) * 100);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(GAMETIME + 1), new KeyValue(gameTimeSeconds, 0)));
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
							readyTime();
							new GameReadyTimer().timerSetter();
						});
						MessageListener.msg = " ,";
					} else if (message[0].equals("startWord")) {
						txtWord.setText(message[1]);
						btnWord.setDisable(false);
						txtFieldWord.setDisable(false);
						Platform.runLater(() -> {
							gameTime();
							new GameTimer().timerSetter(StartViewController.account.getUserName(),Integer.toString(GameScoreCounter.score));
						});
						MessageListener.msg = " ,";
					} else if(message[0].equals("endGame")) {
						btnWord.setDisable(true);
						txtFieldWord.setDisable(true);
						txtWord.setText("End!!");
						Platform.runLater(() ->{
							endTime();
							new GameEndTimer().timerSetter();
						});
						MessageListener.msg = " ,";
					} else if(message[0].equals("onResultButton")) {
						btnResult.setDisable(false);
						btnResult.setOpacity(1);
						MessageListener.msg = " ,";
					}
					else if (message[0].equals(" ")) {
						//대기
					}
				}
			}
		};
		thread.start();
	}

	public void sendWord() {
		String nextWord = txtFieldWord.getText();
		String word = txtWord.getText();
		txtFieldWord.setText(""); // txtFielWord 비우기
		
		if (nextWord.length()>1 && nextWord.length()<6) {
			char[] stageWordChar = word.toCharArray();
			char[] nextWordChar = nextWord.toCharArray();
			
			if (stageWordChar[word.length()-1] == nextWordChar[0]) {
				System.out.println(word);
				System.out.println(nextWord);
				System.out.println(GameScoreCounter.score);
				if((GameScoreCounter.scoreControl(nextWord)) == true) {
					System.out.println("if check");
					System.out.println(word);
					System.out.println(nextWord);
					txtWord.setText(nextWord);					
				}
			}
		}
	}
	
	public void showResult() throws IOException {
		Client.client.send("resultGame,");
		
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/GameEndView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnResult.getScene().getWindow();
		primaryStage.setScene(scene);
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