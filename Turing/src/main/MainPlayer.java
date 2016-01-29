package main;

import java.io.IOException;
import java.util.Scanner;

import networking.ChatConnection;
import networking.ChatListener;

public class MainPlayer implements ChatListener{
	private ChatConnection connection;
	
	public MainPlayer(int port){
		try {
			connection = new ChatConnection(false, this);
			connection.start();
		} catch (IOException e) {
			System.out.println("Connecting to server failed");
			e.printStackTrace();
		}
	}
	
	public void addMessage(String message) {
		System.out.println("bot: "+message);
		System.out.print("you: ");
	}
	
	public ChatConnection getConnection(){
		return connection;
	}
	
	
	public void run(){
		connection.start();
		System.out.println("\n\nChat started");
		System.out.print("you: ");
		Scanner scanner = new Scanner(System.in);
		while(true){
			if(scanner.hasNextLine()){
				System.out.println("yo");
				String line = scanner.nextLine();
				connection.sendMessage(line);
				System.out.println();
				if(line.contains("endsession")){
					break;
				}
			}
		}
		System.out.println("Session ended.");
		connection.endSession();
		scanner.close();
	}
	
	
	public static void main(String[] args) {
		System.out.print("Port nubmer: ");
		Scanner scanner = new Scanner(System.in);
		int port = Integer.parseInt(scanner.nextLine());
		System.out.print("\n");
		
		MainPlayer player = new MainPlayer(port);
		System.out.println("\nChat started");
		System.out.print("you: ");
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			player.getConnection().sendMessage(line);
			if(line.contains("endsession")){
				break;
			}
		}
		System.out.println("Session ended.");
		player.getConnection().endSession();
		scanner.close();
		
	}
}
