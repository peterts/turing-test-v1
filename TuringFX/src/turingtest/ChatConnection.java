package turingtest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatConnection extends Thread{
	public static final String name = "localhost";
	public static final Integer port = 56618;

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ChatListener listener;

	public ChatConnection(boolean isServer, ChatListener listener) throws IOException{
		if(isServer){
			startServer();
		}else{
			startClient();
		}
		this.listener = listener;
	}
	
	@SuppressWarnings("resource")
	private void startServer() throws IOException{
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
		
		socket = serverSocket.accept();	
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	private void startClient() throws IOException{
		socket = new Socket(name, port);	
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	}
	

	public void sendMessage(String message){
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endSession(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		while(true){
			try {
				String message = in.readUTF();
				if(message.equals("endsession")){
					break;
				}
				listener.addMessage(message);
			} catch (IOException e) {
				break;
			}
		}
		endSession();
	}

}
