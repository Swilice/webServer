package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler {
	
	public ClientHandler() {
		
	}
	
	public HttpRequest handle(Socket clientConnection) {
		HttpRequest request = new HttpRequest();
		String requestLine = null;
		
		try {
			InputStreamReader isr =  new InputStreamReader(clientConnection.getInputStream());
			BufferedReader rawData = new BufferedReader (isr);
	
			// On parse les données et les assigne aux attributs de la classe
			requestLine = rawData.readLine();
		} catch (IOException ioe) {
			//error
		}
		if(requestLine == null) {
			//pb de connection à gérer
		}
		String splittedRequestLine[] = requestLine.split(" ");
		if(splittedRequestLine.length != 3) {
			//problème sur la request, à gérer
		}	
		request.setMethod(splittedRequestLine[0]);
		
		if (splittedRequestLine[1].equals("")) 
			request.setUrl("index.html");
		else if (splittedRequestLine[1].contains("..")) {
			//error
		}
		else 
			request.setUrl(splittedRequestLine[1]);
		
		request.setHttpVersion(splittedRequestLine[2]);
		
		//On ignore le reste pour le moment
		
		return request;
	}

}
