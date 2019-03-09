import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk {
    public static void main( String[] args) throws IOException {
    	String hostname = null;
    	int port;
    	String string = null;
    	String http = null;
    	String ask = null;
    	
    	int serverPort = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(serverPort);
    	
        try {
        	while(true) {
        		
        		//Create socket and set timeout
            	Socket clientSocket = serverSocket.accept();
            	clientSocket.setSoTimeout(1000);
            	//create output stream attached to socket
            	DataOutputStream dataToClient = new DataOutputStream(clientSocket.getOutputStream());
            	//create input stream attached to socket
            	BufferedReader dataFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            	
            	//store the client get request in string
            	//split client get request to array, with delimiters specified inside "[ ]" chars.
            	String httpGetRequest = dataFromClient.readLine();
            	String[] httpGetRequestParts = httpGetRequest.split("[\\s=&?/]");
            	
            	//call method getParam to get Get-request parameters.
            	String[] params = getParam(httpGetRequestParts);
            	ask = params[0];
            	hostname = params[1];
            	port = Integer.parseInt(params[2]);
            	string = params[3];

            	if(string != null) {
	            	string = string.replace('+',' ');
            	}
            	http = params[4];
            	
            	//if there is a ask in URI, proceed. Else return 400 Bad Request
            	if(ask.compareTo("ask") == 0 && http.compareTo("HTTP") == 0) {
            	
	            	try {
	            	String serverOutput = TCPClient.askServer(hostname, port, string);
	            	String respHeader = "HTTP/1.1 200 OK\r\n\r\n";
	            	StringBuilder sb = new StringBuilder();
	            	sb.append(respHeader);
	            	sb.append(serverOutput);
	    			dataToClient.writeBytes(sb.toString());
	            	} catch(Exception exc) {
	            		System.out.println("Not found");
	            		dataToClient.writeBytes("HTTP/1.1 404 Not found\r\n");
	            	}
            	} else {
            		dataToClient.writeBytes("HTTP/1.1 400 Bad Request\r\n");
            	}
    			clientSocket.close();
    			dataFromClient.close();
    			dataToClient.close();
        	}
        } catch(Exception exc) {
        	System.out.println("exception occured");
        }
    }
    
    public static String[] getParam(String[] httpGetRequestParts) {
    	String getAsk = null;
    	String getHostname = null;
    	String getPort = null;
    	String getString = null;
    	String getHttp = null;
    	
    	for(int i = 0; i < httpGetRequestParts.length; i++) {
    		if(httpGetRequestParts[i].compareTo("ask") == 0) {
    			getAsk = httpGetRequestParts[i];
    		} else if(httpGetRequestParts[i].compareTo("hostname") == 0) {
	    		getHostname = httpGetRequestParts[i+1];
			} else if(httpGetRequestParts[i].compareTo("port") == 0) {
				getPort = httpGetRequestParts[i+1];
			} else if(httpGetRequestParts[i].compareTo("string") == 0) {
				getString = httpGetRequestParts[i+1];
			} else if(httpGetRequestParts[i].compareTo("HTTP") == 0) {
				getHttp = httpGetRequestParts[i];
			}
    	}
    	String[] params = new String[5];
    	params[0] = getAsk;
    	params[1] = getHostname;
    	params[2] = getPort;
    	params[3] = getString;
    	params[4] = getHttp;
		return params;  	
    }
}

