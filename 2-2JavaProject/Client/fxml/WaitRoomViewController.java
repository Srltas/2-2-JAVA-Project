package fxml;

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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WaitRoomViewController implements Initializable {
	@FXML
	private Button btnStart;
	@FXML
	private Button btnBack;
	@FXML
	private Text txtUser1Name;
	@FXML
	private Text txtUser2Name;
	@FXML
	private Text txtUser3Name;
	@FXML
	private Text txtUser4Name;
	@FXML
	private Text txtUser1Rating;
	@FXML
	private Text txtUser2Rating;
	@FXML
	private Text txtUser3Rating;
	@FXML
	private Text txtUser4Rating;
	@FXML
	private ImageView imgUser1;
	@FXML
	private ImageView imgUser2;
	@FXML
	private ImageView imgUser3;
	@FXML
	private ImageView imgUser4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWaitRoom();
	}

	String msg;
	String number;
	String messageBody;
	boolean loop = true;

	public void startGame() {
		Client.client.send("6" + Client.client.toString());
	}

	public void goBack() throws Exception {
		loop = false;
		
		//서버에게 퇴장알리기
		Client.client.send("5" + Client.client.toString());
		
		// MenuRoomView화면 출력
		Parent MenuRoomView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
		Scene scene = new Scene(MenuRoomView);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}

	public void setWaitRoom() {
		Thread thread = new Thread() {
			@Override
			public void run(){
				while (loop) {
					msg = MessageListener.msg;
					number = msg.substring(0, 1);
					messageBody = msg.substring(1);

					if (number.equals("4")) {
						if (messageBody.equals("1"))
							setOpacityPlayer1();
						else if (messageBody.equals("2"))
							setOpacityPlayer2();
						else if (messageBody.equals("3"))
							setOpacityPlayer3();
						else if (messageBody.equals("4")) {
							setOpacityPlayer4();
						}
					}else if(number.equals("6")) {
						loop = false;
						
						try {
							// MenuRoomView화면 출력
							Parent InGameView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
							Scene scene = new Scene(InGameView);
							Stage primaryStage = (Stage) btnBack.getScene().getWindow();
							primaryStage.setScene(scene);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		thread.start();
	}

	public void setOpacityPlayer1() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(0);
		imgUser3.setOpacity(0);
		imgUser4.setOpacity(0);
		btnStart.setDisable(false);
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
	}
}
