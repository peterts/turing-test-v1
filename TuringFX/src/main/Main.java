package main;
	
import java.io.IOException;

import networking.ChatConnection;
import chat.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	private Parent chatWindow;
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
			chatWindow = loader.load();
			chatWindow.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());
			
			ChatController controller = loader.getController();
			controller.setMainApp(this);
			ChatConnection connection = new ChatConnection(isServer, controller);
			controller.setConnection(connection);
			
			primaryStage.setScene(new Scene(chatWindow, 600, 400));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showChatWindow(){
		primaryStage.setScene(new Scene(chatWindow, 600, 400));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
