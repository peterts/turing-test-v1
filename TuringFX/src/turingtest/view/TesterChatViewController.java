package turingtest.view;

import turingtest.ChatConnection;
import turingtest.ChatListener;
import turingtest.MainPlayer;
import turingtest.model.PlayerSession;
import turingtest.model.TesterSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TesterChatViewController implements ChatListener{
	private final String fontFamily = "Verdena";
	private final Double fontSize = 14.0;
	
	@FXML
	private TextField txtMessage;
	@FXML
	private ListView<TextFlow> lstMessageDisplay;
	@FXML
	private Button btnSendMessage;
	@FXML
	private Label lblBotActive;
	private ObservableList<TextFlow> messages = FXCollections.observableArrayList();
	private ChatConnection connection;
	private TesterSession session;
	
	public void setConnection(ChatConnection connection){
		this.connection = connection;
	}
	
	public void setTesterSession(TesterSession session){
		this.session = session;
		if(session.isBot()){
			lblBotActive.setText("Bot active");
			disableChat();
		}else{
			lblBotActive.setText("Bot disabled");
		}
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
		
		connection.sendMessage(message);
		
		if(!session.isBot()){
			disableChat();			
		}
	}
	
	private void sendBotMessage(String message){
		TextFlow tf = new TextFlow();
		Text nickname = new Text("bot: ");
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
		
		if(session.isBot()){
			sendBotMessage(session.getBotResponse(message));
		}else{
			enableChat();
		}
	}
	
	public void enableChat(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				txtMessage.setDisable(false);
				btnSendMessage.setDisable(false);
				txtMessage.requestFocus();
			}
		});
	}
	
	public void disableChat(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtMessage.setDisable(true);
				btnSendMessage.setDisable(true);
			}
		});
	}
	
}
