package turingtest.view;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import turingtest.MainPlayer;
import turingtest.model.PlayerSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResultViewController {
	@FXML
	private Label lblRebusPoints;
	@FXML
	private Label lblTotalPoints;
	@FXML
	private Button btnEndGame;
	private MainPlayer main;
	private PlayerSession session;
	
	@FXML
	private void initialize(){

	}
	
	private int computeRebusScore(){
		float fracPoints;
		if(session.getMaxPossiblePoints() != 0){
			fracPoints = session.getTotalPoints()/session.getMaxPossiblePoints();
		}else{
			fracPoints = 0;
		}
		return Math.round(10*fracPoints);
	}
	
	public void setPlayerSession(PlayerSession session){
		this.session = session;
	}
	
	public void showRebusResult(){
		int rebusScore = computeRebusScore();
		lblRebusPoints.setText(String.format("Rebus points: %s/10", rebusScore));
		lblTotalPoints.setText(String.format("(Game points: %s/%s)", session.getTotalPoints(), session.getMaxPossiblePoints()));
	}
	
	public void setMainApp(MainPlayer main){
		this.main = main;
	}
	
	public void newGameClicked(){
		main.newGame();
	}

}
