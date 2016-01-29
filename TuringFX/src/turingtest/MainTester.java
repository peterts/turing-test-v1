package turingtest;
	
import java.io.IOException;

import turingtest.model.TesterSession;
import turingtest.view.TesterChatViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainTester extends Application{
	public final static int WINDOW_WIDTH = 600;
	public final static int WINDOW_HEIGHT = 400;
	
	private BorderPane chatView;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			initChatWindow(true);
			primaryStage.setTitle("Chat");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initChatWindow(boolean isServer) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TesterChatView.fxml"));
			chatView = loader.load();
			TesterChatViewController chatViewController = loader.getController();
			
			ChatConnection connection = new ChatConnection(isServer, chatViewController);
			chatViewController.setConnection(connection);
			chatViewController.setConnection(connection);
			connection.start();
			
			chatViewController.setTesterSession(new TesterSession());
			
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
