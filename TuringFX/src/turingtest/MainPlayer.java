package turingtest;
	
import java.io.IOException;

import turingtest.model.PlayerSession;
import turingtest.view.PlayerChatViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class MainPlayer extends Application{
	public final static double WINDOW_WIDTH = 1000;
	public final static double WINDOW_HEIGHT = 800;
	
	private BorderPane chatView;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			initChatWindow(false);
			primaryStage.setTitle("Chat");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initChatWindow(boolean isServer){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/PlayerChatView.fxml"));
			chatView = loader.load();
			PlayerChatViewController chatViewController = loader.getController();
			
			ChatConnection connection = new ChatConnection(isServer, chatViewController);
			chatViewController.setConnection(connection);
			chatViewController.setConnection(connection);
			connection.start();
			
			chatViewController.setPlayerSession(new PlayerSession());
			
			primaryStage.setScene(new Scene(chatView, WINDOW_WIDTH, WINDOW_HEIGHT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showChatWindow(){
		primaryStage.setScene(new Scene(chatView, WINDOW_WIDTH, WINDOW_HEIGHT));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
