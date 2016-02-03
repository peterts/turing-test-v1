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
	public final static double WINDOW_WIDTH = 1000;
	public final static double WINDOW_HEIGHT = 800;
	
	private BorderPane chatView;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			initChatWindow();
			primaryStage.setTitle("Chat");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initChatWindow() throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TesterChatView.fxml"));
			chatView = loader.load();
			TesterChatViewController chatViewController = loader.getController();
			
			ChatConnection connection = new ChatConnection(true, chatViewController);
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
