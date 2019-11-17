package fxml;

import java.io.DataOutputStream;
import java.io.OutputStream;

import LoginUI.LoginUIClient;
import LoginUI.MessageListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartViewController {
	String text;
	OutputStream out = null;
	DataOutputStream dout = null;

	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUserId;

	@FXML
	private TextField txtPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnSignUp;

	@FXML
	private Button btnFindId;

	public void login(ActionEvent event) throws Exception {

		text = "0" + txtUserId.getText() + "," + txtPassword.getText();
		LoginUIClient.client.sendLogin(text);
		Thread.sleep(100);

		if (MessageListener.msg.equals("true")) {
			Parent menuView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainView.fxml"));
			Scene scene = new Scene(menuView);
			Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
			primaryStage.setScene(scene);
		}
	}

	public void enterSignUpView(ActionEvent event) throws Exception {
		Parent signUpView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SignUpView.fxml"));
		Scene scene = new Scene(signUpView);
		Stage primaryStage = (Stage) btnSignUp.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	
	public void enterFindIdView(ActionEvent event) throws Exception {
		Parent findIdView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FindIDView.fxml"));
		Scene scene = new Scene(findIdView);
		Stage primaryStage = (Stage) btnFindId.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}
