import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
    	
    	//user input for port
    	int port = Integer.parseInt(args[0]);

    	//create server socket
        ServerSocket serverSocket = new ServerSocket(port);
        
        while(true) {
        	try {
        	
            //create client socket and server socket accept
        	Socket clientSocket = serverSocket.accept();
        	//create input stream attached to socket
        	BufferedReader dataFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        	//create output stream attached to socket
        	DataOutputStream dataToClient = new DataOutputStream(clientSocket.getOutputStream());
        	
        	//setup string and string builder
        	//server response with HTTP response message
        	StringBuilder sb = new StringBuilder();
    		String line = null;
    		String respHeader = "HTTP/1.1 200 OK\r\n\r\n";
    		sb.append(respHeader);
    		
    		//iterate until the BufferedReader return 'isEmtpy()' which means there's no more data to read.
    		//each line will appended to a StringBuilder and later returned as String.
			while(!(line = dataFromClient.readLine()).isEmpty()) {
				sb.append(line + "\n");
			}
			
			//write to client and closes connections
			dataToClient.writeBytes(sb.toString());
			clientSocket.close();
			dataFromClient.close();
			dataToClient.close();
			System.out.println("Closed");
        	}
        	
        	catch (IOException exc) {
        	System.out.println("error");
        }
      } 
  }
}

