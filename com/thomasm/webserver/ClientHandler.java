package com.thomasm.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.thomasm.logger.Logger;

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
			Logger.getInstance().printLogToConsole("500: Couldn't obtain InputStream from socket");
		}
		if(requestLine == null) {
			Logger.getInstance().printLogToConsole("500: Request wasn't properly read from socket");
		}
		String splittedRequestLine[] = requestLine.split(" ");
		if(splittedRequestLine.length != 3) {
			Logger.getInstance().printLogToConsole("500: Request form is incorrect");
		}	
		if(splittedRequestLine[0] != "GET" && splittedRequestLine[0] != "get") {
			Logger.getInstance().printLogToConsole("501: Request type is not implemented");
		}
		request.setMethod(splittedRequestLine[0]);
		
		if (splittedRequestLine[1].equals("") || splittedRequestLine[1].equals("/")) 
			request.setUrl("/index.html");
		else if (splittedRequestLine[1].contains("..")) {
			Logger.getInstance().printLogToConsole("403: Trying to get to a forbidden folder");
		}
		else 
			request.setUrl(splittedRequestLine[1]);
		
		if(splittedRequestLine[2] != "HTTP/1.0") {
			Logger.getInstance().printLogToConsole("501: Http version not implemented");
		}
		request.setHttpVersion(splittedRequestLine[2]);
		
		return request;
	}

}
