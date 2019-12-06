package fxml;

import java.io.IOException;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindPwViewController {
	@FXML
	private TextField txtFieldID;
	@FXML
	private TextField txtFieldPW;
	@FXML
	private TextField txtFieldPW2;
	@FXML
	private Button btnChange;
	@FXML
	private Button btnBack;
	@FXML
	private Label lblID;
	@FXML
	private Label lblPW;
	@FXML
	private Label lblPW2;
	
	String msg;
	String[] message;
	
	public void changePW() throws IOException, InterruptedException {
		if(btnChange.getText().equals("찾기")) {
			if(txtFieldID.getText().equals("") == true) {
				lblID.setText("필수 정보입니다.");
			}else {
				lblID.setText("");
			}
			if(txtFieldPW.getText().equals("") == true) {
				lblPW.setText("필수 정보입니다.");
			}else {
				lblPW.setText("");
			}
			if(txtFieldPW2.getText().equals("") == true) {
				lblPW2.setText("필수 정보입니다.");
			}else {
				lblPW2.setText("");
			}
			if(txtFieldID.getText().equals("") != true && txtFieldPW.getText().equals("") != true && txtFieldPW2.getText().equals("") != true) {
				Client.client.send("changePW," + txtFieldID.getText() +"," + txtFieldPW.getText()+ "," + txtFieldPW2.getText());
				Thread.sleep(100);
				msg = MessageListener.msg;
				message = msg.split(",");
				
				if(message[0].equals("changePWSuccess")) {
					lblPW2.setText("비밀번호가 변경되었습니다.");
					btnChange.setText("확인");
				}else {
					lblPW2.setText("해당 아이가 없거나 잘못입력하였습니다.");
				}
			}
		}else if(btnChange.getText().equals("확인")) {
			Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
			Scene scene = new Scene(View);
			Stage primaryStage = (Stage) btnBack.getScene().getWindow();
			primaryStage.setScene(scene);
		}
	}
	
	public void Back() throws IOException {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}
