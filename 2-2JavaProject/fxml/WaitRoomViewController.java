package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import Socket.MessageListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class WaitRoomViewController implements Initializable{
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWaitRoom();
	}
	
	@FXML
	private Button btnReady;
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
	
	String msg;
	String number;
	String messageBody;
	
	public void setWaitRoom() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while(true) {
					msg = MessageListener.msg;
					number = msg.substring(0, 1);
					messageBody = msg.substring(1);
					
					if(number.equals("2")) {
						if(messageBody.equals("1"))
							setOpacityPlayer1();
						else if(messageBody.equals("2"))
							setOpacityPlayer2();
						else if(messageBody.equals("3"))
							setOpacityPlayer3();
						else if(messageBody.equals("4"))
							setOpacityPlayer4();
					}
				}
			}
		};
		thread.start();
	}
	public void setOpacityPlayer1() {
		imgUser1.setOpacity(1);

	}
	public void setOpacityPlayer2() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(1);
	}
	public void setOpacityPlayer3() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(1);
		imgUser3.setOpacity(1);
	}
	public void setOpacityPlayer4() {
		imgUser1.setOpacity(1);
		imgUser2.setOpacity(1);
		imgUser3.setOpacity(1);
		imgUser4.setOpacity(1);
	}
}
