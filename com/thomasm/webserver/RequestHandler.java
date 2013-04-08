package com.thomasm.webserver;

import java.io.IOException;
import java.net.Socket;

public interface RequestHandler {
	
	public void handle(HttpRequest request, Socket clientConnection) throws IOException;
}
