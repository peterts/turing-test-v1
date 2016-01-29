package main;
import java.io.IOException;

import networking.ChatConnection;
import networking.ChatListener;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;


public class MainTester implements ChatListener{
	private ChatConnection connection;
	private ChatterBotSession botSession;
	
	public MainTester() throws Exception{
		try {
			connection = new ChatConnection(true, this);
			connection.start();
		} catch (IOException e) {
			System.out.println("Connecting to server failed");
			e.printStackTrace();
		}
		
		ChatterBotFactory factory = new ChatterBotFactory();
        ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
        botSession = bot.createSession();
	}
	
	public void addMessage(String message) {
		try {
			String response = botSession.think(message);
			connection.sendMessage(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ChatConnection getConnection(){
		return connection;
	}
	

	public static void main(String[] args) throws Exception {
		new MainTester();
	}
}
