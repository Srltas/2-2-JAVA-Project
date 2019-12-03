package fxml;

import java.net.URL;
import java.util.ResourceBundle;

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
	
	boolean loop = true;
	String msg;
	String[] message;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Loop();
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
							setOpacityPlayer1();
						else if (message[1].equals("2"))
							setOpacityPlayer2();
						else if (message[1].equals("3"))
							setOpacityPlayer3();
						else if (message[1].equals("4")) {
							setOpacityPlayer4();
						}
					} else if(message[0].equals("chat")) {
						System.out.println("Hi~");
						System.out.println(message[1]);
						String text = message[1];
						//MessageListener.msg = "";
						Platform.runLater(() -> {
							txtAreaChat.appendText(text);
						});
						MessageListener.msg = "";
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
	
	public void receiveMessage() {
		
	}
	
	public void setOpacityPlayer1() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(0);
		imgUser3.setOpacity(0);
		imgUser4.setOpacity(0);
	}

	public void setOpacityPlayer2() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(1);
		imgUser3.setOpacity(0);
		imgUser4.setOpacity(0);
	}

	public void setOpacityPlayer3() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(1);
		imgUser3.setOpacity(1);
		imgUser4.setOpacity(0);
	}

	public void setOpacityPlayer4() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(1);
		imgUser3.setOpacity(1);
		imgUser4.setOpacity(1);
		txtFielWord.setDisable(false);
		btnWord.setDisable(false);
	}
}