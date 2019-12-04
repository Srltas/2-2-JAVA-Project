package fxml;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import clientLoginData.Account;
import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class InGameViewController implements Initializable{
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
	private Text txtUser1Name;
	@FXML
	private Text txtUser2Name;
	@FXML
	private Text txtUser3Name;
	@FXML
	private Text txtUser4Name;
	@FXML
	private Text txtUser1Heart;
	@FXML
	private Text txtUser2Heart;
	@FXML
	private Text txtUser3Heart;
	@FXML
	private Text txtUser4Heart;
	@FXML
	private ProgressBar pgbTime;
	
	InputStream inStream;
	DataInputStream dataInStream;
	
	boolean loop = true;
	String msg;
	String[] message;
	public static int checkCount = 0;
	Account account = new Account();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Loop();
		checkCount = 1;
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
					} else if(message[0].equals("chat")) {

						String chatMessage = account.getId() +" : "+ message[1] + "\n";
						Platform.runLater(() -> {
							txtAreaChat.appendText(chatMessage);

						});
						MessageListener.msg = " ,";
					} else if(message[0].equals("exitGameRoom")) {
						if(message[1].equals("1"))
							exitPlayer1();
						else if(message[1].equals("2"))
							exitPlayer2();
						else if(message[1].equals("3"))
							exitPlayer3();
						else if(message[1].equals("4"))
							exitPlayer4();
						
					} else if(message[0].equals(" ")) {
						
					}
				}
			}
		};
		thread.start();
	}
	
	
	public void sendMessage() {
		Client.client.send("chat," + txtFieldChat.getText());
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