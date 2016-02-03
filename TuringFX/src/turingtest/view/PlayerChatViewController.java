package turingtest.view;

import turingtest.ChatConnection;
import turingtest.ChatListener;
import turingtest.MainPlayer;
import turingtest.model.PlayerSession;
import turingtest.model.TesterType;
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

public class PlayerChatViewController implements ChatListener{
	private static final String FONT_FAMILY = "Verdena";
	private static final Double FONT_SIZE = 36.0;
	
	@FXML
	private TextField txtMessage;
	@FXML
	private ListView<TextFlow> lstMessageDisplay;
	@FXML
	private Button btnSendMessage;
	@FXML
	private Button btnGuessComputer;
	@FXML
	private Button btnGuessHuman;
	@FXML
	private Label lblTotalPoints;
	@FXML
	private Label lblCurrentPoints;
	@FXML
	private Label lblLinesLeft;
	private ObservableList<TextFlow> messages = FXCollections.observableArrayList();
	private ChatConnection connection;
	private PlayerSession session;
	private MainPlayer main;
	
	public void setConnection(ChatConnection connection){
		this.connection = connection;
	}
	
	public void setPlayerSession(PlayerSession session){
		this.session = session;
		updateLabels();
	}
	
	public void setMainApp(MainPlayer main){
		this.main = main;
	}
	
	@FXML
	private void initialize(){
		txtMessage.requestFocus();
		lstMessageDisplay.setItems(messages);
		disableChat();
	}
	
	private void clearMessages(){
		messages.clear();
	}
	
	private void updateLabels(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				lblCurrentPoints.setText("Available points: "+session.getAvailablePoints());
				lblLinesLeft.setText(String.format("Max. lines left: %d/%d", session.getLinesLeft(), session.getMaxNumLines()));
				lblTotalPoints.setText("Total points: " +  session.getTotalPoints());	
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
			}
		});
	}
	
	public void sendMessage(){
		String message = txtMessage.getText();
		addChatMessage("you: ", message);
		connection.sendMessage(message);
		txtMessage.clear();
		session.useLine();
		updateLabels();
		disableChat();
	}
	
	public void guessHuman(){
		connection.sendMessage(MessageType.GUESS.toString()+TesterType.HUMAN);
		session.registerGuess(TesterType.HUMAN);
		disableChat();
	}
	
	public void guessComputer(){
		connection.sendMessage(MessageType.GUESS.toString()+TesterType.COMPUTER);
		session.registerGuess(TesterType.COMPUTER);
		disableChat();
	}

	@Override
	public void addMessage(String message){
		if(message.contains(MessageType.READY.toString())){
			enableChat(true);
			return;
		}
		if(message.contains(MessageType.ANSWER.toString())){
			TesterType actualType = TesterType.getType(message.split("-")[1]);
			session.evaluateGuess(actualType);
			main.showRoundEndView();
			return;
		}else{
			addChatMessage("other: ", message);
		}
		if(session.getLinesLeft() > 0){
			enableChat(true);			
		}else{
			enableChat(false);
		}
		
	}
	
	public void enableChat(boolean enableMessage){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				if(enableMessage){
					txtMessage.setDisable(false);
					btnSendMessage.setDisable(false);
				}
				btnGuessComputer.setDisable(false);
				btnGuessHuman.setDisable(false);
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
				btnGuessComputer.setDisable(true);
				btnGuessHuman.setDisable(true);
			}
		});
	}
	
}
