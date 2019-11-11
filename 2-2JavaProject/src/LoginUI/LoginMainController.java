package LoginUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginMainController {

	@FXML
	private Label lblStatus;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	private TextField txtPassword;
	
	public void Login(ActionEvent event) throws Exception{
		if(txtUserName.getText().equals("user") && txtPassword.getText().equals("pass")) {
			lblStatus.setText("Login Success");
			//������ ���̵� + ��й�ȣ �ֱ�
			String text = txtUserName.getText() + "," + txtPassword.getText();
			Client client = new Client(text);
			client.startClient("127.0.0.1", 9876);
			
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}else {
			lblStatus.setText("Login Failed");
		}
	}
}
