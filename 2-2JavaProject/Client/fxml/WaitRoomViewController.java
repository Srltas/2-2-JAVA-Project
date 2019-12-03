package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import clientStarter.StartClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WaitRoomViewController implements Initializable {
	@FXML
	private Button btnStart;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnLoop;
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
	String[] message;
	boolean loop = true;
	boolean viewLoop = true;
	boolean checkReady = false;

	public void startGame() throws Exception {
		if (btnStart.getText().equals("Ready")) {
			if (checkReady == false) {
				checkReady = true;
				Client.client.send("readyPlayer," + Client.client.toString());
				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							while (loop) {
								msg = MessageListener.msg;
								message = msg.split(",");

								if (message[0].equals("startGame")) {
									Stage pane = (Stage) StartClient.stage.getScene().getWindow();
									pane.close();
									Parent inGameView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
									Scene scene = new Scene(inGameView);
									StartClient.stage.setScene(scene);
									StartClient.stage.show();
									loop = false;
									viewLoop = false;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				thread.start();
			} else {
				Client.client.send("unReadyPlayer," + Client.client.toString());
				checkReady = false;
				loop = false;
			}
		} else if (btnStart.getText().equals("Start")) {
			Client.client.send("startGame," + Client.client.toString());
			msg = MessageListener.msg;
			message = msg.split(",");

			if (message[0].equals("startGame")) {
				Stage pane = (Stage) StartClient.stage.getScene().getWindow();
				pane.close();
				Parent inGameView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
				Scene scene = new Scene(inGameView);
				StartClient.stage.setScene(scene);
				StartClient.stage.show();
				loop = false;
				viewLoop = false;
			}
		}
	}

	public void goBack(ActionEvent event) throws Exception {
		loop = false;
		viewLoop = false;

		// 서버에게 퇴장알리기
		Client.client.send("exitWaitRoom," + Client.client.toString());

		// MenuRoomView화면 출력
		Parent menuRoomView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
		AnchorPane pane = (AnchorPane) StartClient.stage.getScene().getRoot();
		pane.getChildren().add(menuRoomView);
	}

	public void setWaitRoom() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (viewLoop) {
					msg = MessageListener.msg;
					message = msg.split(",");

					if (message[0].equals("enterWaitRoom")) {
						if (message[1].equals("1"))
							setOpacityPlayer1();
						else if (message[1].equals("2"))
							setOpacityPlayer2();
						else if (message[1].equals("3"))
							setOpacityPlayer3();
						else if (message[1].equals("4")) {
							setOpacityPlayer4();
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
		btnStart.setText("Start");
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