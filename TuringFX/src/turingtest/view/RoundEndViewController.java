package turingtest.view;

import turingtest.MainPlayer;
import turingtest.model.PlayerSession;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class RoundEndViewController {
	@FXML
	private Label lblGuess;
	@FXML
	private Label lblAnswer;
	@FXML
	private Label lblAvailablePoints;
	@FXML
	private Label lblTotalPoints;
	@FXML
	private Button btnNewRound;
	@FXML
	private Button btnEndGame;
	
	private MainPlayer main;
	private PlayerSession session;
	
	
	public void setPlayerSession(PlayerSession session){
		this.session = session;
	}
	
	public void setMainApp(MainPlayer main){
		this.main = main;
	}
	
	@FXML
	private void initialize(){

	}
	
	private void sleep(long millis){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}						
			}
			
		});
	}
	
	public void showAnswer(){
		lblAvailablePoints.setText("Available points: "+session.getAvailablePoints());
		lblTotalPoints.setText("Total points this far: "+session.getPrevTotalPoints());
		lblGuess.setText(String.format("You guessed \"%s\"", session.getGuess()));
	    final Animation animation = new Transition() {
	        {
	            setCycleDuration(Duration.millis(3000));
	        }
	    
	        protected void interpolate(double frac) {
	            final int n = Math.round(3 * (float) frac);
	            lblAnswer.setText("Answer was" + "...".substring(0, n));
	        }
	        
	    
	    };
	    animation.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				lblAnswer.setText(String.format("Answer was \"%s\"", session.getAnswer()));
				if(session.getGuess() == session.getAnswer()){
					lblAnswer.setTextFill(Color.web("#009900"));
					lblGuess.setTextFill(Color.web("#009900"));

				}else{
					lblAnswer.setTextFill(Color.web("#ff0000"));
					lblGuess.setTextFill(Color.web("#ff0000"));
				}
				lblTotalPoints.setText("Total points this far: "+session.getTotalPoints());
			}
	    	
	    });
	    animation.play();   

	}

}
