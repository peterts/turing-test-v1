package turingtest.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import turingtest.ChatConnection;
import turingtest.MainPlayer;

public class PlayerChatRootController {
	private MainPlayer mainApp;
	private ChatConnection connection;
	
	@FXML
	private Label lblTotalPoints;
	@FXML
	private Label lblCurrentPoints;
	@FXML
	private Label lblLinesLeft;
	
	public void setMainApp(MainPlayer mainApp){
		this.mainApp = mainApp;
	}
	
	public void setConnection(ChatConnection connection){
		this.connection = connection;
	}
	
	public void guessHuman(){
//		connection.sendMessage("guesshuman");
		System.out.println("Guess human");
	}
	
	public void guessComputer(){
//		connection.sendMessage("guesscomputer");
		System.out.println("Guess computer");
	}


}
