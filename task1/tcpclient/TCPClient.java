package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
	
	public static String askServer(String hostname, int port, String ToServer) throws  IOException {
		
		//if no third argument was sent, ask server only with arguments hostname and port
		if(ToServer == null) {
			return askServer(hostname, port);
		}
		
		//Create socket and set timeout
		Socket clientSocket = new Socket(hostname, port);
		clientSocket.setSoTimeout(3000);
		
		//create output stream attached to socket
		DataOutputStream dataToServer = new DataOutputStream(clientSocket.getOutputStream());
		//create input stream attached to socket
		BufferedReader dataFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		//send third optional argument
		dataToServer.writeBytes(ToServer + "\n");

		//iterate until the BufferedReader return null which means there's no more data to read.
		//each line will appended to a StringBuilder and returned as String.
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		try {
			while((line = dataFromServer.readLine()) != null) {
				sb.append(line + "\n");
			}
			clientSocket.close();
			return sb.toString();
		}  catch (IOException exc) {
			clientSocket.close();
			return sb.toString();
		}
	}
	
	public static String askServer(String hostname, int port) throws  IOException {
				
		//Create socket and set timeout
		Socket clientSocket = new Socket(hostname, port);
		clientSocket.setSoTimeout(3000);
		
		//create input stream attached to socket
		BufferedReader dataFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		//iterate until the BufferedReader return null which means there's no more data to read.
		//each line will appended to a StringBuilder and returned as String.
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		int counter = 0;
		
		try {
			while((line = dataFromServer.readLine()) != null && counter < 1000) {
				sb.append(line + "\n");
				counter++;
			}
			clientSocket.close();
			return sb.toString();
			} catch (IOException exc) {
				clientSocket.close();
				return sb.toString();
			}
	}
}

