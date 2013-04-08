package com.thomasm.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.thomasm.logger.Logger;

public class ClientHandler implements Runnable {
	
	private Socket clientConnection;
	private RequestRouter router;
	
	public ClientHandler(Socket clientConnection, RequestRouter router) {
		this.router = router;
		this.clientConnection = clientConnection;
	}
	
	/**
	 * Analyzing what was received through the socket
	 * Instanciate an HttpRequest object or an error response if incorrect input
	 */
	public void run() {
		HttpRequest request = new HttpRequest();
		String requestLine = null;
		
		try {
			InputStreamReader isr =  new InputStreamReader(clientConnection.getInputStream());
			BufferedReader rawData = new BufferedReader (isr);
	
			requestLine = rawData.readLine();
		} catch (IOException ioe) {
			handleError("500", "Couldn't obtain InputStream from socket");
		}
		if(requestLine == null) {
			handleError("500", "Request wasn't properly read from socket");
		}
		String splittedRequestLine[] = requestLine.split(" ");
		if(splittedRequestLine.length != 3) {
			handleError("500", "Request form is incorrect");
		}	
		if(!splittedRequestLine[0].equals("GET") && !splittedRequestLine[0].equals("get")) {
			handleError("501", "Request type is not implemented");
		}
		request.setMethod(splittedRequestLine[0]);
		
		if (splittedRequestLine[1].equals("") || splittedRequestLine[1].equals("/")) 
			request.setUrl("/index.html");
		else {
			request.setUrl(splittedRequestLine[1]);
		}
		
		if(!splittedRequestLine[2].equals("HTTP/1.1")) {
			handleError("501", "Http version not implemented");
		}
		request.setHttpVersion(splittedRequestLine[2]);
		
		RequestHandler requestHandler = router.getHandler(request);
		if(requestHandler == null) {
			handleError("500", "File type not handled");
		}
		else {
			try {
				requestHandler.handle(request, clientConnection);
			} catch (IOException e) {
				handleError("500", "Response generation failed");
			}
		}
	}
	
	/**
	 * Generates an error page that is directly sent through the socket and keeps trace of what happened in the logs
	 * @param code
	 * @param reason
	 */
	public void handleError(String code, String reason) {
		Logger.getInstance().printLogToConsole(code + ": " + reason);

		try {
			HttpResponse response = new HttpResponse(clientConnection);
			response.addHeader("Content-Type", "text/html");
			response.setStatusCode(code);
			response.setStatusReason(reason);
			PrintWriter pw = new PrintWriter(response.getBody());
			

			pw.println("<html><head><title>Server Error</title></head><body><h1>Server Error</h1><p>");
			pw.println(code + ": " + reason);
			pw.println("</p><pre>");
			pw.println("</pre></body></html>");
			pw.close();
		} catch (IOException ioe) {
			Logger.getInstance().printLogToConsole("Handling of error : \"" + code + ": " + reason +"\" failed");
		}
	}
}
