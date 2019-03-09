import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class ConcHTTPAsk {
	public static void main(String[] args) throws Exception {
		try{
			int port = Integer.parseInt(args[0]);
			ServerSocket serverSocket = new ServerSocket(port);
			
			while(true) {
				Socket clientSocket = serverSocket.accept();
				new Thread(new MyRunnable(clientSocket)).start();
			}
		} catch(Exception exe){
			System.out.println("error");
		}
	}
}

