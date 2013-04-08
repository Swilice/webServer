package com.thomasm.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.thomasm.logger.Logger;

public class HttpRequestHandler extends FileRequestHandler {
	
	public HttpRequestHandler(String dataFolder) {
		this.dataFolder = dataFolder;
	}
	
	/**
	 * Generates the response object and print it into the socket
	 */
	public void handle(HttpRequest request, Socket clientConnection) throws IOException {
		String splittedPath[] = request.getUrl().split("/");
		String fileName = splittedPath[splittedPath.length-1];
		File file = new File(dataFolder + "/" + fileName);
		
		if(file.exists()) {
			HttpResponse response = new HttpResponse(clientConnection);
			
			response.setStatusCode("200");
			response.setStatusReason("OK");
			
			response.addHeader("Content-Type", "text/html");
			response.addHeader("Content-Length", String.valueOf(file.length()));
			
			FileInputStream inputStream = new FileInputStream(file);
			OutputStream outputStream = response.getBody();
			
			int byteFromInput;
			while ((byteFromInput = inputStream.read()) != -1)
				outputStream.write(byteFromInput);
			
			inputStream.close();
			outputStream.close();
		}
		else {
			handleError(clientConnection, "404", "File : \"" + dataFolder + "/" + fileName + "\" not found");
		}
	}
	
	/**
	 * See @clientHandler.handleError
	 * @param clientConnection
	 * @param code
	 * @param reason
	 */
	public void handleError(Socket clientConnection, String code, String reason) {
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