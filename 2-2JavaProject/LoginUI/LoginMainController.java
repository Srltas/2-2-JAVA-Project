package LoginUI;

import java.io.DataOutputStream;
import java.io.OutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginMainController {
	String text;
	OutputStream out = null;
	DataOutputStream dout = null;
	
	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUserName;
//
	@FXML
	private TextField txtPassword;
	
	@FXML
	private Button btnLogin;
	
	public void Login(ActionEvent event) throws Exception {
		
		text = "0" + txtUserName.getText() + "," + txtPassword.getText();
		LoginUIClient.client.sendLogin(text);
		Thread.sleep(100);
		
		if(MessageListener.msg.equals("true")){
			Parent menuView = FXMLLoader.load(getClass().getClassLoader().getResource("LoginUI/Main.fxml"));
			Scene scene = new Scene(menuView);
			Stage primaryStage = (Stage)btnLogin.getScene().getWindow();
			primaryStage.setScene(scene);
		}
	}
}
