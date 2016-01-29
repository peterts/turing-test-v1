package turingtest;
	
import java.io.IOException;

import turingtest.view.ChatViewController;
import turingtest.view.TesterChatRootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainTester extends Application{
	private BorderPane chatRootLayout;
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
	
	public void initChatWindow(boolean isServer){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TesterChatRoot.fxml"));
			
			chatRootLayout = loader.load();
			TesterChatRootController chatRootController = loader.getController();
			
			loader = new FXMLLoader(getClass().getResource("view/ChatView.fxml"));
			BorderPane chatView = loader.load();
			ChatViewController chatViewController = loader.getController();
			chatRootLayout.setCenter(chatView);
			
			ChatConnection connection = new ChatConnection(isServer, chatViewController);
			chatRootController.setConnection(connection);
			chatViewController.setConnection(connection);
			connection.start();
			
			primaryStage.setScene(new Scene(chatRootLayout, 600, 400));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showChatWindow(){
		primaryStage.setScene(new Scene(chatRootLayout, 600, 400));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
