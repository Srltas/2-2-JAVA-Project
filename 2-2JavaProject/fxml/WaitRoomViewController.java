package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class WaitRoomViewController implements Initializable{
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setOpacity();
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
	
	public void setOpacity() {
		imgUser2.setOpacity(0);
		imgUser3.setOpacity(0);
		imgUser4.setOpacity(0);
		txtUser2Name.setOpacity(0);
		txtUser3Name.setOpacity(0);
		txtUser4Name.setOpacity(0);
		txtUser2Rating.setOpacity(0);
		txtUser3Rating.setOpacity(0);
		txtUser4Rating.setOpacity(0);
	}
}
