package turingtest;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

public class AddressResolution {
	public static final int PORT = 56619;
	public static final String SERVER_GREETING = "helloimserver";
	public static final String CLIENT_GREETING = "helloserverimclient";
	public static final String SERVER_RESPONSE = "myipis-";
	
	private static String fixAddress(String address){
		StringBuilder sb = new StringBuilder(address);
		List<Integer> removeChars = new ArrayList<Integer>();
		for(int i = 0; i < sb.length(); i++){
			if(".0123456789".indexOf(sb.charAt(i)) == -1){
				removeChars.add(i);
			}
		}
		for(int i : removeChars){
			sb.deleteCharAt(i);
		}
		return sb.toString();
	}
	
	
	public static void lookForClient(String address){

		try{
			address = fixAddress(address);
			System.out.println(address);
			
			MulticastSocket multicastSocket = new MulticastSocket(PORT);
			InetAddress group = InetAddress.getByName("224.0.0.0");
			multicastSocket.joinGroup(group);
			byte[] bufSent = SERVER_GREETING.getBytes();
			byte[] bufReceived = new byte[256];
			DatagramPacket packetSend = new DatagramPacket(bufSent, bufSent.length, group, PORT);
			DatagramPacket packetReceive = null;
			String received = "";
			
			while(true){
				multicastSocket.send(packetSend);
				Thread.sleep(1000);
			    packetReceive = new DatagramPacket(bufReceived, bufReceived.length);
			    multicastSocket.receive(packetReceive);
			    received = new String(packetReceive.getData());
			    if(received.contains(CLIENT_GREETING)){
			    	break;
			    }
			}
			
			bufSent = (SERVER_RESPONSE+address).getBytes();
			packetSend = new DatagramPacket(bufSent, bufSent.length, group, PORT);
			multicastSocket.send(packetSend);
			multicastSocket.close();
		}catch(Exception e){
				e.printStackTrace();
		}
	}
	
	public static String lookForServer(){
		try{
			MulticastSocket multicastSocket = new MulticastSocket(PORT);
			InetAddress group = InetAddress.getByName("224.0.0.0");
			multicastSocket.joinGroup(group);
			byte[] bufSent = CLIENT_GREETING.getBytes();
			byte[] bufReceived = new byte[256];
			DatagramPacket packetSend = new DatagramPacket(bufSent, bufSent.length, group, PORT);
			DatagramPacket packetReceive = null;
			String received = "";
			
			while(true){
			    packetReceive = new DatagramPacket(bufReceived, bufReceived.length);
			    multicastSocket.receive(packetReceive);
			    received = new String(packetReceive.getData());
			    if(received.contains(SERVER_GREETING)){
			    	multicastSocket.send(packetSend);
			    	break;
			    }
				Thread.sleep(1000);
			}
			
			while(true){
			    packetReceive = new DatagramPacket(bufReceived, bufReceived.length);
			    multicastSocket.receive(packetReceive);
			    received = new String(packetReceive.getData());
			    if(received.contains(SERVER_RESPONSE)){
			    	break;
			    }
			}
			multicastSocket.close();
		    return received.split("-")[1];
		}catch(Exception e){
				e.printStackTrace();
		}
		return "";
	}
}
