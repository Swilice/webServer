package webserver;

import java.io.IOException;
import java.net.Socket;

public abstract class RequestHandler {
	
	protected String dataFolder;
	
	public abstract HttpResponse handle(HttpRequest request, Socket clientConnection) throws IOException;
	
	/* Accessors */
	public String getDataFolder() {
		return dataFolder;
	}

	public void setDataFolder(String dataFolder) {
		this.dataFolder = dataFolder;
	}
}
