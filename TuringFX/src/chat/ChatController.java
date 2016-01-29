package chat;

import java.io.IOException;

import main.Main;
import networking.ChatConnection;
import networking.ChatListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ChatController implements ChatListener{
	private final String fontFamily = "Verdena";
	private final Double fontSize = 13.0;
	
	@FXML
	private TextField txtMessage;
	@FXML
	private ListView<TextFlow> lstMessageDisplay;
	private ObservableList<TextFlow> messages = FXCollections.observableArrayList();
	private Main mainApp;
	
	private ChatConnection connection;
	
	public void setMainApp(Main mainApp){
		this.mainApp = mainApp;
	}
	
	public void setConnection(ChatConnection connection){
		this.connection = connection;
		connection.start();
	}
	
	@FXML
	private void initialize(){
		txtMessage.requestFocus();
		lstMessageDisplay.setItems(messages);
	}
	
	public void sendMessage(){
		TextFlow tf = new TextFlow();
		
		Text nickname = new Text("you: ");
		nickname.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
		
		String message = txtMessage.getText();
		Text messageText = new Text(message);
		messageText.setFont(Font.font(fontFamily, FontWeight.NORMAL, fontSize));
		
		tf.getChildren().addAll(nickname, messageText);
		messages.add(tf);
		
		txtMessage.clear();
		txtMessage.requestFocus();
		
		connection.sendMessage(message);
	}

	@Override
	public void addMessage(String message){
		TextFlow tf = new TextFlow();
		
		Text nickname = new Text("other: ");
		nickname.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
		
		Text messageText = new Text(message);
		messageText.setFont(Font.font(fontFamily, FontWeight.NORMAL, fontSize));
		
		tf.getChildren().addAll(nickname, messageText);
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				messages.add(tf);
			}
		});
	}
	
}
