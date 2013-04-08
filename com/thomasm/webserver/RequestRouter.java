package com.thomasm.webserver;

public class RequestRouter {
	
	String dataFolder;
	private RequestHandler handler;
	
	public RequestRouter(String dataFolder) {
		this.dataFolder = dataFolder;
	}
	
	/**
	 * Determines which type of handler we need depending on the extension of the file requested
	 * @param request
	 * @return
	 */
	public RequestHandler getHandler(HttpRequest request) {
		
		String fileType = request.getUrl();
		if (fileType.endsWith("jpg")) {
			handler = new ImageRequestHandler(dataFolder);
		}
		else if (fileType.endsWith("html")) {
			handler = new HttpRequestHandler(dataFolder);
		}
		else {
			handler = null;
		}
		
		return handler;
	}
}
