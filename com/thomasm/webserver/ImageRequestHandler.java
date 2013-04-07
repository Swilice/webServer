package com.thomasm.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.thomasm.logger.Logger;

public class ImageRequestHandler extends RequestHandler {

	String dataFolder;
	
	public ImageRequestHandler(String dataFolder) {
		this.dataFolder = dataFolder;
	}
	
	public HttpResponse handle(HttpRequest request, Socket clientConnection) throws IOException {
		HttpResponse response = null;
		
		String splittedPath[] = request.getUrl().split("/");
		String fileName = splittedPath[splittedPath.length-1];
		File file = new File(dataFolder + "/" + fileName);
		
		if(file.exists()) { 
			response = new HttpResponse(clientConnection);
			
			// Simple example --> everything is static
			response.setHttpVersion("HTTP/1.1");	
			response.setStatusCode("200");
			response.setStatusReason("OK");
			
			response.addHeader("Server", "ThomasMwebServer/1.0");
			response.addHeader("Content-Type", "image/jpeg");
			response.addHeader("Content-Length", String.valueOf(file.length()));
			response.addHeader("Connection", "Close");
			
			FileInputStream inputStream = new FileInputStream(file);
		    OutputStream outputStream = response.getBody();
		    
		    int byteFromInput;
	        while ((byteFromInput = inputStream.read()) != -1)
	        	outputStream.write(byteFromInput);

	        inputStream.close();
	        outputStream.close();
		}
		else {
			Logger.getInstance().printLogToConsole("404: File not found");
		}
		
		return response;
	}

}
