package fxml;

import java.io.IOException;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindIdViewController {
	@FXML
	private TextField txtPhonNumber;
	@FXML
	private Button btnFindID;
	@FXML
	private Button btnBack;
	@FXML
	private Label lblFindID;
	@FXML
	private Label lblWaring;
	
	String[] message;
	String msg;
	String phonNumber;
	
	public void findID(ActionEvent event) throws Exception {
		System.out.println("아이디찾기");
	}
	
	public void findID() throws InterruptedException, IOException {
		if(btnFindID.getText().equals("찾기")) {
			phonNumber = txtPhonNumber.getText();
			if(phonNumber.equals("")) {
				lblWaring.setText("필수 정보입니다.");
			}else {
				lblWaring.setText("");
			}
			
			if(!phonNumber.equals("")) {
				Client.client.send("findID," + txtPhonNumber.getText());
				Thread.sleep(100);
				
				msg = MessageListener.msg;
				message = msg.split(",");
				
				if(message[0].equals("findIDsuccess")) {
					lblFindID.setText(message[1]);
					btnFindID.setText("확인");
				}else {
					lblWaring.setText("해당 아이디가 없습니다.");
				}
			}
		} else if(btnFindID.getText().equals("확인")) {
			Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
			Scene scene = new Scene(View);
			Stage primaryStage = (Stage) btnFindID.getScene().getWindow();
			primaryStage.setScene(scene);
		}
	}
	public void back(ActionEvent event) throws Exception {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}