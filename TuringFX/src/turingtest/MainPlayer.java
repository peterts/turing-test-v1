package turingtest;
	
import java.io.IOException;

import turingtest.model.PlayerSession;
import turingtest.view.MessageType;
import turingtest.view.PlayerChatViewController;
import turingtest.view.ResultViewController;
import turingtest.view.RoundEndViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainPlayer extends Application{
	public final static double WINDOW_WIDTH = 1000;
	public final static double WINDOW_HEIGHT = 800;
	
	private BorderPane chatView;
	private BorderPane roundEndView;
	private BorderPane resultView;
	private PlayerChatViewController chatViewController;
	private RoundEndViewController roundEndViewController;
	private ResultViewController resultViewController;
	private Stage primaryStage;
	private PlayerSession session;
	private ChatConnection connection;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			session = new PlayerSession();
			initChatWindow();
			initRoundEndWindow();
			initResultdWindow();
			primaryStage.setTitle("Chat");
			primaryStage.setScene(new Scene(chatView, WINDOW_WIDTH, WINDOW_HEIGHT));
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	            	System.out.println("Session ended");
	            	connection.dissconnect();
	            	Platform.exit();
	            	System.exit(0);
	            }
	        });
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initChatWindow(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/PlayerChatView.fxml"));
			chatView = loader.load();
			chatViewController = loader.getController();
			chatViewController.setMainApp(this);
			
			connection = new ChatConnection(false, chatViewController);
			chatViewController.setConnection(connection);
			connection.start();

			chatViewController.setPlayerSession(session);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initRoundEndWindow(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/RoundEndView.fxml"));
			roundEndView = loader.load();
			roundEndViewController = loader.getController();
			roundEndViewController.setMainApp(this);
			roundEndViewController.setPlayerSession(session);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void initResultdWindow(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ResultView.fxml"));
			resultView = loader.load();
			resultViewController = loader.getController();
			resultViewController.setMainApp(this);
			resultViewController.setPlayerSession(session);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void endRound(){
		showRoundEndView();
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				chatViewController.resetChat();
				roundEndViewController.showAnswer();				
			}
			
		});
	}
	
	public void newRound(){
		showChatWindow();
		connection.sendMessage(MessageType.NEW_ROUND.toString());
	}
	
	public void endGame(){
		showResultWindow();
		resultViewController.showRebusResult();
		connection.sendMessage(MessageType.END_GAME.toString());
	}
	
	public void newGame(){
		session = new PlayerSession();	
		chatViewController.setPlayerSession(session);
		roundEndViewController.setPlayerSession(session);
		resultViewController.setPlayerSession(session);
		showChatWindow();
		connection.sendMessage(MessageType.NEW_GAME.toString());
	}
	
	private void showResultWindow(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				primaryStage.getScene().setRoot(resultView);
			}
		});
	}
	
	
	private void showChatWindow(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				primaryStage.getScene().setRoot(chatView);
			}
		});
	}
	
	private void showRoundEndView(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				primaryStage.getScene().setRoot(roundEndView);
			}
		});

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
