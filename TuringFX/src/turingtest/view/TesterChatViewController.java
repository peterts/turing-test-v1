package turingtest.view;

import turingtest.ChatConnection;
import turingtest.ChatListener;
import turingtest.SentenceHelper;
import turingtest.model.TesterSession;

import java.util.Random;

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
	private static final String FONT_FAMILY = "Verdena";
	private static final Double FONT_SIZE = 36.0;
	
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
	@FXML
	private Label gameStatus;
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
		updateGameStatus("Please choose \"use bot\" or \"don't use bot.");
		txtMessage.requestFocus();
		lstMessageDisplay.setItems(messages);
		disableChat();
	}
	
	public void startNewRound(){
		updateGameStatus("New round. Please choose \"use bot\" or \"don't use bot.");
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				messages.clear();
				btnUseBot.setSelected(false);
				btnDonotUseBot.setSelected(false);
				btnUseBot.setDisable(false);
				btnDonotUseBot.setDisable(false);
			}
			
		});
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
				lstMessageDisplay.scrollTo(messages.size()-1);
			}
		});
	}
	
	public void useBot(){
		updateGameStatus("Round started. Bot is answering messages.");
		session.setIsBot(true);
		session.startBotSession();
		btnUseBot.setDisable(true);
		btnDonotUseBot.setDisable(true);
		connection.sendMessage(MessageType.READY.toString());
		System.out.println("usebot");
	}
	
	public void donotUseBot(){
		updateGameStatus("Round started. You are answering messages.");
		session.setIsBot(false);
		btnUseBot.setDisable(true);
		btnDonotUseBot.setDisable(true);
		System.out.println("Dont use bot");
		connection.sendMessage(MessageType.READY.toString());
		enableChat();
	}	
	
	public void sendMessage(){
		String message = txtMessage.getText();
		message = SentenceHelper.fixSentence(message);
		addChatMessage("you: ", message);
		connection.sendMessage(message);
		txtMessage.clear();
		disableChat();			
	}
	
	private void sendBotResponse(String message){
		Random rand = new Random();
		double wpm = 40.0 + rand.nextFloat()*40.0;
		long startTime = System.currentTimeMillis();
		String response = session.getBotResponse(message);
		long minTimeToUse = (long) (60000*SentenceHelper.countWords(response)/wpm);
		long timeUsed = System.currentTimeMillis() - startTime;
		if(timeUsed < minTimeToUse){
			try {
				Thread.sleep(minTimeToUse-timeUsed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		addChatMessage("bot: ", response);
		connection.sendMessage(response);
	}

	@Override
	public void addMessage(String message){		
		if(message.contains(MessageType.GUESS.toString())){
			String guess = message.split("-")[1];
			updateGameStatus(String.format("Player guessed \"%s\".", guess));
			connection.sendMessage(MessageType.ANSWER.toString()+session.getTesterType());
		}else if(message.contains(MessageType.NEW_ROUND.toString())){
			startNewRound();
		}else if(message.contains(MessageType.END_GAME.toString())){
			updateGameStatus("Game was ended by user.");
		}else if(message.contains(MessageType.NEW_GAME.toString())){
			startNewRound();
		}else{
			addChatMessage("other: ", message);
			if(session.isBot()){
				sendBotResponse(message);
			}else{
				enableChat();
			}
		}
	}
	
	private void updateGameStatus(String status){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				gameStatus.setText(status);
			}
		});
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
