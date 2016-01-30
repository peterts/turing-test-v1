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
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TesterChatViewController implements ChatListener{	
	public final static String ANSWER_MESSAGE = "answer-";
	private static final String FONT_FAMILY = "Verdena";
	private static final Double FONT_SIZE = 14.0;
	
	@FXML
	private TextField txtMessage;
	@FXML
	private ListView<TextFlow> lstMessageDisplay;
	@FXML
	private Button btnSendMessage;
	@FXML
	private ToggleButton btnUseBot;
	@FXML
	private ToggleButton btnDonotUseBot;
	private ObservableList<TextFlow> messages = FXCollections.observableArrayList();
	private ChatConnection connection;
	private TesterSession session;
	
	public void setConnection(ChatConnection connection){
		this.connection = connection;
	}
	
	public void setTesterSession(TesterSession session){
		this.session = session;
	}
	
	@FXML
	private void initialize(){
		txtMessage.requestFocus();
		lstMessageDisplay.setItems(messages);
		disableChat();
	}
	
	private void addChatMessage(String nickname, String message){
		TextFlow tf = new TextFlow();
		Text nicknameText = new Text(nickname);
		nicknameText.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, FONT_SIZE));
		Text messageText = new Text(message);
		messageText.setFont(Font.font(FONT_FAMILY, FontWeight.NORMAL, FONT_SIZE));	
		tf.getChildren().addAll(nicknameText, messageText);
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				messages.add(tf);
			}
		});
	}
	
	public void useBot(){
		session.setIsBot(true);
		session.startBotSession();
		btnUseBot.setDisable(true);
		btnDonotUseBot.setDisable(true);
		System.out.println("usebot");
	}
	
	public void donotUseBot(){
		session.setIsBot(false);
		btnUseBot.setDisable(true);
		btnDonotUseBot.setDisable(true);
		System.out.println("Dont use bot");
		enableChat();
	}
	
	
	public void sendMessage(){
		String message = txtMessage.getText();
		addChatMessage("you: ", message);
		connection.sendMessage(message);
		txtMessage.clear();
		disableChat();			
	}
	
	private void sendBotMessage(String message){
		addChatMessage("bot: ", message);
		connection.sendMessage(message);
	}

	@Override
	public void addMessage(String message){		
		if(message.contains(PlayerChatViewController.GUESS_MESSAGE)){
			String modifiedMessage = String.format("PLAYER GUESSED \"%s\"", message.split("-")[1]);
			addChatMessage("", modifiedMessage);
			connection.sendMessage(ANSWER_MESSAGE+session.getTesterType());
			return;
		}else{
			addChatMessage("other: ", message);
		}
		
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
