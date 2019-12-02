package fxml;

import java.net.URL;
import java.util.ResourceBundle;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
