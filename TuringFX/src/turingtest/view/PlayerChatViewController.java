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
	private final String fontFamily = "Verdena";
	private final Double fontSize = 14.0;
	
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
	
	public void setConnection(ChatConnection connection){
		this.connection = connection;
	}
	
	public void setPlayerSession(PlayerSession session){
		this.session = session;
	}
	
	@FXML
	private void initialize(){
		txtMessage.requestFocus();
		lstMessageDisplay.setItems(messages);
	}
	
	private void updateLabels(){
		lblCurrentPoints.setText("Available points: "+session.getAvailablePoints());
		lblLinesLeft.setText(String.format("Max. lines left: %d/20", session.getLinesLeft()));
		lblTotalPoints.setText("Total points: " +  session.getTotalPoints());
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
		
		session.useLine();
		updateLabels();
		
		disableChat();
	}
	
	public void guessHuman(){
//		connection.sendMessage("guesshuman");
		session.registerGuess(TesterType.HUMAN);
	}
	
	public void guessComputer(){
//		connection.sendMessage("guesscomputer");
		session.registerGuess(TesterType.COMPUTER);
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
		
		enableChat();
	}
	
	public void enableChat(){
		txtMessage.setDisable(false);
		btnSendMessage.setDisable(false);
		btnGuessComputer.setDisable(false);
		btnGuessHuman.setDisable(false);
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				txtMessage.requestFocus();
			}
		});
	}
	
	public void disableChat(){
		txtMessage.setDisable(true);
		btnSendMessage.setDisable(true);
		btnGuessComputer.setDisable(true);
		btnGuessHuman.setDisable(true);
		
	}
	
}
