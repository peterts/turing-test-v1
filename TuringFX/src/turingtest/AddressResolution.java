package turingtest;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AddressResolution {
	public static final int PORT = 56619;
	public static final String SERVER_GREETING = "helloimserver";
	public static final String CLIENT_GREETING = "helloserverimclient";
	public static final String SERVER_RESPONSE = "myipis-";
	
	public static void lookForClient(String address){
		try{
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
