package webserver;

import java.net.Socket;

public class RequestHandlerFactory {
	
	String dataFolder;
	
	public RequestHandlerFactory(String dataFolder) {
		this.dataFolder = dataFolder;
	}
	
	public RequestHandler defineHandler(HttpRequest request) {
		RequestHandler handler = null;
		
		String fileType = extensionFind(request);
		if (fileType != null) {
			if (fileType == "jpeg" || fileType == "jpg") {
				handler = new ImageRequestHandler(clientConnection, dataFolder);
			}
			else if (fileType == "html") {
				handler = new HttpRequestHandler(clientConnection, dataFolder);
			}
		}
		else {
			//error
		}
		
		return handler;
	}
	
	private String extensionFind(HttpRequest request) {
		String extension = null;
		String splittedUrl[] = request.getUrl().split("/");
		String fileRequested = splittedUrl[splittedUrl.length-1];
		if(fileRequested.contains(".")) {
			extension = fileRequested.split(".")[1];
		}
		return extension;
	}
	/*
	public RequestHandler newHttpRequestHandler(Socket clientConnection) {
		return new HttpRequestHandler(clientConnection, dataFolder);
	}
	
	public RequestHandler newImageRequestHandler(Socket clientConnection) {
		return new ImageRequestHandler(clientConnection, dataFolder);
	} */
}
