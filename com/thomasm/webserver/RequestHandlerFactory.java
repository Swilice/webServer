package com.thomasm.webserver;

import com.thomasm.logger.Logger;

public class RequestHandlerFactory {
	
	private String dataFolder;
	private RequestHandler handler;
	
	public RequestHandlerFactory(String dataFolder) {
		this.dataFolder = dataFolder;
	}
	
	public RequestHandler getHandler(HttpRequest request) {
		
		String fileType = request.getUrl();
		if (fileType != null) {
			if (fileType.endsWith("jpeg")) {
				handler = new ImageRequestHandler(dataFolder);
			}
			else if (fileType.endsWith("html")) {
				handler = new HttpRequestHandler(dataFolder);
			}
			else 
				handler = null;
		}
		else {
			Logger.getInstance().printLogToConsole("500: File type not handled");
		}
		return handler;
	}
	
	
}
