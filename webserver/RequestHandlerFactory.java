package webserver;

import java.net.Socket;

public class RequestHandlerFactory {
	
	private String dataFolder;
	private RequestHandler handler;
	
	public RequestHandlerFactory(String dataFolder) {
		this.dataFolder = dataFolder;
		this.handler = null;
	}
	
	public RequestHandler defineHandler(HttpRequest request) {
		
		String fileType = request.getUrl();
		if (fileType != null) {
			if (fileType.endsWith("jpeg") || fileType.endsWith("jpg")) {
				handler = new ImageRequestHandler(dataFolder);
			}
			else if (fileType.endsWith("html")) {
				handler = new HttpRequestHandler(dataFolder);
			}
			else 
				handler = null;
		}
		else {
			//error
		}
		
		return handler;
	}
}
