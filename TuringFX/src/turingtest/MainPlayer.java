package turingtest;
	
import java.io.IOException;

import turingtest.model.PlayerSession;
import turingtest.view.PlayerChatViewController;
import turingtest.view.RoundEndViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainPlayer extends Application{
	public final static double WINDOW_WIDTH = 1000;
	public final static double WINDOW_HEIGHT = 800;
	
	private BorderPane chatView;
	private BorderPane roundEndView;
	private PlayerChatViewController chatViewController;
	private RoundEndViewController roundEndViewController;
	private Stage primaryStage;
	private PlayerSession session;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			primaryStage.setFullScreen(true);
			session = new PlayerSession();
			initChatWindow();
			initRoundEndWindow();
			primaryStage.setTitle("Chat");
			primaryStage.setScene(new Scene(chatView, WINDOW_WIDTH, WINDOW_HEIGHT));
			primaryStage.show();
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
			
			ChatConnection connection = new ChatConnection(false, chatViewController);
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
	
	public void showChatWindow(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				chatViewController.resetChat();
				primaryStage.getScene().setRoot(chatView);
			}
		});
	}
	
	public void showRoundEndView(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				primaryStage.getScene().setRoot(roundEndView);
				roundEndViewController.showAnswer();
			}
		});

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
