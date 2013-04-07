package com.thomasm.webserver;

import java.io.IOException;
import java.net.Socket;

public abstract class RequestHandler {
	
	public abstract HttpResponse handle(HttpRequest request, Socket clientConnection) throws IOException;
}
