package Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;


public class Main extends Application {
	
	Socket socket;
	TextArea textArea;
	
	//Ŭ���̾�Ʈ ���α׷� ���� �޼ҵ�
	public void startClient(String IP, int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP,port);
					receive();
				} catch(Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[���� ���� ����]");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}
	
	//Ŭ���̾�Ʈ ���α׷��� ���� �޼ҵ�
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//�����κ��� �޽����� ���޹޴� �޼ҵ�
	public void receive() {
		 while(true) {
			 try {
				 InputStream in = socket.getInputStream();
				 byte[] buffer = new byte[512];
				 int length = in. read(buffer);
				 if(length == -1) throw new IOException();
				 String message = new String(buffer, 0, length, "UTF-8");
				 Platform.runLater(()->{
					 textArea.appendText(message);
				 });
			 } catch(Exception e) {
				 stopClient();
				 break;
			 }
		 }
	}
	
	//������ �޽����� �����ϴ� �޼ҵ�
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				} catch(Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
	
	//������ ���α׷��� ���۽�Ű�� �޼ҵ�
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));
		
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		
		TextField userName = new TextField();
		userName.setPrefWidth(150);
		userName.setPromptText("�г����� �Է��ϼ���.");
		HBox.setHgrow(userName, Priority.ALWAYS);
		
		TextField IPText = new TextField("127.0.0.1");
		TextField portText = new TextField("9876");
		portText.setPrefWidth(80);
		
		hbox.getChildren().addAll(userName, IPText, portText);
		root.setTop(hbox);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		root.setCenter(textArea);
		
		TextField input = new TextField();
		input.setPrefWidth(Double.MAX_VALUE);
		input.setDisable(true);
		
		input.setOnAction(event->{
			send(userName.getText() + ": " + input.getText() + "\n");
			input.setText("");
			input.requestFocus();
		});
		
		Button sendButton = new Button("������");
		sendButton.setDisable(true);
		
		sendButton.setOnAction(event-> {
			send(userName.getText() + ": " + input.getText() + "\n");
			input.setText("");
			input.requestFocus();
		});
		
		Button connectionButton = new Button("�����ϱ�");
		connectionButton.setOnAction(event->{
			if(connectionButton.getText().equals("�����ϱ�")) {
				int port = 9876;
				try {
					port = Integer.parseInt(portText.getText());
				} catch(Exception e) {
					e.printStackTrace();
				}
				startClient(IPText.getText(), port);
				Platform.runLater(()-> {
					textArea.appendText("[ä�ù� ����]\n");
				});
				connectionButton.setText("�����ϱ�");
				input.setDisable(false);
				sendButton.setDisable(false);
				input.requestFocus();
			} else {
				stopClient();
				Platform.runLater(()-> {
					textArea.appendText("[ä�ù� ����]\n");
				});
				connectionButton.setText("�����ϱ�");
				input.setDisable(true);
				sendButton.setDisable(true);
			}
		});
		
		BorderPane pane = new BorderPane();
		pane.setLeft(connectionButton);
		pane.setCenter(input);
		pane.setRight(sendButton);
		
		root.setBottom(pane);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setTitle("[ä�� Ŭ���̾�Ʈ]");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event-> stopClient());
		primaryStage.show();
		
		connectionButton.requestFocus();
		
	}
	
	//���α׷��� ������
	public static void main(String[] args) {
		launch(args);
	}
}
